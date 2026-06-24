package mapper;

import entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {

    // 登录（不带角色）
    User login(@Param("username") String username,
               @Param("password") String password);

    // 登录（带角色）
    User loginByRole(@Param("username") String username,
                     @Param("password") String password,
                     @Param("role") String role);

    // ===================== 以下是补全的增删改查 =====================
    // 新增用户（注册）
    int insertUser(User user);

    // 根据ID查询用户
    User getUserById(@Param("id") Integer id);

    // 查询所有用户
    List<User> getAllUsers();

    // 修改用户信息
    int updateUser(User user);

    // 根据ID删除用户
    int deleteUser(@Param("id") Integer id);
}