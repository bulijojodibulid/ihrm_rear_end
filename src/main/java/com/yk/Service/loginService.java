package com.yk.Service;

import com.yk.DTO.UserDTO;

public interface loginService {
    UserDTO queryUser(String phone, String password);
    String login(String phone, String password);
}
