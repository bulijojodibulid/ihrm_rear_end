package com.yk.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleVO {
    private Integer id;
    private String name;
    private String description;
    private Integer state;
}
