package com.yk.Service.impl;

import com.yk.Mapper.PermissionMapper;
import com.yk.Service.PermissionService;
import com.yk.VO.PermissionVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper mapper;
    public PermissionServiceImpl(PermissionMapper mapper){
        this.mapper = mapper;
    }
    @Override
    public List<PermissionVO> queryAll() {
        return mapper.queryAll();
    }

    @Override
    public void add(PermissionVO p) {
        mapper.add(p);
    }

    @Override
    public void del(List<Integer> ids) {
        mapper.del(ids);
    }

    @Override
    public void update(PermissionVO p) {
        mapper.update(p);
    }
}
