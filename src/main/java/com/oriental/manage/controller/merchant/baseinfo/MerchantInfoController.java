package com.oriental.manage.controller.merchant.baseinfo;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.service.merchant.baseinfo.IMerchantInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/19
 * Time: 9:53
 * Desc：商户基本信息
 */
@Slf4j
@Controller
@RequestMapping("/merchant/info")
public class MerchantInfoController {


    @Autowired
    private IMerchantInfoService merchantService;

    @RequestMapping("/init")
    public String init() {
        return "merchant/baseinfo/searchMerchantInfo";
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("merchant-info_add")
    public String toAdd() {
        return "merchant/baseinfo/addMerchantInfo";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("merchant-info_update")
    public String toUpdate() {
        return "merchant/baseinfo/updateMerchantInfo";
    }

    @OperateLogger(content = "修改商户基本信息", operationType = OperateLogger.OperationType.U, tables = "T_MCHNT_INFO")
    @RequiresPermissions(value = {"merchant-info_auditpass", "merchant-info_auditrefuse", "merchant-info_close",
            "merchant-info_open", "merchant-info_reopen"}, logical = Logical.OR)
    @RequestMapping("updateByStatus")
    public
    @ResponseBody
    ResponseDTO<String> updateByStatus(MerchantInfo merchantInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            merchantInfo.setLastUpdTime(new Date());
            if ("B".equals(merchantInfo.getMerchantStatus())) {
                merchantInfo.setOpenUser(SessionUtils.getUserName());
            }
            merchantService.updateMerchantInfoByStatus(merchantInfo, responseDTO);
        } catch (Exception e) {
            log.error("跟新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "新增商户基本信息", operationType = OperateLogger.OperationType.C, tables = "T_MCHNT_INFO", noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("merchant-info_add")
    protected ResponseDTO<String> add(ResponseDTO<String> responseDTO, MerchantInfo merchantInfo) {
        log.info("新增信息:{}", merchantInfo);
        try {
            if (checkCode(merchantInfo.getMerchantCode(), null)) {
                responseDTO.setMsg("商户代码已经存在");
                responseDTO.setSuccess(false);
            } else if (checkAbbName(merchantInfo.getMerchantAbbrName(), null)) {
                responseDTO.setMsg("商户简称已经存在");
                responseDTO.setSuccess(false);
            } else if (checkMerchantName(merchantInfo.getMerchantName(), null)) {
                responseDTO.setMsg("商户名称已经存在");
                responseDTO.setSuccess(false);
            } else {
                if (merchantInfo.getMerchantArea() != null) {
                    String are = merchantInfo.getMerchantArea();
                    String area = are.substring(0, 2);
                    String city = are.substring(2, 4);
                    merchantInfo.setMerchantArea(area);
                    merchantInfo.setCityCode(city);
                }
                merchantInfo.setMerchantStatus("A");
                merchantInfo.setOpenDate(new Date());
                merchantInfo.setCompanyType(CompanyType.MERCHANT.getCode());
                merchantInfo.setOpenUser(SessionUtils.getUserName());
                merchantInfo.setModifyUser(SessionUtils.getUserName());
                merchantInfo.setDeleteFlag("0");
                merchantInfo.setSuspendFlag("0");
                merchantService.createMerchantInfo(merchantInfo, responseDTO);
            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改商户基本信息", operationType = OperateLogger.OperationType.U, tables = "T_MCHNT_INFO")
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("merchant-info_update")
    protected ResponseDTO<String> update(ResponseDTO<String> responseDTO, MerchantInfo merchantInfo) {
        log.info("修改信息:{}", merchantInfo);
        try {
            if (checkAbbName(merchantInfo.getMerchantAbbrName(), merchantInfo.getMerchantCode())) {
                responseDTO.setMsg("商户简称已经存在");
                responseDTO.setSuccess(false);
            } else if (checkMerchantName(merchantInfo.getMerchantName(), merchantInfo.getMerchantCode())) {
                responseDTO.setMsg("商户名称已经存在");
                responseDTO.setSuccess(false);
            } else {
                if (merchantInfo.getMerchantArea() != null && merchantInfo.getMerchantArea().length() == 4) {
                    String are = merchantInfo.getMerchantArea();
                    String area = are.substring(0, 2);
                    String city = are.substring(2, 4);
                    merchantInfo.setCityCode(city);
                    merchantInfo.setMerchantArea(area);
                }
                merchantInfo.setModifyUser(SessionUtils.getUserName());
                merchantInfo.setMerchantStatus("A");
                merchantService.updateMerchantInfo(merchantInfo, responseDTO);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "查询商户基本信息", operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @RequiresPermissions("merchant-info_search")
    @ResponseBody
    protected ResponseDTO<Pagination<MerchantInfo>> search(Pagination<MerchantInfo> pagination, MerchantInfo merchantInfo) {
        ResponseDTO<Pagination<MerchantInfo>> responseDTO = new ResponseDTO<Pagination<MerchantInfo>>();
        log.info("查询信息:{},{}", merchantInfo, pagination);
        try {
            merchantService.searchMerchantInfo(pagination, merchantInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("checkCode")
    @ResponseBody
    public boolean checkCode(String temp, String code) {
        MerchantInfo merchantInfo1 = new MerchantInfo();
        merchantInfo1.setMerchantCode(temp);
        return merchantService.check(merchantInfo1, code);
    }

    @RequestMapping("checkAbbName")
    @ResponseBody
    public boolean checkAbbName(String temp, String code) {
        MerchantInfo merchantInfo1 = new MerchantInfo();
        merchantInfo1.setMerchantAbbrName(temp);
        return merchantService.check(merchantInfo1, code);
    }

    @RequestMapping("checkMerchantName")
    @ResponseBody
    public boolean checkMerchantName(String temp, String code) {
        MerchantInfo merchantInfo1 = new MerchantInfo();
        merchantInfo1.setMerchantName(temp);
        return merchantService.check(merchantInfo1, code);
    }


}
