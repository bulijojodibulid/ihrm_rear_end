package com.yk.Service.impl;

import com.yk.Mapper.DepartmentMapper;
import com.yk.Service.DepartmentService;
import com.yk.VO.DepartmentVO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class departmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    public departmentServiceImpl(DepartmentMapper departmentMapper){
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<DepartmentVO> queryAllDepartment() {
        return departmentMapper.queryAllDepartment();
    }

    @Override
    public Integer addDepartment(Integer pid, String name, String desc, Integer managerId) {
        return departmentMapper.addDepartment(pid, name, desc, managerId);
    }

    @Override
    public Integer updateDepartment(Integer id, String name, Integer managerId, String description) {
        return departmentMapper.updateDepartment(id, name, managerId, description);
    }

    @Override
    public Integer delDepartment(List<Integer> ids) {
        return departmentMapper.delDepartment(ids);
    }
}
