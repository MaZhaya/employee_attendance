package interceptor;

import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器：统一检查用户是否已登录。
 * 替代原来每个 Servlet 里重复的登录检查代码。
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 未登录 → 跳转到登录页
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false; // 不放行，后续 Controller 不会执行
        }

        String uri = request.getRequestURI();
        String role = loginUser.getRole();

        // 管理员才能访问 /admin/ 开头的路径
        if (uri.contains("/admin/") && !"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/employee/index.jsp");
            return false;
        }

        // 员工才能访问 /employee/ 开头的路径
        if (uri.contains("/employee/") && !"employee".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            return false;
        }

        return true; // 放行
    }
}
