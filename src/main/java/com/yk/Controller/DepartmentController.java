package com.yk.Controller;

import com.yk.POJO.Result;
import com.yk.Service.DepartmentService;
import com.yk.VO.DepartmentVO;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.ResolutionSyntax;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ihrm")
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public Result queryAllDepartment(){
        List<DepartmentVO> departments = departmentService.queryAllDepartment();
        return Result.success(departments);
    }

    @PostMapping("/department")
    public Result addDepartment(@RequestParam Integer pid,
                                   @RequestParam String name,
                                   @RequestParam String introduce,
                                   @RequestParam Integer managerId){
        Integer flag = departmentService.addDepartment(pid, name, introduce, managerId);
        return flag.equals(1) ? Result.success("添加成功") : Result.error("添加失败");
    }

    @PutMapping("/department")
    public Result updateDepartment(@RequestParam Integer id,
                                   @RequestParam String name,
                                   @RequestParam Integer managerId,
                                   @RequestParam("introduce") String description){
        Integer flag = departmentService.updateDepartment(id, name, managerId, description);
        return flag.equals(1) ? Result.success("修改成功") : Result.error("修改失败，请稍后重试");
    }

    @DeleteMapping("/department")
    public Result delDepartment(String ids){
        List<Integer> ids1 = Arrays.stream(ids.split(","))
                .map(String::trim) // 去除可能存在的空格
                .map(Integer::parseInt) // 将字符串转换为int类型
                .collect(Collectors.toList());
        Integer res = departmentService.delDepartment(ids1);
        return !res.equals(0) ? Result.success("删除成功") : Result.error("删除失败，请重试");
    }
}
