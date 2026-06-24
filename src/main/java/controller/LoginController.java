package controller;

import entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.UserService;

/**
 * 登录/注册/退出 — 替代 LoginServlet + RegisterServlet + LogoutServlet
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // ==================== 登录 ====================

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String role,
                        HttpSession session,
                        Model model) {

        User loginUser = userService.login(username, password, role);

        if (loginUser == null) {
            model.addAttribute("msg", "账号或密码错误！");
            return "login"; // → /login.jsp
        }

        session.setAttribute("loginUser", loginUser);

        if ("admin".equals(loginUser.getRole())) {
            return "redirect:/admin/index.jsp";
        } else {
            return "redirect:/employee/index.jsp";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ==================== 注册 ====================

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(defaultValue = "employee") String role,
                           Model model) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        boolean success = userService.register(user);

        if (success) {
            return "redirect:/login.jsp?msg=注册成功，请登录";
        } else {
            model.addAttribute("msg", "注册失败，用户名已存在！");
            return "register";
        }
    }

    // ==================== 退出 ====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.jsp";
    }
}
