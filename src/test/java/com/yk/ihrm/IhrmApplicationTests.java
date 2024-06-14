package com.yk.ihrm;

import com.yk.Mapper.UserMapper;
import com.yk.Service.EmployeeService;
import com.yk.VO.EmployeeVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class IhrmApplicationTests {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        List<Integer> list = new LinkedList<>();
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);
        employeeService.updateRole(1, list);
    }
}
