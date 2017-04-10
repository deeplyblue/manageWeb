package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.authorize.UserRealm;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.UserRoleMapper;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.pojo.base.UserRole;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.manage.service.base.IUserRoleService;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by lupf on 2016/4/22.
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private UserRealm leopardRealm;

    @Override
    public List<UserRole> getUserRolesByUserId(String userId) {
        UserRole userRoleQuery = new UserRole();
        userRoleQuery.setUserId(userId);
        return userRoleMapper.selectBySelective(userRoleQuery);
    }

    @Override
    public void insertRoleWithUserId(String userId, String[] roles) {
        List<UserRole> list = getUserRolesByUserId(userId);
        if (list != null && list.size() > 0) {
            for (UserRole role : list) {
                userRoleMapper.deleteByPrimaryKey(role.getId());
            }
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setUserName(SessionUtils.getUserName());
        if (roles != null && roles.length > 0) {
            for (String roleId : roles) {
                userRole.setId(UUID.randomUUID().toString());
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            }
        }

        //立即更新用户权限
        UserInfo userInfo = userInfoService.getUserById(userId);

        leopardRealm.doClearCache(userInfo.getUserName());
    }

    @Override
    public void deleteRoleWithUserID(String userId) {
        userRoleMapper.deleteByPrimaryUserid(userId);
    }

    @Override
    public List<UserRole> getUserByRole(String roleId) {
        return userRoleMapper.getUserByRole(roleId);
    }
}
