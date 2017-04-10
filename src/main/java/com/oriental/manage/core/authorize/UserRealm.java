package com.oriental.manage.core.authorize;

import com.oriental.manage.core.enums.SessionKey;
import com.oriental.manage.core.enums.UserStatus;
import com.oriental.manage.core.enums.UserType;
import com.oriental.manage.core.exception.LoginRepeatException;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.ResourceInfo;
import com.oriental.manage.pojo.base.RoleInfo;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IResourceInfoService;
import com.oriental.manage.service.base.IRoleInfoService;
import com.oriental.manage.service.base.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lupf on 2016/4/22.
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRoleInfoService roleInfoService;
    @Autowired
    private IResourceInfoService resourceInfoService;
    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("-----------------获取用户授权------------------");

        String userName = (String) principalCollection.getPrimaryPrincipal();

        UserInfo userInfo = userInfoService.getUserByName(userName);

        List<RoleInfo> roleInfoList = roleInfoService.getRolesByUserId(userInfo.getUserId());
        List<ResourceInfo> resourceInfoList = resourceInfoService.getResourceWithUserId(userInfo.getUserId());

        Set<String> roles = new HashSet<String>();
        Set<ResourceInfo> menus = new HashSet<ResourceInfo>();
        Set<ResourceInfo> operations = new HashSet<ResourceInfo>();
        Set<String> permission = new HashSet<String>();

        for (RoleInfo role : roleInfoList) {
            roles.add(role.getRoleName());
        }
        //将用户类型添加为角色
        if (StringUtils.equals(userInfo.getUserType(), "02")) {
            roles.add(UserType.MERCHANT_USER.getCode());
        } else if (StringUtils.equals(userInfo.getUserType(), "01")) {
            roles.add(UserType.SYS_USER.getCode());
        }

        for (ResourceInfo info : resourceInfoList) {
            if (StringUtils.equals(info.getRsrcStatus(), "A") && StringUtils.equals(info.getRsrcVisableFlag(), "1")) {
                if (StringUtils.equals(info.getRsrcType(), "02")) {
                    operations.add(info);
                    if (StringUtils.isNotBlank(info.getRsrcOperation())) {
                        permission.add(info.getRsrcOperation());
                    } else {
                        log.info("按钮操作未配置rsrcCode：{}", info.getRsrcCode());
                    }
                } else {
                    menus.add(info);
                    if (StringUtils.isNotBlank(info.getRsrcOperation())) {
                        permission.add(info.getRsrcOperation());
                    } else {
                        log.info("菜单操作未配置rsrcCode：{}", info.getRsrcCode());
                    }
                }
            }
        }

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();

        /**
         * 添加打印授权信息
         * @TODO 验证测试环境，偶尔出现操作权限丢失问题
         */
        log.info("用户：{},菜单：{}个-{}", currentUser.getPrincipal(), menus.size(), menus);
        log.info("用户：{},角色：{}个-{}", currentUser.getPrincipal(), roles.size(), roles);
        log.info("用户：{},权限：{}个-{}", currentUser.getPrincipal(), permission.size(), permission);

        session.setAttribute(SessionKey.MENU.getCode(), menus);
        session.setAttribute(SessionKey.OPERATION.getCode(), operations);
        session.setAttribute(SessionKey.MCHNT_CODE.getCode(), userInfo.getCompanyCode());
        SessionUtils.setUserId(userInfo.getUserId());
        SessionUtils.setUserTel(userInfo.getUserMobile());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roles);
        authorizationInfo.addStringPermissions(permission);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException, LoginRepeatException {
        log.debug("-----------登录用户认证---------------");

        ForceLoginToken forceLoginToken = (ForceLoginToken) authenticationToken;

        String userName = (String) authenticationToken.getPrincipal();
        UserInfo userInfo = userInfoService.getUserByName(userName);

        if (null == userInfo) {
            throw new UnknownAccountException("没有找到该账号");
        } else if (StringUtils.equals(UserStatus.STOPPED.getCode(), userInfo.getUserStatus())) {
            throw new LockedAccountException("密码过期,请重置密码后登陆");
        } else if (!StringUtils.equals(UserStatus.NORMAL.getCode(), userInfo.getUserStatus())) {
            throw new LockedAccountException("账号状态不可用");
        }

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session : sessions) {
                SimplePrincipalCollection principals = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                log.info("当前登录用户:{}", userName);
                if (principals != null) {
                    log.info("遍历session,{},{}", principals.asList(), session);

                    if (principals.asList().contains(userName)) {
                        if (forceLoginToken.isForceFlag()) {
                            //踢出已登录的同名用户
                            log.info("使无效session,{},{}", principals.asList(), session);
                            session.stop();
                        } else {
                            throw new LoginRepeatException(userName);
                        }
                    }
                }
            }
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo.getUserName(),
                userInfo.getUserPwd(), getName());
        return authenticationInfo;
    }

    /**
     * 修改用户角色、角色的权限时,清除缓存的授权信息
     *
     * @TODO 及时刷新权限，暂未启用
     */
    @Override
    protected final void doClearCache(PrincipalCollection principalCollection) {
        super.doClearCache(principalCollection);
    }

    /**
     * 供外部传入用户名，清空其权限缓存
     *
     * @param userName 用户名
     */
    public final void doClearCache(String userName) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            SimplePrincipalCollection principals = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principals != null && principals.asList().contains(userName)) {
                this.doClearCache(principals);
            }
        }
    }
}
