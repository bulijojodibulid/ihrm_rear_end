package com.yk.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Integer id;
    private String phone;
    private String name;
    private String password;
    private Integer departmentId;
    private Integer company_id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
