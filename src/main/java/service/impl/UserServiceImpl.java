package service.impl;

import entity.User;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public User login(String username, String password, String role) {
        return userMapper.loginByRole(username, password, role);
    }

    @Override
    @Transactional
    public boolean register(User user) {
        int rows = userMapper.insertUser(user);
        return rows > 0;
    }
}
