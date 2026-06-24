package service.impl;

import entity.Employee;
import mapper.EmployeeMapper;
import service.EmployeeService;
import utils.MyBatisUtil;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper = MyBatisUtil.getSqlSession().getMapper(EmployeeMapper.class);

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