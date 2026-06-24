package service;


import entity.Attendance;
import java.util.List;

public interface AttendanceService {

    // 上班打卡
    public boolean checkIn(Integer empId);

    // 下班签退
    public boolean checkOut(Integer empId);

    // 查询个人考勤
    public List<Attendance> getMyAttendance(Integer empId);

    // 管理员查询所有考勤
    public List<Attendance> getAllAttendances();
}