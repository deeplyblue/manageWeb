package com.oriental.manage.core.authorize;

import lombok.Setter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collections;

/**
 * Created by lupf on 2016/12/8.
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

    @Value("#{cfgProperties['httpEnable']}")
    @Setter
    private String httpEnable;

    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        WebUtils.issueRedirect(request, response, redirectUrl, Collections.EMPTY_MAP, true, Boolean.parseBoolean(httpEnable));
    }
}
