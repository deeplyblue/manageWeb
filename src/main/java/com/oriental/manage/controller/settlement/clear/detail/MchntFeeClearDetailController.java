package com.oriental.manage.controller.settlement.clear.detail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangxinhai on 2016/5/23.
 */
@Controller
@RequestMapping("settlement/clear/detail/mchntFeeClearDetail")
public class MchntFeeClearDetailController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @RequestMapping("init")
    public String init() {
        return "settlement/clear/detail/mchntFeeClearDetail";
    }

    @RequestMapping("redirectMchntFeeClearDay")
    public String redirectMchntClearDay() {
        return "settlement/clear/detail/redirectMchntFeeClearDay";
    }
}

