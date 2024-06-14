package com.yk.Service;

import com.yk.VO.PermissionVO;

import java.util.List;

public interface PermissionService {
    List<PermissionVO> queryAll();
    void add(PermissionVO p);

    void del(List<Integer> ids);
    void update(PermissionVO p);
}
