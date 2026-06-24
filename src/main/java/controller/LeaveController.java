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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.LeaveService;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // ========== 跳转请假申请页（员工） ==========
    @GetMapping("/toApply")
    public String toApply() {
        return "employee/leaveApply";
    }

    // ========== 提交请假申请（员工） ==========
    @PostMapping("/apply")
    public String apply(@RequestParam("leaveType") String leaveType,
                        @RequestParam(name = "startDate", required = false) String startDate,
                        @RequestParam(name = "endDate", required = false) String endDate,
                        @RequestParam("reason") String reason,
                        HttpSession session,
                        RedirectAttributes redirectAttrs) {

        User loginUser = (User) session.getAttribute("loginUser");
        LeaveApply leave = new LeaveApply();
        leave.setEmpId(loginUser.getEmpId());
        leave.setLeaveType(leaveType);
        leave.setReason(reason);

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

        leaveService.addLeave(leave);
        redirectAttrs.addFlashAttribute("msg", "请假申请提交成功，等待管理员审批！");
        return "redirect:/leave/toApply";
    }

    // ========== 我的请假记录（员工） ==========
    @GetMapping("/my")
    public String listMy(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        List<LeaveApply> list = leaveService.listMy(loginUser.getEmpId());
        model.addAttribute("leaveList", list);
        return "employee/myLeave";
    }

    // ========== 所有请假记录 + 审批操作（管理员） ==========
    @GetMapping("/list")
    public String listAll(Model model) {
        List<LeaveApply> list = leaveService.listAll();
        model.addAttribute("leaveList", list);
        return "admin/leaveAudit";
    }

    // ========== 审批通过 ==========
    @GetMapping("/pass")
    public String approve(@RequestParam("id") Integer id,
                          RedirectAttributes redirectAttrs) {
        leaveService.approve(id);
        redirectAttrs.addFlashAttribute("msg", "审批通过成功！");
        return "redirect:/leave/list";
    }

    // ========== 审批拒绝 ==========
    @GetMapping("/refuse")
    public String reject(@RequestParam("id") Integer id,
                         RedirectAttributes redirectAttrs) {
        leaveService.reject(id);
        redirectAttrs.addFlashAttribute("msg", "已拒绝该申请！");
        return "redirect:/leave/list";
    }
}
