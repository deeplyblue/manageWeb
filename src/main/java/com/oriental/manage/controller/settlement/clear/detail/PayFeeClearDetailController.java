package com.oriental.manage.controller.settlement.clear.detail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangxinhai on 2016/5/23.
 */
@Controller
@RequestMapping("settlement/clear/detail/payFeeClearDetail")
public class PayFeeClearDetailController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @RequestMapping("init")
    public String init() {
        return "settlement/clear/detail/payFeeClearDetail";
    }
    @RequestMapping("redirectPayFeeClearDay")
    public String redirectPayClearDay() {
        return "settlement/clear/detail/redirectPayFeeClearDay";
    }

}
