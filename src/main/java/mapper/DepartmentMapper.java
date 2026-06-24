package mapper;

import entity.Department;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DepartmentMapper {
    boolean addDept(Department dept);
    boolean deleteDept(@Param("id") Integer id);
    boolean updateDept(Department dept);
    List<Department> listAll();
}