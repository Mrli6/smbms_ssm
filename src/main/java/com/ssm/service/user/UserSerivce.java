package com.ssm.service.user;

import com.ssm.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserSerivce {
    User getLoginUser(String userCode);

    int savePwd(Map<String, Object> map);
    
    int getUsersCount(Map<String, Object> map);

    List<User> queryUsers(Map<String, Object> map);

    User queryUserById(int id);

    int queryUserByUserCode(String userCode);

    int addUser(User user);

    int modifyUser(User user);

    int delUser(int id);
}
