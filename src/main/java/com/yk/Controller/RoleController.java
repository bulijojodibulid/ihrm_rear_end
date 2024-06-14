package com.yk.Controller;

import com.yk.POJO.Result;
import com.yk.Service.RoleService;
import com.yk.VO.PageBean;
import com.yk.VO.RoleVO;
import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ihrm")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    @GetMapping("/role")
    public Result queryAllRole(Integer page, Integer pageSize){
        PageBean<RoleVO> res = roleService.page(page, pageSize);
        return Result.success(res);
    }
    @PostMapping("/role")
    public Result addRole(@RequestParam String name, @RequestParam String description, @RequestParam Integer state){
        Integer res = roleService.addRole(name, description, state);
        return res.equals(1)?Result.success("添加成功"):Result.error("添加失败");
    }

    @DeleteMapping("/role/{id}")
    public Result delRole(@PathVariable Integer id){
        Integer res = roleService.delRole(id);
        return res.equals(1)?Result.success("删除成功"):Result.error("删除失败");
    }

    @PutMapping("/role")
    public Result updateRole(@RequestBody RoleVO role){
        Integer res = roleService.updateRole(role);
        return res.equals(1)? Result.success("修改成功"):Result.error("修改失败");
    }

    @GetMapping("/enableRole")
    public Result getEnableRoleList(){
        return Result.success(roleService.getEnableRoleList());
    }
}
