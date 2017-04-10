package com.oriental.manage.controller.institution;

import com.oriental.manage.controller.merchant.trans.MerchantTransRightController;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.service.institution.ITransRightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/10.
 */
@Slf4j
@Controller
@RequestMapping("institution/transRight")
public class TransRightController extends MerchantTransRightController {

    @Autowired
    private ITransRightService transRightService;


    @RequestMapping("/init")
    public String init() {
        return "institution/searchTransRight";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("transRight_update")
    public String toUpdate() {
        return "institution/updateTransRight";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("transRight_add")
    public String toAdd() {
        return "institution/addTransRight";
    }





}
