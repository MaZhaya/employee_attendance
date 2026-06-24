package service.impl;

import entity.LeaveApply;
import mapper.LeaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LeaveService;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    @Transactional
    public boolean addLeave(LeaveApply leave) {
        return leaveMapper.addLeave(leave);
    }

    @Override
    @Transactional
    public boolean approve(Integer id) {
        return leaveMapper.updateStatus(id, "已通过");
    }

    @Override
    @Transactional
    public boolean reject(Integer id) {
        return leaveMapper.updateStatus(id, "已拒绝");
    }

    @Override
    public List<LeaveApply> listAll() {
        return leaveMapper.listAll();
    }

    @Override
    public List<LeaveApply> listMy(Integer empId) {
        return leaveMapper.listByEmpId(empId);
    }

    @Override
    public List<LeaveApply> getMyLeaves(Integer empId) {
        return leaveMapper.listByEmpId(empId);
    }
}
