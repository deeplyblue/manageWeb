package com.oriental.manage.controller.business;

import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.business.MchntInfo;
import com.oriental.manage.service.business.IMchntInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/5/25.
 * 业务机构信息管理
 */
@Slf4j
@Controller
@RequestMapping("business/mchntInfo")
public class MchntInfoConterller {

    @Autowired
    private IMchntInfoService mchntInfoService;

    @RequestMapping("init")
    public String init(){
        return "business/searchMchntInfo";
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<MchntInfo>> queryPage (MchntInfo mchntInfo, Pagination<MchntInfo> pagination){
        ResponseDTO<Pagination<MchntInfo>> responseDTO =new ResponseDTO<Pagination<MchntInfo>>();
        try {

            mchntInfo.setCompanyType(CompanyType.BUSINESS.getCode());
            mchntInfoService.queryPage(pagination,mchntInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toAdd")
    public String toAdd(){
        return "business/addMchntInfo";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(MchntInfo mchntInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            mchntInfo.setClrRank("0");
            mchntInfo.setGuaranteeFlag("0");
            mchntInfo.setSuspendFlag("0");
            mchntInfo.setCompanyType(CompanyType.BUSINESS.getCode());
            mchntInfoService.addMchntInfo(responseDTO,mchntInfo);
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "business/updateMchntInfo";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(MchntInfo mchntInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            mchntInfoService.updateMchntInfo(responseDTO,mchntInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(MchntInfo mchntInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
           mchntInfoService.deleteMchntInfo(responseDTO,mchntInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
