package com.yk.Mapper;

import com.yk.VO.RoleVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM ihrm.role")
    List<RoleVO> queryAllRole();

    @Insert("INSERT INTO ihrm.role(name, description, state) VALUES (#{name}, #{description}, #{state})")
    Integer addRole(@Param("name") String name,
                    @Param("description") String description,
                    @Param("state") Integer state);

    @Delete("DELETE FROM ihrm.role WHERE id=#{id}")
    Integer delRole(Integer id);

    @Update("UPDATE ihrm.role SET name=#{name}, state=#{state}, description=#{description} WHERE id=#{id}")
    Integer updateRole(RoleVO role);

    @Select("SELECT id, name, description FROM ihrm.role WHERE state=1")
    List<RoleVO> getEnableRoleList();
}
