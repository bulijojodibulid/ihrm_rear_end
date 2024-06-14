package com.yk.Service;

import com.yk.VO.DepartmentVO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentVO> queryAllDepartment();

    Integer addDepartment(Integer pid, String name, String desc, Integer managerId);
    Integer updateDepartment(Integer id, String name, Integer managerId, String description);
    Integer delDepartment(List<Integer> ids);
}
