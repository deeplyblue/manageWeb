package com.oriental.manage.core.authorize;

import com.oriental.manage.core.utils.SessionUtils;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import schemasMicrosoftComOfficeOffice.STInsetMode;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;

/**
 * Created by lupf on 2016/7/4.
 */
@Data
@Slf4j
public class TimeoutSessionControlFilter extends AccessControlFilter {

    private final String timeoutUrl = "/timeout.jsp"; //踢出后到的地址
//    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
//    private int maxSession = 1; //同一个帐号最大会话数 默认1

    @Autowired
    private EnterpriseCacheSessionDAO sessionDAO;
    private Cache<String, Deque<Serializable>> cache;

    @Value("#{cfgProperties['httpEnable']}")
    @Setter
    private String httpEnable;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (subject.isAuthenticated() || httpServletRequest.getServletPath().contains("logout")) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);

        WebUtils.issueRedirect(servletRequest, servletResponse, timeoutUrl, Collections.EMPTY_MAP, true, Boolean.parseBoolean(httpEnable));
        return false;

    }
}
