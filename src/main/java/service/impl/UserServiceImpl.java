package service.impl;

import entity.User;
import mapper.UserMapper;
import service.UserService;
import utils.MyBatisUtil;

public class UserServiceImpl implements UserService {

    private final UserMapper userMapper = MyBatisUtil.getSqlSession().getMapper(UserMapper.class);

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public User login(String username, String password, String role) {
        return userMapper.loginByRole(username, password, role);
    }

    @Override
    public boolean register(User user) {
        // 调用UserMapper的insert方法
        int rows = userMapper.insertUser(user);
        return rows > 0; // 插入行数>0表示成功
    }
}