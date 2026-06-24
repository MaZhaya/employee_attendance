package servlet;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // 替换为MyBatis业务层
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. 封装用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        // 3. 调用业务层完成注册
        boolean success = userService.register(user);

        if (success) {
            // 注册成功，跳转到登录页
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=注册成功，请登录");
        } else {
            // 注册失败，返回注册页
            request.setAttribute("msg", "注册失败，用户名已存在！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}