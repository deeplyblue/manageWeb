package com.oriental.manage.controller.settlement.report.formsReport;

import com.oriental.manage.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lupf on 2017/1/10.
 */
@Slf4j
@Controller
@RequestMapping("settlement/report/formsReport/billManageCall")
public class BillManageCallController {

    @Value("#{cfgProperties['bill_typeKey']}")
    private String typeKey;
    @Value("#{cfgProperties['bill_numKey']}")
    private String numKey;
    @Value("#{cfgProperties['bill_demon']}")
    private String billDemon;

    @RequestMapping(value = "init/{type}", method = RequestMethod.GET)
    public String init(@PathVariable String type, HttpServletRequest request) {

        SecurityUtils.getSubject().checkPermission("billManage-" + type);

        String timestamp = DateUtils.now();

        String signature = new SimpleHash("SHA-1", type + this.typeKey, this.numKey + timestamp).toString();

        StringBuilder sb = new StringBuilder();
        sb.append(billDemon);
        sb.append("/manage/login/jump/jsp.action?type=");
        /*sb.append(type);
        sb.append("&num=");
        sb.append(timestamp);
        sb.append("&singe=");
        sb.append(signature);*/

        request.setAttribute("url", sb.toString());
        request.setAttribute("type", type);
        request.setAttribute("num", timestamp);
        request.setAttribute("singe", signature);

        return "settlement/report/formsReport/billManage";
    }
}
