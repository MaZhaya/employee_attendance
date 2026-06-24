package service.impl;

import entity.Department;
import mapper.DepartmentMapper;
import service.DepartmentService;
import utils.MyBatisUtil;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper = MyBatisUtil.getSqlSession().getMapper(DepartmentMapper.class);

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