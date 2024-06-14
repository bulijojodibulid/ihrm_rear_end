package com.yk.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVO {
    private Integer userId;
    private String mobile;
    private String userName;
    private Integer companyId;
    private String companyName;
    private Integer departmentId;
    private String departmentName;
}
