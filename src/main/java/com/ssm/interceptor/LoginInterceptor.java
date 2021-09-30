package com.ssm.interceptor;

import com.ssm.util.Constant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在登录时，需要经过 ../login.do 路径，此时还没有注册session，所以需要加一个路径名判断
        if(request.getRequestURI().contains("login.do") || request.getSession().getAttribute(Constant.USER_SESSION) != null){
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/loginfailure.jsp");
        return false;
    }
}
