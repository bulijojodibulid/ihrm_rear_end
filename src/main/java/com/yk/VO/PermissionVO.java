package com.yk.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionVO {
    private Integer id;
    private Integer type;
    private String code;
    private Integer pid;
    private Integer enVisible;
    private String name;
    private String description;
}
