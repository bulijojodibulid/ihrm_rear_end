package com.yk.Common;

import com.yk.DTO.ImportUserDTO;
import com.yk.Mapper.DepartmentMapper;
import com.yk.Mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AssemblyUser {
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final Map<String, Method> map;

    public AssemblyUser(UserMapper userMapper, DepartmentMapper departmentMapper) throws NoSuchMethodException {
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;

        this.map = new HashMap<String, Method>(){{
            put("用户名(只能中文)", AssemblyUser.class.getDeclaredMethod("setName", ImportUserDTO.class, String.class));
            put("手机号(不能重复)", AssemblyUser.class.getDeclaredMethod("setPhone", ImportUserDTO.class, String.class));
            put("部门(系统中存在的)", AssemblyUser.class.getDeclaredMethod("setDep", ImportUserDTO.class, String.class));
            put("聘用形式(正式与非正式)", AssemblyUser.class.getDeclaredMethod("setForm", ImportUserDTO.class, String.class));
            put("转正日期(2022/10/24)", AssemblyUser.class.getDeclaredMethod("setDate", ImportUserDTO.class, String.class));
        }};
    }

    private String setName(ImportUserDTO user, String value){
        String res = "";
        user.setName(value);
        return res;
    }
    private String setPhone(ImportUserDTO user, String value){
        String res = "";
        // 查询手机号是否存在
        String flag = userMapper.getPhoneByPhone(value);
        if(flag == null){
            user.setPhone(value);
        }else{
            res = "手机号码重复";
        }
        return res;
    }
    private String setDep(ImportUserDTO user, String value){
        String res = "";
        // 查找部门Id
        Integer id = departmentMapper.getIdByName(value);
        if(id != null){
            user.setDepartmentId(id);
        }else{
            res = "部门不存在";
        }
        return res;
    }
    private String setForm(ImportUserDTO user, String value){
        String res = "";
        Integer form = value.equals("正式") ? 1 : 0;
        user.setFormOfEmployment(form);
        return res;
    }
    private String setDate(ImportUserDTO user, String value){
        String res = "";
        LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("MM/dd/yy"));
        user.setTimeOfEntry(localDate);
        return res;
    }

    public String assembly(ImportUserDTO user, String value, String title) throws InvocationTargetException, IllegalAccessException {
        Method method = map.get(title);
        return (String) method.invoke(null, user, value);
    }
}
