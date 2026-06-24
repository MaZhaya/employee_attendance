package controller;

import entity.LeaveApply;
import entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.LeaveService;

import java.sql.Date;
import java.util.List;

/**
 * 请假申请与审批 — 替代 LeaveServlet
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // ========== 跳转请假申请页（员工） ==========
    @GetMapping(params = "action=toApply")
    public String toApply() {
        return "employee/leaveApply";
    }

    // ========== 提交请假申请（员工） ==========
    @PostMapping(params = "action=apply")
    public String apply(@RequestParam String leaveType,
                        @RequestParam(required = false) String startDate,
                        @RequestParam(required = false) String endDate,
                        @RequestParam String reason,
                        HttpSession session,
                        Model model) {

        User loginUser = (User) session.getAttribute("loginUser");
        LeaveApply leave = new LeaveApply();
        leave.setEmpId(loginUser.getEmpId());
        leave.setLeaveType(leaveType);
        leave.setReason(reason);

        // 处理日期（如果用户填写了就用填写的，否则默认今天到明天）
        if (startDate != null && !startDate.isEmpty()) {
            leave.setStartTime(Date.valueOf(startDate));
        } else {
            leave.setStartTime(new Date(System.currentTimeMillis()));
        }
        if (endDate != null && !endDate.isEmpty()) {
            leave.setEndTime(Date.valueOf(endDate));
        } else {
            leave.setEndTime(new Date(System.currentTimeMillis() + 86400000L));
        }
        leave.setApproveStatus("待审批");

        boolean result = leaveService.addLeave(leave);
        model.addAttribute("msg", result ? "请假申请提交成功，等待管理员审批！" : "提交失败，请重试！");
        return "employee/leaveApply";
    }

    // ========== 我的请假记录（员工） ==========
    @GetMapping(params = "action=listMyLeave")
    public String listMy(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        List<LeaveApply> list = leaveService.listMy(loginUser.getEmpId());
        model.addAttribute("leaveList", list);
        return "employee/myLeave";
    }

    // ========== 所有请假记录（管理员） ==========
    @GetMapping(params = "action=listAll")
    public String listAll(@RequestParam(required = false) String msg, Model model) {
        List<LeaveApply> list = leaveService.listAll();
        model.addAttribute("leaveList", list);
        if (msg != null) {
            model.addAttribute("msg", msg);
        }
        return "admin/leaveAudit";
    }

    // ========== 审批通过（管理员） ==========
    @GetMapping(params = "action=pass")
    public String approve(@RequestParam Integer id) {
        leaveService.approve(id);
        return "redirect:/leave?action=listAll&msg=审批通过成功！";
    }

    // ========== 审批拒绝（管理员） ==========
    @GetMapping(params = "action=refuse")
    public String reject(@RequestParam Integer id) {
        leaveService.reject(id);
        return "redirect:/leave?action=listAll&msg=已拒绝该申请！";
    }
}
