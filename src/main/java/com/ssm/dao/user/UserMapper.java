package com.ssm.dao.user;

import com.ssm.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User getLoginUser(String userCode);

    int savePwd(@Param("map") Map<String, Object> map);

    List<User> getUsersCount(@Param("map") Map<String, Object> map);

    List<User> queryUsers(@Param("map") Map<String, Object> map);

    User queryUserById(@Param("id") int id);

    User queryUserByUserCode(@Param("userCode") String userCode);

    int addUser(User user);

    int modifyUser(User user);

    int delUser(@Param("id") int id);

}
