package service;

import entity.User;

// UserService 接口
public interface UserService {
    User login(String username, String password);
    User login(String username, String password, String role);
    boolean register(User user); // 新增注册方法
}