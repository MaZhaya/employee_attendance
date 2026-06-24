package mapper;

import entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmployeeMapper {
    boolean addEmployee(Employee employee);
    boolean deleteEmployee(@Param("id") Integer id);
    boolean updateEmployee(Employee employee);
    Employee getEmployeeById(@Param("id") Integer id);
    List<Employee> listAll();
}