package com.ssm.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import com.ssm.dao.user.UserMapper;
import com.ssm.pojo.Role;
import com.ssm.pojo.User;
import com.ssm.service.role.RoleService;
import com.ssm.service.user.UserSerivce;
import com.ssm.util.Constant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSerivce userSerivce;
    @Autowired
    private RoleService roleService;


    // 查询部分或所有===================================================================================================
    @RequestMapping("/user.do")
    public String goUser(@RequestParam(value = "queryUserName", required = false) String queryUserName,
                         @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
                         @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model){
        int currentPageNo = 1;
        if (pageIndex != null){
            currentPageNo = Integer.parseInt(pageIndex);
        }
        model.addAttribute("currentPageNo", currentPageNo);


        List<Role> roleList = roleService.queryAllRoles();
        model.addAttribute("roleList", roleList);


        Map<String, Object> map = new HashMap<>();
        if(queryUserName != null){
            queryUserName = queryUserName.trim();
        }
        model.addAttribute("queryUserName", queryUserName);


        int id = 0;
        if(queryUserRole != null){
            id = Integer.parseInt(queryUserRole);
        }
        model.addAttribute("queryUserRole", queryUserRole);


        map.put("queryUserName", queryUserName);
        map.put("queryUserRole", id);
        int totalCount = userSerivce.getUsersCount(map);
        model.addAttribute("totalCount", totalCount);


        int pageSize = 5;
        map.put("start",(currentPageNo-1)*pageSize);
        map.put("size", pageSize);
        List<User> userList = userSerivce.queryUsers(map);
        model.addAttribute("userList", userList);


        int totalPageCount = totalCount % pageSize != 0 ? totalCount/pageSize+1 : totalCount/pageSize;
        model.addAttribute("totalPageCount", totalPageCount);


        return "userlist";
    }


    // 查看详细=========================================================================================================
    @RequestMapping("/viewuser")
    public String viewUser(@RequestParam("uid") String id, Model model){
        User user = userSerivce.queryUserById(Integer.parseInt(id));
        model.addAttribute("user", user);

        return "userview";
    }


    // 增加=============================================================================================================
    @RequestMapping("/useradd.do")
    public String goAdd(){
        return "useradd";
    }

    @RequestMapping("/getrolelist")
    @ResponseBody
    public String getRoleList() throws JsonProcessingException {
        List<Role> roleList = roleService.queryAllRoles();

        return new ObjectMapper().writeValueAsString(roleList);
    }

    @RequestMapping("/ucexist")
    @ResponseBody
    public String ucexist(@RequestParam("userCode") String userCode) throws JsonProcessingException {
        Map<String, String> resultMap = new HashMap<>();

        if(StringUtils.isNullOrEmpty(userCode)){
            resultMap.put("userCode", "exist");
        } else{
            if(userSerivce.queryUserByUserCode(userCode) == 1){
                resultMap.put("userCode", "exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }

        return new ObjectMapper().writeValueAsString(resultMap);
    }

    // 不能使用User直接传参，会类型转换报错
    @RequestMapping("useradd")
    public String addUser(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
        User user = new User();
        user.setUserCode(req.getParameter("userCode"));
        user.setUserName(req.getParameter("userName"));
        user.setUserPassword(req.getParameter("userPassword"));
        user.setGender(Integer.valueOf(req.getParameter("gender")));
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        user.setUserRole(Integer.valueOf(req.getParameter("userRole")));
        user.setCreatedBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        user.setCreationDate(new Date());

        if(userSerivce.addUser(user) > 0){
            return "redirect:/user/user.do";
        }else{
            return "useradd";
        }

    }


    // 修改=============================================================================================================
    @RequestMapping("/modifyuser")
    public String goModify(@RequestParam("uid") String id, Model model) throws ParseException {
        User user = userSerivce.queryUserById(Integer.parseInt(id));
        model.addAttribute("user", user);

        return "usermodify";
    }

    @RequestMapping("modifysave")
    public String modifySave(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
        User user = new User();
        user.setId(Integer.valueOf(req.getParameter("uid")));
        user.setUserName(req.getParameter("userName"));
        user.setGender(Integer.valueOf(req.getParameter("gender")));
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birthday")));
        user.setPhone(req.getParameter("phone"));
        user.setAddress(req.getParameter("address"));
        user.setUserRole(Integer.valueOf(req.getParameter("userRole")));
        user.setModifyBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        if(userSerivce.modifyUser(user) > 0){
            return "redirect:/user/user.do";
        }else{
            return "usermodify";
        }
    }

    // 删除=============================================================================================================
    @RequestMapping("/deluser")
    @ResponseBody
    public String delUser(@RequestParam("uid") String uid) throws JsonProcessingException {
        Map<String, Object> resultMap = new HashMap<>();

        int id = 0;
        if(!StringUtils.isNullOrEmpty(uid)){
            id = Integer.parseInt(uid);
        }

        if(id <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            if(userSerivce.delUser(id) > 0){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }

        return new ObjectMapper().writeValueAsString(resultMap);
    }


    @Test
    public void test() throws ParseException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        User user = userMapper.queryUserById(20);
        System.out.println(user.getBirthday());
        String format = new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday());
        System.out.println(format);

        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(format));
    }

}
