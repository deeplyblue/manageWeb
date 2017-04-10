package com.oriental.manage.controller.institution.settleManage;

import com.oriental.manage.controller.merchant.settleManage.MerchantClearCfgController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wuxg on 2016/5/16.
 */
@Slf4j
@Controller
@RequestMapping("/institution/clearCfg")
public class InstitutionClearCfgController extends MerchantClearCfgController{

    @RequestMapping("/init")
    public String init() {
        return "institution/settleManage/searchClearCfg";
    }


    @RequestMapping("/toAdd")
    @RequiresPermissions("clearCfg_add")
    public String toAdd() {
        return "institution/settleManage/addClearCfg";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("clearCfg_update")
    public String toUpdate() {
        return "institution/settleManage/updateClearCfg";
    }
}
