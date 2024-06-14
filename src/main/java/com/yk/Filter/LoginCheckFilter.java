package com.yk.Filter;

import com.alibaba.fastjson.JSONObject;
import com.yk.POJO.Result;
import com.yk.Utils.JwtUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Integer userId = null;

        // 强制转换，以便获取URL路径
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setCharacterEncoding("utf-8");

        //1.获取请求URL
        String url = req.getRequestURL().toString();

        //2.判断请求URL是否包含login
        if(url.contains("login")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //3.获取请求头中的令牌(token)
        String jwt = req.getHeader("token");

        //4.判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            Result result = Result.error("未登录");
            String noLogin = JSONObject.toJSONString(result);
            resp.getWriter().write(noLogin);
            return;
        }

        //5.判断令牌是否失效
        try {
            // 5.1 解析JWT获取用户id
            userId = JwtUtils.parseJwt(jwt);
        }catch (Exception e){
            Result result = Result.error("账号异常，请重新登录");
            String fail = JSONObject.toJSONString(result);
            resp.setStatus(401);
            resp.getWriter().write(fail);
            return;
        }
        //6. 将用户id设置进请求参数中
        servletRequest.setAttribute("userId", userId);
        //7.放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
