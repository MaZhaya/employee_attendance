package dao;

import entity.LeaveApply;

import java.util.List;

public interface LeaveDao {
    boolean addLeave(LeaveApply leave);
    boolean updateStatus(Integer id, String status);
    List<LeaveApply> listAll();
    List<LeaveApply> listByEmpId(Integer empId);
}