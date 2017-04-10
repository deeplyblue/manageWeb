package com.oriental.manage.controller.system;

import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lupf on 2016/6/17.
 */
@Slf4j
@Controller
@RequestMapping("system/manage")
public class SystemManageController {

    @RequestMapping("init")
    public String init() {
        return "system/systemManage";
    }

    @RequestMapping("whoIsYourDaddy")
    public String redirectToHomePage(String asPrincipal) {

        Subject currentUser = SecurityUtils.getSubject();
        if (StringUtils.isNotBlank(asPrincipal)) {
            if (!currentUser.isRunAs()) {
                SimplePrincipalCollection principal = new SimplePrincipalCollection(asPrincipal, SessionUtils.getUserId());
                currentUser.runAs(principal);
            } else {
                currentUser.releaseRunAs();
            }
        }

        return "redirect:/home.jsp";
    }
}
