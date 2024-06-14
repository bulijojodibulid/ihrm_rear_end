package com.yk.Mapper;

import com.yk.VO.PermissionVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {
    @Select("SELECT id, type, code, pid, enVisible, name, description FROM ihrm.permission")
    List<PermissionVO> queryAll();

    @Insert("INSERT INTO ihrm.permission (code, " +
            "enVisible, " +
            "name, " +
            "description, pid, type) VALUES (#{code}, " +
            "#{enVisible}, " +
            "#{name}, " +
            "#{description}, #{pid}, #{type})")
    void add(PermissionVO p);

    void del(@Param("ids") List<Integer> ids);

    void update(PermissionVO p);
}
