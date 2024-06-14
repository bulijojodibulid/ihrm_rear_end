package com.yk.Mapper;

import com.yk.VO.DepartmentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("SELECT d.id," +
            "       d.pid," +
            "       d.name as name," +
            "       d.managerId," +
            "       u.name as managerName," +
            "       d.description as introduce," +
            "       d.gmt_create FROM departments d join user u on d.managerId = u.id; ")
    List<DepartmentVO> queryAllDepartment();

    @Insert("INSERT INTO departments(name, description, pid, managerId) VALUES (#{name}, #{desc}, #{pid}, #{managerId})")
    Integer addDepartment(@Param("pid")Integer pid,
                       @Param("name")String name,
                       @Param("desc")String desc,
                       @Param("managerId")Integer managerId);

    Integer updateDepartment(@Param("id") Integer id,
                             @Param("name") String name,
                             @Param("managerId")Integer managerId,
                             @Param("description")String description);

    Integer delDepartment(List<Integer> ids);
    @Select("SELECT id FROM departments WHERE pid=#{id}")
    List<Integer> getId(Integer id);

    @Select("SELECT id FROM departments WHERE name=#{name}")
    Integer getIdByName(String name);

    @Update("UPDATE departments SET managerId=0 WHERE managerId=#{id}")
    Integer updateDepByManagerId(Integer id);
}
