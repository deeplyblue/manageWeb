package com.oriental.manage.service.base.impl;

import com.oriental.manage.dao.base.UserInfoMapper;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IUserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lupf on 2016/4/13.
 */
@Slf4j
@Service
public class UserLoginServiceImpl implements IUserLoginService {

    @Autowired private UserInfoMapper userInfoMapper;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public boolean validateUserLogin(UserInfo userInfo) throws Exception {
        UserInfo userInfoQuery = new UserInfo();
        userInfoQuery.setUserName(userInfo.getUserName());

        List<UserInfo> userInfos = this.userInfoMapper.selectBySelective(userInfoQuery);
        if (userInfos.size() != 1){
            log.info("查询不到该登录用户,用户名：{},查询到：{}个",userInfo.getUserName(),userInfos.size());
            return false;
        }

        if (userInfo.getUserPwd().equals(userInfos.get(0).getUserPwd())){
            log.info("用户：{},登录成功",userInfo.getUserName());
            return true;
        }

        return false;
    }
}
