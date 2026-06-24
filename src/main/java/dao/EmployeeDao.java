package dao;

import entity.Employee;

import java.util.List;

public interface EmployeeDao {
    // 添加员工
    boolean addEmployee(Employee employee);

    // 删除员工
    boolean deleteEmployee(Integer id);

    // 修改员工
    boolean updateEmployee(Employee employee);

    // 根据ID查询
    Employee getEmployeeById(Integer id);

    // 查询所有
    List<Employee> listAll();
}