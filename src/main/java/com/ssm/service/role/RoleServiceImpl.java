package com.ssm.service.role;

import com.ssm.dao.role.RoleMapper;
import com.ssm.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryAllRoles() {
        return roleMapper.queryAllRoles();
    }
}
