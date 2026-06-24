package servlet;

import entity.LeaveApply;
import entity.User;
import service.LeaveService; // 直接导入service包下的类
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.impl.LeaveServiceImpl;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class LeaveServlet extends HttpServlet {

    private final LeaveService leaveService = new LeaveServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String msg = "";

        if ("addLeave".equals(action) && "employee".equals(loginUser.getRole())) {
            Integer empId = loginUser.getEmpId();
            String leaveType = request.getParameter("leaveType");
            String reason = request.getParameter("reason");

            LeaveApply leave = new LeaveApply();
            leave.setEmpId(empId);
            leave.setLeaveType(leaveType);
            leave.setReason(reason);
            leave.setStartTime(new Date(System.currentTimeMillis()));
            leave.setEndTime(new Date(System.currentTimeMillis() + 86400000L));
            leave.setApproveStatus("待审批");

            boolean result = leaveService.addLeave(leave);
            msg = result ? "请假申请提交成功，等待管理员审批！" : "提交失败，请重试！";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/employee/applyLeave.jsp").forward(request, response);
        }

        else if ("approve".equals(action) && "admin".equals(loginUser.getRole())) {
            try {
                Integer id = Integer.parseInt(request.getParameter("id"));
                boolean result = leaveService.approve(id);
                msg = result ? "审批通过成功！" : "操作失败！";
            } catch (Exception e) {
                msg = "参数错误！";
            }
            response.sendRedirect(request.getContextPath() + "/leave?action=listAll&msg=" + msg);
        }

        else if ("reject".equals(action) && "admin".equals(loginUser.getRole())) {
            try {
                Integer id = Integer.parseInt(request.getParameter("id"));
                boolean result = leaveService.reject(id);
                msg = result ? "已拒绝该申请！" : "操作失败！";
            } catch (Exception e) {
                msg = "参数错误！";
            }
            response.sendRedirect(request.getContextPath() + "/leave?action=listAll&msg=" + msg);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("toApply".equals(action) && "employee".equals(loginUser.getRole())) {
            request.getRequestDispatcher("/employee/applyLeave.jsp").forward(request, response);
        }

        else if ("listMyLeave".equals(action) && "employee".equals(loginUser.getRole())) {
            // 调用你Service里的 listMy 方法
            List<LeaveApply> leaveList = leaveService.listMy(loginUser.getEmpId());
            request.setAttribute("leaveList", leaveList);
            request.getRequestDispatcher("/employee/myLeave.jsp").forward(request, response);
        }

        else if ("listAll".equals(action) && "admin".equals(loginUser.getRole())) {
            List<LeaveApply> leaveList = leaveService.listAll();
            request.setAttribute("leaveList", leaveList);
            request.setAttribute("msg", request.getParameter("msg"));
            request.getRequestDispatcher("/admin/leaveAudit.jsp").forward(request, response);
        }
    }
}