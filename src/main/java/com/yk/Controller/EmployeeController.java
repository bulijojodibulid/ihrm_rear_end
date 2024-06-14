package com.yk.Controller;

import com.yk.DTO.ImportUserDTO;
import com.yk.POJO.Result;
import com.yk.Service.EmployeeService;
import com.yk.VO.EmployeeVO;
import com.yk.VO.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/ihrm")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){
        this.service = service;
    }

    @GetMapping("/emp")
    public Result getEmp(Integer currentPage, Integer pageSize, String keyword, Integer departmentId){
        PageBean<EmployeeVO> res = service.page(currentPage, pageSize, keyword, departmentId);
        return Result.success(res);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportEmp() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 获取excel数据流
        ByteArrayOutputStream out = service.export();
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }

    @GetMapping("/template")
    public ResponseEntity<byte[]> getTemplate() throws IOException {
        ByteArrayOutputStream template = service.getTemplate();
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(template.toByteArray());
    }

    @PostMapping("/template")
    public Result importEmp(@RequestParam("file") MultipartFile file) throws IOException {
        String res = service.importEmp(file);
        return  Result.success(res);
    }

    @DeleteMapping("/employee/{id}")
    public Result delEmp(@PathVariable Integer id){
        service.delUser(id);
        return Result.success("删除成功");
    }

    @PostMapping("/employee")
    public Result addEmp(@RequestBody ImportUserDTO user){
        Integer res = service.addUser(user);
        return res.equals(1)?Result.success("添加成功"):Result.error("添加失败，手机号重复");
    }

    @GetMapping("/employee")
    public Result getOneEmp(Integer id){
        EmployeeVO res = service.getOneEmp(id);
        return Result.success(res);
    }

    @PutMapping("/employee")
    public Result updateEmp(@RequestBody EmployeeVO emp){
        Integer res = service.updateEmp(emp);
        return res.equals(1) ? Result.success("保存成功") : Result.error("保存失败");
    }

    @PutMapping("/employee/{empId}/{roleIds}")
    public Result updateRole(@PathVariable Integer empId, @PathVariable List<Integer> roleIds){
        log.info("接收到的roleIds值为:"+roleIds);
        service.updateRole(empId, roleIds);
        return Result.success();
    }

    @GetMapping("/roleIds/{empId}")
    public Result getRoleIds(@PathVariable Integer empId){
        List<Integer> roleIds = service.getRoleIds(empId);
        return Result.success(roleIds);
    }
}
