package com.yk.Service;

import com.yk.VO.PageBean;
import com.yk.VO.RoleVO;

import java.util.List;

public interface RoleService {
    List<RoleVO> queryAllRole();

    PageBean<RoleVO> page(Integer page, Integer pageSize);
    Integer addRole(String name, String description, Integer state);
    Integer delRole(Integer id);
    Integer updateRole(RoleVO role);
    List<RoleVO> getEnableRoleList();
}
