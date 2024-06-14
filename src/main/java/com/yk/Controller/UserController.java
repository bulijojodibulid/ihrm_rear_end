package com.yk.Controller;

import com.yk.POJO.Result;
import com.yk.Service.UserService;
import com.yk.VO.UserVO;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ihrm")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public Result queryUserInfo(HttpServletRequest req){
        Integer userId = (Integer)req.getAttribute("userId");
        UserVO userInfo = userService.queryUserInfo(userId);
        if(userInfo != null){
            return Result.success(userInfo);
        }else{
            return Result.error("用户信息获取失败");
        }
    }

    @PutMapping("/users")
    public Result updateUserPwd(HttpServletRequest req, @RequestParam String oldPwd, @RequestParam String newPwd){
        Integer userId = (Integer) req.getAttribute("userId");
        String res = userService.updateUserPwd(oldPwd, newPwd, userId);
        return Result.success(res);
    }

    @GetMapping("/simpleUsers")
    public Result querySimpleUsers(){
        return Result.success(userService.querySimpleUsers());
    }
}
