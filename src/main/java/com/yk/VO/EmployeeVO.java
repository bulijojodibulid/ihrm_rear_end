package com.yk.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeVO {
    private Integer id;
    private String phone;
    private String name;
    private Integer formOfEmployment;
    private String workNumber;
    private Integer departmentId;
    private String departmentName;
    private LocalDate timeOfEntry;
}
