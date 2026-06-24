package servlet;

import entity.Attendance;
import entity.User;
import service.AttendanceService; // 直接导入service包下的类
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.impl.AttendanceServiceImpl;

import java.io.IOException;
import java.util.List;

public class AttendanceServlet extends HttpServlet {
    // 直接 new AttendanceService()
    private final AttendanceService attendanceService = new AttendanceServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !"employee".equals(loginUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Integer empId = loginUser.getEmpId();
        String action = request.getParameter("action");
        String msg = "";

        if ("checkIn".equals(action)) {
            boolean result = attendanceService.checkIn(empId);
            msg = result ? "上班打卡成功！" : "今日已打卡，无需重复操作！";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/employee/checkin.jsp").forward(request, response);
        } else if ("checkOut".equals(action)) {
            boolean result = attendanceService.checkOut(empId);
            msg = result ? "下班签退成功！" : "未查询到今日上班打卡记录！";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/employee/checkin.jsp").forward(request, response);
        }else if ("listAll".equals(action)) {
            List<Attendance> list = attendanceService.getAllAttendances();
            request.setAttribute("attList", list);
            request.getRequestDispatcher("/admin/attendance/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null || !"employee".equals(loginUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        Integer empId = loginUser.getEmpId();

        if ("listMyAttendance".equals(action)) {
            // 调用你Service里的 listByEmpId 方法
            List<Attendance> attendanceList = attendanceService.getMyAttendance(empId);
            request.setAttribute("attendanceList", attendanceList);
            request.getRequestDispatcher("/employee/myAttendance.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/employee/checkin.jsp").forward(request, response);
        }
    }
}