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

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        HttpSession session,
                        Model model) {

        User loginUser = userService.login(username, password, role);

        if (loginUser == null) {
            model.addAttribute("msg", "账号或密码错误！");
            return "login";
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

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(name = "role", defaultValue = "employee") String role,
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.jsp";
    }
}
