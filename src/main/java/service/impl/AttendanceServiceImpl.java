package service.impl;

import entity.Attendance;
import mapper.AttendanceMapper;
import service.AttendanceService;
import utils.MyBatisUtil;
import java.sql.Time;
import java.util.List;

public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceMapper attendanceMapper = MyBatisUtil.getSqlSession().getMapper(AttendanceMapper.class);

    @Override
    public boolean checkIn(Integer empId) {
        Attendance today = attendanceMapper.getTodayAttendance(empId);
        if (today != null) return false;

        Attendance att = new Attendance();
        att.setEmpId(empId);
        att.setCheckDate(new java.sql.Date(System.currentTimeMillis()));
        att.setCheckIn(new Time(System.currentTimeMillis()));

        Time now = new Time(System.currentTimeMillis());
        Time standard = Time.valueOf("09:00:00");
        att.setStatus(now.after(standard) ? "迟到" : "正常");

        return attendanceMapper.checkIn(att);
    }

    @Override
    public boolean checkOut(Integer empId) {
        return attendanceMapper.checkOut(empId);
    }

    @Override
    public List<Attendance> getMyAttendance(Integer empId) {
        return attendanceMapper.listByEmpId(empId);
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceMapper.listAll();
    }
}