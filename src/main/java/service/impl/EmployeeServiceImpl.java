package service.impl;

import entity.Employee;
import mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.EmployeeService;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public boolean add(Employee emp) {
        return employeeMapper.addEmployee(emp);
    }

    @Override
    public boolean delete(Integer id) {
        return employeeMapper.deleteEmployee(id);
    }

    @Override
    public boolean update(Employee emp) {
        return employeeMapper.updateEmployee(emp);
    }

    @Override
    public Employee getById(Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    @Override
    public List<Employee> list() {
        return employeeMapper.listAll();
    }
}
