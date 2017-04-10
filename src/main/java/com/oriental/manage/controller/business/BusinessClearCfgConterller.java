package com.oriental.manage.controller.business;

import com.oriental.clearDubbo.api.clear.cfg.ClearCfgInterface;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.ClearCfg;
import com.oriental.manage.service.merchant.IClearCfgService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangjun on 2016/6/8.
 *业务机构结算配置
 */
@Slf4j
@Controller
@RequestMapping("business/businessClearCfg")
public class BusinessClearCfgConterller {


    @Autowired
    private IClearCfgService clearCfgService;



    @RequestMapping("/init")
    public String init() {
        return "business/searchClearCfg";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "business/addClearCfg";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "business/updateClearCfg";
    }

    @RequestMapping("search")
    public @ResponseBody
    ResponseDTO<Pagination<ClearCfg>> queryPage(Pagination<ClearCfg> pagination, ClearCfg clearCfg){
        ResponseDTO<Pagination<ClearCfg>> responseDTO = new ResponseDTO<Pagination<ClearCfg>>();
        try {
            clearCfg.setOrgType(CompanyType.BUSINESS.getCode());
            clearCfgService.queryPage(pagination,clearCfg);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(ClearCfg clearCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            clearCfg.setOrgType(CompanyType.BUSINESS.getCode());
            clearCfg.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            clearCfgService.addClearCfg(responseDTO,clearCfg);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(ClearCfg clearCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            clearCfgService.updateClearCfg(responseDTO,clearCfg);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(ClearCfg clearCfg) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            clearCfgService.deleteClearCfg(responseDTO,clearCfg);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


}
