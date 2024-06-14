package com.yk.Controller;

import com.yk.POJO.Result;
import com.yk.POJO.User;
import com.yk.Service.loginService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ihrm")
public class LoginController {
    private final loginService loginService;
    public LoginController(loginService loginService){
        this.loginService = loginService;
    }
    @PostMapping("/login")
    public Result login(@RequestParam String phone,@RequestParam String pwd) {
        String token = loginService.login(phone, pwd);
        return token.isEmpty() ? Result.error("用户名或者密码错误") : Result.success(token);
    }
}
