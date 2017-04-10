package com.oriental.manage.controller.business;

import com.oriental.manage.controller.merchant.settleManage.MerchantFeeTemplateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: wuxg
 * Date: 2016/5/30
 * Time: 16:20
 * Desc：业务机构手续费模板配置
 */
@Slf4j
@Controller
@RequestMapping("/business/feeTemplate")
public class BusinessFeeTemplateController extends MerchantFeeTemplateController {

    @RequestMapping("/init")
    public String init() {
        return "business/searchFeeTemplateInfo";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "business/addFeeTemplateInfo";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "business/updateFeeTemplateInfo";
    }

}
