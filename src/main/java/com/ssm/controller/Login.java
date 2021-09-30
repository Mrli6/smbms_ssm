package com.ssm.controller;

import com.ssm.pojo.User;
import com.ssm.service.user.UserSerivce;
import com.ssm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Login {

    @Autowired
    private UserSerivce userSerivce;

    @RequestMapping("/login.do")
    public String login(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String userPassword,
                        HttpServletRequest req, Model model){
        User loginUser = userSerivce.getLoginUser(userCode);
        if(loginUser != null && loginUser.getUserPassword().equals(userPassword)){
            req.getSession().setAttribute(Constant.USER_SESSION, loginUser);
            return "frame";
        }else{
            model.addAttribute("error", "用户名或密码不正确");
            return "forward:login.jsp";
        }
    }
}
