package controller;

import entity.Attendance;
import entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AttendanceService;

import java.util.List;

/**
 * 考勤打卡 — 替代 AttendanceServlet
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // ========== 打卡页面 ==========
    @GetMapping
    public String checkinPage() {
        return "employee/attendance"; // → /employee/attendance.jsp
    }

    // ========== 上班打卡 ==========
    @PostMapping(params = "action=checkIn")
    public String checkIn(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        Integer empId = loginUser.getEmpId();
        boolean result = attendanceService.checkIn(empId);
        model.addAttribute("msg", result ? "上班打卡成功！" : "今日已打卡，无需重复操作！");
        return "employee/attendance";
    }

    // ========== 下班签退 ==========
    @PostMapping(params = "action=checkOut")
    public String checkOut(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        Integer empId = loginUser.getEmpId();
        boolean result = attendanceService.checkOut(empId);
        model.addAttribute("msg", result ? "下班签退成功！" : "未查询到今日上班打卡记录！");
        return "employee/attendance";
    }

    // ========== 我的考勤记录（员工） ==========
    @GetMapping(params = "action=listMyAttendance")
    public String listMy(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        List<Attendance> list = attendanceService.getMyAttendance(loginUser.getEmpId());
        model.addAttribute("attendanceList", list);
        return "employee/myAttendance";
    }

    // ========== 全员考勤记录（管理员） ==========
    @GetMapping(params = "action=listAll")
    public String listAll(Model model) {
        List<Attendance> list = attendanceService.getAllAttendances();
        model.addAttribute("attList", list);
        return "admin/attendance/allAttendance";
    }
}
