package servlet;

import entity.Department;
import service.DepartmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.DepartmentServiceImpl;

import java.io.IOException;
import java.util.List;

public class DeptServlet extends HttpServlet {
    private DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            List<Department> list = departmentService.list();
            request.setAttribute("deptList", list);
            request.getRequestDispatcher("/admin/dept/list.jsp").forward(request, response);
        }
        else if ("toAdd".equals(action)) {
            request.getRequestDispatcher("/admin/dept/add.jsp").forward(request, response);
        }
        else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            departmentService.delete(id);
            response.sendRedirect(request.getContextPath() + "/dept?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String name = request.getParameter("deptName");
            Department dept = new Department();
            dept.setDeptName(name);
            departmentService.add(dept);
            response.sendRedirect(request.getContextPath() + "/dept?action=list");
        }
    }
}