package com.yk.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    Integer id;
    String phone;
    String password;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModify;
}
