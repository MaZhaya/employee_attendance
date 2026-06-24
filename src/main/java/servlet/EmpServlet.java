package servlet;

import entity.Employee;
import service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.EmployeeServiceImpl;

import java.io.IOException;
import java.util.List;

public class EmpServlet extends HttpServlet {
    private EmployeeService employeeService = new EmployeeServiceImpl();
    //管理员：员工管理
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            List<Employee> list = employeeService.list();
            request.setAttribute("empList", list);
            request.getRequestDispatcher("/admin/emp/list.jsp").forward(request, response);
        }
        else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            employeeService.delete(id);
            response.sendRedirect(request.getContextPath() + "/emp?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empName = request.getParameter("empName");
        String gender = request.getParameter("gender");
        int deptId = Integer.parseInt(request.getParameter("deptId"));

        Employee emp = new Employee();
        emp.setEmpName(empName);
        emp.setGender(gender);
        emp.setDeptId(deptId);

        employeeService.add(emp);
        response.sendRedirect(request.getContextPath() + "/emp?action=list");
    }
}