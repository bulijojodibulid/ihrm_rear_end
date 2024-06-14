package com.yk.Service;

import com.yk.DTO.ImportUserDTO;
import com.yk.VO.EmployeeVO;
import com.yk.VO.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface EmployeeService {
    PageBean<EmployeeVO> page(Integer page, Integer pageSize, String keyword, Integer departmentId);
    ByteArrayOutputStream export() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    ByteArrayOutputStream getTemplate() throws IOException;
    String importEmp(MultipartFile file) throws IOException;

    void delUser(Integer id);
    Integer addUser(ImportUserDTO user);

    EmployeeVO getOneEmp(Integer id);

    Integer updateEmp(EmployeeVO emp);

    void updateRole(Integer empId, List<Integer> newRoleIds);

    List<Integer> getRoleIds(Integer empId);
}
