package com.oriental.manage.service.base;

import com.oriental.manage.pojo.base.UserInfo;

/**
 * Created by lupf on 2016/4/13.
 */
public interface IUserLoginService {

    boolean validateUserLogin(UserInfo userInfo) throws Exception;
}
