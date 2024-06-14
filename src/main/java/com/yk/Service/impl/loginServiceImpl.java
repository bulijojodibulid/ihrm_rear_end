package com.yk.Service.impl;

import com.yk.DTO.UserDTO;
import com.yk.Mapper.UserMapper;
import com.yk.Service.loginService;
import com.yk.Utils.JwtUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class loginServiceImpl implements loginService {
    private final UserMapper userMapper;

    public loginServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO queryUser(String phone, String password) {
        return userMapper.queryUser(phone, password);
    }

    @Override
    public String login(String phone, String password){
        UserDTO userDTO = this.queryUser(phone, password);
        if(userDTO != null) {
            return JwtUtils.getJwt(userDTO.getId());
        }else{
            return "";
        }
    }
}
