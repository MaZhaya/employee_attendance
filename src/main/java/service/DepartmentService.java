package service;

import entity.Department;
import java.util.List;

public interface DepartmentService {
    boolean add(Department dept);
    boolean delete(Integer id);
    boolean update(Department dept);
    List<Department> list();
}