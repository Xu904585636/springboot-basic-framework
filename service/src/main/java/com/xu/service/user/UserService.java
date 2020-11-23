package com.xu.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.entity.user.User;

public interface UserService extends IService<User> {

    User getUserById(int userId);
}
