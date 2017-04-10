package com.oriental.manage.controller.business;

import com.oriental.manage.controller.merchant.settleManage.MerchantFeeCfgController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangjun on 2016/6/14.
 * 业务机构手续费配置
 */
@Slf4j
@Controller
@RequestMapping("business/feeCfg")
public class BusinessFeeCfgController extends MerchantFeeCfgController {
    @RequestMapping("/init")
    public String init() {
        return "business/searchFeeCfg";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "business/addFeeCfg";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "business/updateFeeCfg";
    }

}