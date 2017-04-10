package com.oriental.manage.service.base;

import com.oriental.manage.pojo.base.UserRole;

import java.util.List;

/**
 * Created by lupf on 2016/4/22.
 */
public interface IUserRoleService {

    List<UserRole> getUserRolesByUserId(String userId);

    void insertRoleWithUserId(String userId, String[] roles);

    void deleteRoleWithUserID(String userId);

    List<UserRole> getUserByRole(String roleId);
}
