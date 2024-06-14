package com.yk.Service;

import com.yk.DTO.UserDTO;
import com.yk.VO.SimpleUserVO;
import com.yk.VO.UserVO;

import java.util.List;

public interface UserService {
    UserVO queryUserInfo(Integer userId);

    UserDTO queryUserById(Integer userId);

    String updateUserPwd(String oldPwd, String newPwd, Integer userId);

    List<SimpleUserVO> querySimpleUsers();
}
