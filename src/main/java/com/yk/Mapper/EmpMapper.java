package com.yk.Mapper;

import com.yk.VO.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper {
    @Select("SELECT id, phone, name, department_id, formOfEmployment, workNumber, timeOfEntry FROM ihrm.user WHERE id=#{id}")
    EmployeeVO getOneEmp(Integer id);

    Integer updateEmp(EmployeeVO emp);
    @Select("SELECT role_id FROM ihrm.user_role_relations WHERE user_id=#{id}")
    List<Integer> getRoleListIdsByEmpId(Integer id);
    void addRoles(@Param("roleIds") List<Integer> roleIds, @Param("empId") Integer empId);
    void delRoles(@Param("roleIds") List<Integer> roleIds, @Param("empId") Integer empId);
}
