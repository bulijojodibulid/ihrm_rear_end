package com.yk.DTO;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class CompanyDTO {
    private Integer id;
    private String name;
    private String address;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
