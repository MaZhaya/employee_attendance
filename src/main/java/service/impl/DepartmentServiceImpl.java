package service.impl;

import entity.Department;
import mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.DepartmentService;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public boolean add(Department dept) {
        return departmentMapper.addDept(dept);
    }

    @Override
    public boolean delete(Integer id) {
        return departmentMapper.deleteDept(id);
    }

    @Override
    public boolean update(Department dept) {
        return departmentMapper.updateDept(dept);
    }

    @Override
    public List<Department> list() {
        return departmentMapper.listAll();
    }
}
