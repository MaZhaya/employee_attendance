package test;

import entity.User;
import mapper.UserMapper;
import org.junit.jupiter.api.Test;
import utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class UserMapperTest {

    // 1. 测试新增（注册）
    @Test
    public void testInsert() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setUsername("testuser");
            user.setPassword("123456");
            user.setRole("employee");
            user.setEmpId(1);

            mapper.insertUser(user);
            System.out.println("新增成功");
        }
    }

    // 2. 测试根据ID查询
    @Test
    public void testGetById() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getUserById(1);
            System.out.println(user);
        }
    }

    // 3. 测试查询所有
    @Test
    public void testGetAll() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> list = mapper.getAllUsers();
            list.forEach(System.out::println);
        }
    }

    // 4. 测试修改
    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            User user = new User();
            user.setId(1);
            user.setUsername("adminUpdate");
            user.setPassword("654321");
            user.setRole("admin");

            mapper.updateUser(user);
            System.out.println("修改成功");
        }
    }

    // 5. 测试删除
    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.deleteUser(1);
            System.out.println("删除成功");
        }
    }

    // 6. 测试登录
    @Test
    public void testLogin() {
        try (SqlSession session = MyBatisUtil.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.login("admin", "123456");
            System.out.println("登录用户：" + user);
        }
    }
}