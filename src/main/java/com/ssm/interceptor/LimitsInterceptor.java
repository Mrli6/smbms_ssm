package com.ssm.interceptor;

import com.ssm.pojo.User;
import com.ssm.util.Constant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LimitsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(Constant.USER_SESSION);
        if(user.getUserRole() == 1){
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/nolimits.jsp");
        return false;
    }
}
