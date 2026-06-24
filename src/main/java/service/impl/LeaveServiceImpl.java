package service.impl;

import entity.LeaveApply;
import mapper.LeaveMapper;
import service.LeaveService;
import utils.MyBatisUtil;
import java.util.List;

public class LeaveServiceImpl implements LeaveService {

    private final LeaveMapper leaveMapper = MyBatisUtil.getSqlSession().getMapper(LeaveMapper.class);

    @Override
    public boolean addLeave(LeaveApply leave) {
        return leaveMapper.addLeave(leave);
    }

    @Override
    public boolean approve(Integer id) {
        return leaveMapper.updateStatus(id, "已通过");
    }

    @Override
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