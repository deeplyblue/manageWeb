package com.oriental.manage.controller.institution.settleManage;


import com.oriental.manage.controller.merchant.settleManage.MerchantFeeTemplateController;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Author: wuxg
 * Date: 2016/5/30
 * Time: 16:20
 * Desc：商户手续费模板配置
 */
@Slf4j
@Controller
@RequestMapping("/institution/feeTemplate")
public class InstitutionFeeTemplateController extends MerchantFeeTemplateController {

    @RequestMapping("/init")
    public String init() {
        return "institution/settleManage/searchFeeTemplateInfo";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("org-feeTemplate_add")
    public String toAdd() {
        return "institution/settleManage/addFeeTemplateInfo";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("org-feeTemplate_update")
    public String toUpdate() {
        return "institution/settleManage/updateFeeTemplateInfo";
    }

}
