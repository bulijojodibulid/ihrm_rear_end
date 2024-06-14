package com.yk.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImportUserDTO {
    private String name;
    private String phone;
    private Integer departmentId;
    private Integer formOfEmployment;
    private LocalDate timeOfEntry;
}
