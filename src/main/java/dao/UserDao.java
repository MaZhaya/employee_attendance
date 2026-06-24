package dao;

import entity.User;

public interface UserDao {
    // 登录验证
    User login(String username, String password);

    User login(String username, String pwd, String role);
}