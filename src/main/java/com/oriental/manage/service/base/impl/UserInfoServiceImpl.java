package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.enums.UserStatus;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.smsUtils.SmsUtils;
import com.oriental.manage.core.utils.EncryptionTools;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.UserInfoMapper;
import com.oriental.manage.dao.base.UserRoleMapper;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.pojo.base.UserRole;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.manage.service.redis.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lupf on 2016/4/22.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserInfoMapper userInfoMapper;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired(required = false)
    private RedisService redisService;
    @Autowired
    private SmsUtils smsUtils;

    private static int MAX_PASSWORD_ERROR_COUNT = 5;

    @Override
    public UserInfo getUserByName(String userName) {
        UserInfo userInfoQuery = new UserInfo();
        userInfoQuery.setUserName(userName);
        List<UserInfo> list = userInfoMapper.selectBySelective(userInfoQuery);
        return list == null || list.size() == 0 ? null : list.get(0);
    }

    @Override
    public void queryPage(Pagination<UserInfo> pagination, UserInfo userInfo) {

        List<UserInfo> list = userInfoMapper.selectBySelective(userInfo);

        if (list != null && list.size() > 0) {
            UserRole userRole = new UserRole();
            for (UserInfo user : list) {
                userRole.setUserId(user.getUserId());
                List<UserRole> userRoleList = userRoleMapper.selectBySelective(userRole);
                user.setUserRoles(userRoleList);

                //剥离隐私信息
                user.setUserPwd(null);
            }
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void addNewUser(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception {
        userInfo.setUserId(UUID.randomUUID().toString());
        userInfo.setReadonlyFlag("0");
        userInfo.setUserPwd(enctryptPwd(userInfo.getUserPwd()));
//        userInfo.setPwdLastUpd(new Date());
        if (StringUtils.isBlank(userInfo.getCompanyCode())) {
            userInfo.setCompanyCode("888888888");
        } else {
            userInfo.setDeptCode(userInfo.getCompanyCode());
        }

        userInfo.setUserStatus(UserStatus.INITIAL.getCode());
        Date now = new Date();
        userInfo.setCreateTime(now);
        userInfo.setCreator(SessionUtils.getUserName());
        userInfo.setLastUpdTime(now);

        if (userInfoMapper.insertSelective(userInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception {

        UserInfo info = this.getUserByName(userInfo.getUserName());
        userInfo.setModifier(SessionUtils.getUserName());
        userInfo.setLastUpdTime(new Date());
        if (StringUtils.isNotBlank(userInfo.getUserPwd())) {
            userInfo.setUserPwd(enctryptPwd(userInfo.getUserPwd()));
            if(info.getUserPwd().equals(userInfo.getUserPwd())){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("不能使用旧密码");
            }
//          这里更新用户密码修改日期
            else{
                //密码过期修改用户状态
                if(info.getUserStatus().equals("05")){
                    userInfo.setUserStatus("02");
                }
            userInfo.setPwdLastUpd(new Date());
                if (this.updateUserInfo(userInfo)) {
                    responseDTO.setSuccess(true);
                } else {
                    responseDTO.setSuccess(false);
                }
            }
        }else{
            userInfo.setPwdLastUpd(new Date());
            if (this.updateUserInfo(userInfo)) {
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setSuccess(false);
            }
        }


    }

    public boolean updateUserInfo(UserInfo userInfo) throws Exception {
        return userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0 ? true : false;
    }

    @Override
    public void updateLoginTimeAndIP(UserInfo userInfo, ResponseDTO<String> responseDTO) throws Exception {
        userInfo.setModifier(SessionUtils.getUserName());
        userInfo.setLastUpdTime(new Date());
        if (userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    /**
     * 删除用户，同时删除用户-角色关系
     * 不留脏数据
     *
     * @param userInfo
     * @param responseDTO
     */
    @Override
    public void deleteUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO) {
        if (userInfoMapper.deleteByPrimaryKey(userInfo.getUserId()) > 0) {
            userRoleMapper.deleteByPrimaryUserid(userInfo.getUserId());
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void auditUserInfo(UserInfo userInfo, ResponseDTO<String> responseDTO) {
        UserInfo record = new UserInfo();
        record.setUserId(userInfo.getUserId());
        record.setUserStatus(userInfo.getUserStatus());
        Date now = new Date();
        record.setLastUpdTime(now);
        record.setAuditDate(now);
        record.setAuditor(SessionUtils.getUserName());

        if (userInfoMapper.updateByPrimaryKeySelective(record) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public List<UserInfo> queryAll() {
        return userInfoMapper.queryAll();
    }

    public String enctryptPwd(String pwd) throws Exception {
        String desStr = new String(pwd.getBytes(), "UTF-8");
        byte[] enc = EncryptionTools.desEncrypt(EncryptionTools.genDESKey(), desStr.getBytes());
        return EncryptionTools.base64Encode(enc);
    }

    /**
     * 查询三个月未修改密码的正常账户
     *
     * @return
     */
    @Override
    public List<UserInfo> queryPwdExpired() {
        DateTime dateTime = DateTime.now();

        UserInfo queryBean = new UserInfo();
        queryBean.setUserStatus(UserStatus.NORMAL.getCode());
        queryBean.setPwdLastUpd(dateTime.minusMonths(3).toDate());

        return userInfoMapper.queryPwdExpired(queryBean);

    }

    /**
     * 记录错误登入次数 超过次数锁定账号
     *
     * @param userInfo
     * @param passwordKey
     * @return
     */
    @Override
    public String errorLoginCount(UserInfo userInfo, String passwordKey) {

        String PASSWORD_KEY = passwordKey + userInfo.getUserId();
        String data = redisService.getCachesData(PASSWORD_KEY);
        if (data == null) {
            redisService.setCachesData(PASSWORD_KEY, "1", 24 * 60);
            return "用户名或密码错误";
        } else {
            int count = Integer.valueOf(data);
            if (count >= 1 && count < 5) {
                count++;
                //重新设置存活时间
                Long time = redisService.getCachesActionTime(PASSWORD_KEY);
                redisService.setCachesData(PASSWORD_KEY, count + "", time.intValue() / 60);
                return "您还有" + (MAX_PASSWORD_ERROR_COUNT - count + 1) + "次，输入机会";
            } else {
                //锁定账号
                UserInfo info = new UserInfo();
                info.setUserId(userInfo.getUserId());
                info.setUserStatus(UserStatus.LOCKED.getCode());
                info.setStatusLastUpd(new Date());
                info.setModifier("sys");
                userInfoMapper.updateByPrimaryKeySelective(info);
                redisService.delCachesData(PASSWORD_KEY);
                return "账号被锁定，请明天再试";
            }


        }

    }

    /**
     * 查询锁定1天的用户
     *
     * @return
     */
    @Override
    public List<UserInfo> queryUserLock() {

        DateTime dateTime = DateTime.now();
        UserInfo queryBean = new UserInfo();
        queryBean.setUserStatus(UserStatus.LOCKED.getCode());
        queryBean.setStatusLastUpd(dateTime.minusDays(1).toDate());
        return userInfoMapper.queryUserLock(queryBean);
    }

    @Override
    public UserInfo getUserById(String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean checkUserName(UserInfo userInfo) {
        List<UserInfo> list = userInfoMapper.checkUserName(userInfo);
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String generatePwd() {
        List<String> list = new ArrayList<>();
        list.add("$");
        list.add("D");
        list.add("f");
        do {
            list.add(RandomStringUtils.randomAlphanumeric(1));
        } while (list.size() < 10);

        Collections.shuffle(list);

        StringBuilder builder = new StringBuilder();
        for (String alpha : list) {
            builder.append(alpha);
        }

        return builder.toString();
    }

    @Override
    public void sendPwd(String userId, String pwd) {
        String template = "您的登录密码已重置：{userPwd},请尽快登录并修改密码。【亿付数字】";

        UserInfo userInfo = this.getUserById(userId);

        if (userInfo != null && StringUtils.isNotBlank(pwd)) {
            String temp = template.replace("{userPwd}", pwd);
            smsUtils.send(userInfo.getUserMobile(), temp);
        }
    }
}
