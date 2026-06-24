import entity.Employee;
import mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.Date;
import java.util.List;

public class EmployeeMapperTest {

    // 1. 测试添加员工
    @Test
    public void testAddEmployee() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            Employee emp = new Employee();
            emp.setEmpNo("EMP1001");
            emp.setEmpName("张三");
            emp.setGender("男");
            emp.setPhone("13800138000");
            emp.setDeptId(1);
            emp.setEntryDate(new Date());
            emp.setStatus(1);

            mapper.addEmployee(emp);
            System.out.println("添加成功");
        }
    }

    // 2. 测试根据ID查询员工
    @Test
    public void testGetById() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmployeeById(1);
            System.out.println(emp);
        }
    }

    // 3. 测试查询所有员工
    @Test
    public void testListAll() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            List<Employee> list = mapper.listAll();
            list.forEach(System.out::println);
        }
    }

    // 4. 测试修改员工
    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            Employee emp = new Employee();
            emp.setId(6);
            emp.setEmpNo("EMP9999");
            emp.setEmpName("张三修改");
            emp.setGender("女");
            emp.setPhone("13999999999");
            emp.setDeptId(1);
            emp.setStatus(0);

            mapper.updateEmployee(emp);
            System.out.println("修改成功");
        }
    }

    // 5. 测试删除员工
    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            mapper.deleteEmployee(6);
            System.out.println("删除成功");
        }
    }
}