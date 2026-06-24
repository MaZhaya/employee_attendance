package controller;

import entity.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.EmployeeService;

import java.util.List;

/**
 * 员工管理 — 替代 EmpServlet
 */
@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmployeeService employeeService;

    // ========== 员工列表 ==========
    @GetMapping(params = "action=list")
    public String list(Model model) {
        List<Employee> list = employeeService.list();
        model.addAttribute("empList", list);
        return "admin/emp/list"; // → /admin/emp/list.jsp
    }

    // ========== 跳转新增页 ==========
    @GetMapping(params = "action=toAdd")
    public String toAdd() {
        return "admin/emp/add"; // → /admin/emp/add.jsp
    }

    // ========== 新增员工 ==========
    @PostMapping
    public String add(@RequestParam String empName,
                      @RequestParam String gender,
                      @RequestParam Integer deptId) {
        Employee emp = new Employee();
        emp.setEmpName(empName);
        emp.setGender(gender);
        emp.setDeptId(deptId);
        employeeService.add(emp);
        return "redirect:/emp?action=list";
    }

    // ========== 删除员工 ==========
    @GetMapping(params = "action=delete")
    public String delete(@RequestParam Integer id) {
        employeeService.delete(id);
        return "redirect:/emp?action=list";
    }
}
