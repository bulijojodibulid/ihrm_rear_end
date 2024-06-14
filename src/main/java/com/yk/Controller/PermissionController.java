package com.yk.Controller;

import com.yk.POJO.Result;
import com.yk.Service.PermissionService;
import com.yk.VO.PermissionVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ihrm")
public class PermissionController {
    private final PermissionService service;
    public PermissionController(PermissionService service){
        this.service = service;
    }
    @GetMapping("/permission")
    public Result queryAll(){
        return Result.success(service.queryAll());
    }

    @PostMapping("/permission")
    public Result add(@RequestBody PermissionVO p){
        service.add(p);
        return Result.success("添加成功");
    }

    @DeleteMapping("/permission/{ids}")
    public Result del(@PathVariable List<Integer> ids){
        service.del(ids);
        return Result.success("删除成功");
    }

    @PutMapping("/permission")
    public Result update(@RequestBody PermissionVO p){
        service.update(p);
        return Result.success("修改成功");
    }
}
