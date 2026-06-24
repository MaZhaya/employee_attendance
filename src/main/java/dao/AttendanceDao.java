package dao;

import entity.Attendance;

import java.util.List;

public interface AttendanceDao {
    // 上班打卡
    boolean checkIn(Attendance attendance);

    // 下班签退
    boolean checkOut(Integer empId);

    // 根据员工ID查询今日考勤
    Attendance getTodayAttendance(Integer empId);

    // 查询某员工所有考勤
    List<Attendance> listByEmpId(Integer empId);

    //管理员查询所有考勤信息
    List<Attendance> listAll();
}