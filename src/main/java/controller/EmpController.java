package controller;

import entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(params = "action=list")
    public String list(Model model) {
        List<Employee> list = employeeService.list();
        model.addAttribute("empList", list);
        return "admin/emp/list";
    }

    @GetMapping(params = "action=toAdd")
    public String toAdd() {
        return "admin/emp/add";
    }

    @PostMapping
    public String add(@RequestParam("empName") String empName,
                      @RequestParam("gender") String gender,
                      @RequestParam("deptId") Integer deptId) {
        Employee emp = new Employee();
        emp.setEmpName(empName);
        emp.setGender(gender);
        emp.setDeptId(deptId);
        employeeService.add(emp);
        return "redirect:/emp?action=list";
    }

    @GetMapping(params = "action=delete")
    public String delete(@RequestParam("id") Integer id) {
        employeeService.delete(id);
        return "redirect:/emp?action=list";
    }
}
