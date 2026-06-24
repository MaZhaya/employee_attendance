package mapper;

import entity.LeaveApply;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface LeaveMapper {
    boolean addLeave(LeaveApply leave);
    boolean updateStatus(@Param("id") Integer id, @Param("status") String status);
    List<LeaveApply> listAll();
    List<LeaveApply> listByEmpId(@Param("empId") Integer empId);
}