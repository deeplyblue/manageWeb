package com.oriental.manage.core.utils;

import com.oriental.manage.core.enums.RedisKey;
import com.oriental.manage.core.enums.SessionKey;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.pojo.base.IplimitCfg;
import com.oriental.manage.service.redis.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by lupf on 2016/5/4.
 */
@Component
public class SessionUtils {

    @Autowired(required = false)
    private RedisService redisService;
    private static SessionUtils sessionUtils;

    @PostConstruct
    public void init() {
        sessionUtils = this;
        sessionUtils.redisService = this.redisService;
    }

    public static String getUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        return (String) currentUser.getPrincipal();
    }
    public static void setUserTel(String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.USER_TEL.getCode(), userId);
    }
    public static String getUserTel() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.USER_TEL.getCode());
    }
    public static void setVerifyCode(String random) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
//        session.setAttribute(SessionKey.VERIFY_CODE.getCode(), random);
        sessionUtils.redisService.setCachesData(RedisKey.IMG_VERIFY_CODE.getHashKey() + session.getId(), random, 5);
    }

    public static String getVerifyCode() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
//        return (String) session.getAttribute(SessionKey.VERIFY_CODE.getCode());
        return sessionUtils.redisService.getCachesData(RedisKey.IMG_VERIFY_CODE.getHashKey() + session.getId());
    }

    public static void setSmsVerifyCode(String random) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
//        session.setAttribute(SessionKey.SMS_VERIFY_CODE.getCode(), random);
        sessionUtils.redisService.setCachesData(RedisKey.SMS_VERIFY_CODE.getHashKey() + session.getId(), random, 10);
    }

    public static long getSmsActionTime() throws BusiException {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long seconds = sessionUtils.redisService.getCachesActionTime(RedisKey.SMS_VERIFY_CODE.getHashKey() + session.getId());

        return seconds;
    }

    public static void setUserId(String userId) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.USER_ID.getCode(), userId);
    }

    public static String getSmsVerifyCode() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
//        return (String) session.getAttribute(SessionKey.SMS_VERIFY_CODE.getCode());
        return sessionUtils.redisService.getCachesData(RedisKey.SMS_VERIFY_CODE.getHashKey() + session.getId());
    }

    public static String getMchntCode() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.MCHNT_CODE.getCode());
    }

    public static String getUserId() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.USER_ID.getCode());
    }

    public static void setServerRandom(String destKey) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.SERVER_RANDOM.getCode(), destKey);
    }

    public static String getServerRandom() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.SERVER_RANDOM.getCode());
    }

    public static void setLastLoginTime(String lastLoginTime) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.LAST_LOGIN_TIME.getCode(), lastLoginTime);
    }

    public static String getLastLoginTime() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.LAST_LOGIN_TIME.getCode());
    }

    public static void setLastLoginIpAddress(String lastLoginIpAddress) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.LAST_LOGIN_ADDRESS.getCode(), lastLoginIpAddress);
    }

    public static String getLastLoginIpAddress() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (String) session.getAttribute(SessionKey.LAST_LOGIN_ADDRESS.getCode());
    }

    public static IplimitCfg getIpLimitCfg() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return (IplimitCfg) session.getAttribute(SessionKey.IP_LIMIT_CFG.getCode());
    }

    public static void setIpLimitCfg(IplimitCfg ipLimitCfg) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(SessionKey.IP_LIMIT_CFG.getCode(), ipLimitCfg);
    }
}
