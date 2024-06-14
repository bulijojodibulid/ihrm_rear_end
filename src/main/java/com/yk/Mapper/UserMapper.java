package com.yk.Mapper;

import com.yk.DTO.ImportUserDTO;
import com.yk.DTO.UserDTO;
import com.yk.VO.EmployeeVO;
import com.yk.VO.SimpleUserVO;
import com.yk.VO.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE phone=#{phone} AND password=#{password}")
    UserDTO queryUser(@Param("phone") String phone, @Param("password") String password);

    UserVO queryUserInfo(Integer userId);

    @Select("SELECT * FROM user WHERE id=#{userId}")
    UserDTO queryUserById(Integer userId);

    @Update("UPDATE user SET password=#{pwd} WHERE id=#{id}")
    boolean updateUserPwd(@Param("id")Integer id, @Param("pwd") String pwd);

    @Select("SELECT * FROM user")
    List<SimpleUserVO> querySimpleUsers();
    List<EmployeeVO> getEmployee(@Param("ids") List<Integer> ids, @Param("keyword") String keyword);

    @Insert("INSERT INTO user (phone, " +
            "name, " +
            "department_id, " +
            "formOfEmployment, " +
            "timeOfEntry) VALUES (#{phone}, " +
            "#{name}," +
            "#{departmentId}," +
            "#{formOfEmployment}," +
            "#{timeOfEntry})")
    Integer addUser(ImportUserDTO user);

    @Select("SELECT phone FROM user WHERE phone=#{phone}")
    String getPhoneByPhone(String phone);

    @Delete("DELETE FROM user WHERE id=#{id}")
    Integer delUser(Integer id);
}
