package dao;

import entity.Department;

import java.util.List;

public interface DepartmentDao {
    boolean addDept(Department dept);
    boolean deleteDept(Integer id);
    boolean updateDept(Department dept);
    //查询所有部门列表
    List<Department> listAll();
}