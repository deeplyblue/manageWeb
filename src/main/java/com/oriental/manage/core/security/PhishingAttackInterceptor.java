package com.oriental.manage.core.security;

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.IPUtil;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.IplimitCfg;
import com.oriental.manage.service.base.IpLimitCfgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lupf on 2016/11/18.
 */
@Slf4j
@Component
public class PhishingAttackInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private Constants constants;
    @Autowired
    private IpLimitCfgService ipLimitCfgService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        super.preHandle(request, response, handler);

        //域名白名单
        Set<String> domains = constants.getDataDictMap().get(CacheKey.SYSTEM_DOMAINS.name().toLowerCase()).keySet();

        String refer = request.getHeader("Referer");
        try {

            //验证refer
            Pattern pattern = Pattern.compile("[^//]+(?=/manage)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(refer);
            matcher.find();

            String domain = matcher.group(0);
            String[] str = domain.split(":");

            //允许白名单内、以及内网ip
            if (!domains.contains(domain) && !ipLimitCfgService.isInnerIp(str[0])) {

                log.error("异常的请求来源refer:{}", refer);
                return false;
            }

        } catch (Exception e) {
            log.error("异常的请求来源refer:{}", refer);
            return false;
        }

        //验证ip
        if (StringUtils.isNotBlank(SessionUtils.getUserId())) {
            String ip = IPUtil.getIpAddrByRequest(request);

            IplimitCfg queryIp = new IplimitCfg();
            queryIp.setClientIp(ip);
            queryIp.setUserId(SessionUtils.getUserId());
            ipLimitCfgService.ipValidate(queryIp);
        }

        return true;
    }

}
