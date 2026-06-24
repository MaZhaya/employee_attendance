package servlet;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录控制器
 * 接收登录页面提交的账号、密码、角色，完成登录验证
 */
// 【关键修复】加上这个注解，才能访问 /login ，解决404！
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 业务层对象：面向接口编程
    private final UserService userService = new UserServiceImpl();

    /**
     * 处理登录请求（POST）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取前端表单提交的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. 调用业务层，进行登录验证
        User loginUser = userService.login(username, password, role);

        // 3. 判断用户是否存在
        if (loginUser == null) {
            // 登录失败：设置错误信息，转发回登录页
            request.setAttribute("msg", "账号或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // 登录成功：将用户信息存入session
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            // 4. 根据角色跳转到不同页面
            if ("admin".equals(loginUser.getRole())) {
                // 管理员 → 后台首页
                response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            } else {
                // 员工 → 员工首页
                response.sendRedirect(request.getContextPath() + "/employee/index.jsp");
            }
        }
    }

    /**
     * 处理GET请求，直接调用doPost，统一处理逻辑
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}