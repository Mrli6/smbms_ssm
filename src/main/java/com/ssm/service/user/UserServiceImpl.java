package com.ssm.service.user;

import com.ssm.dao.user.UserMapper;
import com.ssm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserSerivce {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getLoginUser(String userCode) {
        return userMapper.getLoginUser(userCode);
    }

    @Override
    public int savePwd(Map<String, Object> map) {
        return userMapper.savePwd(map);
    }

    @Override
    public int getUsersCount(Map<String, Object> map) {
        return userMapper.getUsersCount(map).size();
    }

    @Override
    public List<User> queryUsers(Map<String, Object> map) {
        return userMapper.queryUsers(map);
    }

    @Override
    public User queryUserById(int id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public int queryUserByUserCode(String userCode){
        return userMapper.queryUserByUserCode(userCode) != null ? 1 : 0;
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int modifyUser(User user) {
        return userMapper.modifyUser(user);
    }

    @Override
    public int delUser(int id) {
        return userMapper.delUser(id);
    }
}
