package com.xu.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.entity.user.User;
import com.xu.mapper.user.UserMapper;
import com.xu.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getUserById(int userId) {

//        System.out.println(1/0);
        return null;
    }
}
