package com.yk.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentVO {
    Integer id;
    Integer pid;
    String name;
    Integer managerId;
    String managerName;
    String introduce;
    LocalDateTime gmtCreate;
}
