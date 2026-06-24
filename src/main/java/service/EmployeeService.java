package service;

import entity.Employee;
import java.util.List;

public interface EmployeeService {
    boolean add(Employee emp);
    boolean delete(Integer id);
    boolean update(Employee emp);
    Employee getById(Integer id);
    List<Employee> list();
}