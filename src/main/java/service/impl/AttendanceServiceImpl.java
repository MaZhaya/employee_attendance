package service.impl;

import entity.Attendance;
import mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AttendanceService;

import java.sql.Time;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public boolean checkIn(Integer empId) {
        // ① 先查今天是否已经打过卡
        Attendance today = attendanceMapper.getTodayAttendance(empId);
        if (today != null) return false;

        // ② 创建考勤记录
        Attendance att = new Attendance();
        att.setEmpId(empId);
        att.setCheckDate(new java.sql.Date(System.currentTimeMillis()));
        att.setCheckIn(new Time(System.currentTimeMillis()));

        // ③ 判断是否迟到：早于 9:00 = 正常，晚于 9:00 = 迟到
        Time now = new Time(System.currentTimeMillis());
        Time standard = Time.valueOf("09:00:00");
        att.setStatus(now.after(standard) ? "迟到" : "正常");

        // ④ 写入数据库
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
