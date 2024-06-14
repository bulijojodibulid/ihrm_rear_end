package com.yk.Service.impl;

import com.yk.DTO.UserDTO;
import com.yk.Mapper.UserMapper;
import com.yk.Service.UserService;
import com.yk.VO.SimpleUserVO;
import com.yk.VO.UserVO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements UserService {
    private final UserMapper userMapper;

    public userServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public UserVO queryUserInfo(Integer userId) {
        return userMapper.queryUserInfo(userId);
    }

    @Override
    public UserDTO queryUserById(Integer userId) {
        return userMapper.queryUserById(userId);
    }

    @Override
    public String updateUserPwd(String oldPwd, String newPwd, Integer userId){
        UserDTO user = queryUserById(userId);
        String tempPwd = user.getPassword();

        if (!tempPwd.equals(oldPwd)){
            return "旧密码不正确";
        }

        return userMapper.updateUserPwd(userId, newPwd) ? "修改成功" : "修改失败";
    }

    @Override
    public List<SimpleUserVO> querySimpleUsers() {
        return userMapper.querySimpleUsers();
    }
}
