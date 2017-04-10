package com.oriental.manage.controller.institution.settleManage;

import com.oriental.manage.controller.merchant.settleManage.MerchantFeeCfgController;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.merchant.FeeCfg;
import com.oriental.manage.service.merchant.IFeeCfgSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/13.
 */
@Slf4j
@Controller
@RequestMapping("/institution/feeCfg")
public class InstitutionFeeCfgController extends MerchantFeeCfgController {
    @RequestMapping("/init")
    public String init() {
        return "institution/settleManage/searchFeeCfg";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "institution/settleManage/addFeeCfg";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "institution/settleManage/updateFeeCfg";
    }

}