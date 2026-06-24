package service;

import entity.LeaveApply;
import java.util.List;

public interface LeaveService {
    boolean addLeave(LeaveApply leave);
    boolean approve(Integer id);
    boolean reject(Integer id);
    List<LeaveApply> listAll();
    List<LeaveApply> listMy(Integer empId);
    List<LeaveApply> getMyLeaves(Integer empId);
}