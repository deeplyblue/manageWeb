package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.UserInfo;

import java.util.List;

/**
 * Created by lupf on 2016/4/22.
 */
public interface IUserInfoService {

    UserInfo getUserByName(String userName);

    void queryPage(Pagination<UserInfo> pagination, UserInfo userInfo);

    void addNewUser(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception;

    void updateUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception;

    void updateLoginTimeAndIP(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception;

    boolean updateUserInfo(UserInfo userInfo) throws Exception;

    void deleteUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO);

    List<UserInfo> queryAll();

    void auditUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO);

    public String enctryptPwd(String pwd) throws Exception;

    List<UserInfo> queryPwdExpired();

    String errorLoginCount(UserInfo userInfo, String passwordKey);

    List<UserInfo> queryUserLock();

    UserInfo getUserById(String id);

    boolean checkUserName(UserInfo userInfo);

    String generatePwd();

    void sendPwd(String userId, String pwd);
}
