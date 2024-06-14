package com.yk.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yk.Mapper.RoleMapper;
import com.yk.Service.RoleService;
import com.yk.VO.PageBean;
import com.yk.VO.RoleVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    public RoleServiceImpl(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleVO> queryAllRole() {
        return roleMapper.queryAllRole();
    }

    @Override
    public PageBean<RoleVO> page(Integer page, Integer pageSize) {
        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(page, pageSize);
        List<RoleVO> roles = queryAllRole();
        Page<RoleVO> p = (Page<RoleVO>) roles;
        return new PageBean<>(p.getTotal(), p.getResult());
    }

    @Override
    public Integer addRole(String name, String description, Integer state) {
        return roleMapper.addRole(name, description, state);
    }

    @Override
    public Integer delRole(Integer id) {
        return roleMapper.delRole(id);
    }

    @Override
    public Integer updateRole(RoleVO role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public List<RoleVO> getEnableRoleList() {
        return roleMapper.getEnableRoleList();
    }
}
