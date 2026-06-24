package controller;

import entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.DepartmentService;

import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(params = "action=list")
    public String list(Model model) {
        List<Department> list = departmentService.list();
        model.addAttribute("deptList", list);
        return "admin/dept/list";
    }

    @GetMapping(params = "action=toAdd")
    public String toAdd() {
        return "admin/dept/add";
    }

    @PostMapping(params = "action=add")
    public String add(@RequestParam("deptName") String deptName,
                      @RequestParam(name = "deptDesc", required = false) String deptDesc) {
        Department dept = new Department();
        dept.setDeptName(deptName);
        dept.setDeptDesc(deptDesc);
        departmentService.add(dept);
        return "redirect:/dept?action=list";
    }

    @GetMapping(params = "action=delete")
    public String delete(@RequestParam("id") Integer id) {
        departmentService.delete(id);
        return "redirect:/dept?action=list";
    }
}
