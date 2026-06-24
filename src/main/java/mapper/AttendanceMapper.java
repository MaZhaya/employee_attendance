package mapper;

import entity.Attendance;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AttendanceMapper {
    boolean checkIn(Attendance attendance);
    boolean checkOut(@Param("empId") Integer empId);
    Attendance getTodayAttendance(@Param("empId") Integer empId);
    List<Attendance> listByEmpId(@Param("empId") Integer empId);
    List<Attendance> listAll();
    boolean deleteById(@Param("id") Integer id);//删除考勤记录

    List<Attendance> listAllWithEmployee();//关联查询
}