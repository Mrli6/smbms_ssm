package com.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import com.ssm.pojo.User;
import com.ssm.service.user.UserSerivce;
import com.ssm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pwd")
public class PwdController {

    @Autowired
    private UserSerivce userSerivce;

    @RequestMapping("/pwdmodify.do")
    public String goPwdModify(){
        return "pwdmodify";
    }

    @RequestMapping("/pwdmodify")
    @ResponseBody
    public String pwdModify(@RequestParam("oldpassword") String oldpassword, HttpServletRequest req) throws JsonProcessingException {
        User user = (User)req.getSession().getAttribute(Constant.USER_SESSION);

        Map<String, String> resultMap = new HashMap<>();
        if(user == null){
            resultMap.put("result", "sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){
            resultMap.put("result", "error");
        }else{
            if(user.getUserPassword().equals(oldpassword)){
                resultMap.put("result", "true");
            }else{
                resultMap.put("result", "false");
            }
        }

        return new ObjectMapper().writeValueAsString(resultMap);
    }

    @RequestMapping("/savepwd")
    public void savePwd(@RequestParam("newpassword") String newpassword, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User)req.getSession().getAttribute(Constant.USER_SESSION);
        if(user != null && !StringUtils.isNullOrEmpty(newpassword)){
            Map<String, Object> map = new HashMap<>();
            map.put("password", newpassword);
            map.put("id", user.getId());
            int flag = userSerivce.savePwd(map);
            if(flag == 1){
                req.setAttribute(Constant.MESSAGE, "修改成功, 请重新登录");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else{
                req.setAttribute(Constant.MESSAGE, "修改失败");
            }
        }else {
            req.setAttribute(Constant.MESSAGE, "账号过期或者新密码有问题");
        }

        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }





}
