package com.oriental.manage.controller.mchntplatform.busitrans;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.service.transaction.IBizTransService;
import com.oriental.manage.service.transaction.IBusiTransService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by wangjun on 2016/7/5.
 * 商户交易查询
 */
@Slf4j
@Controller
@RequestMapping("mchntplatform/busitransDetail")
public class BusiTransDetailController {

    @Autowired
    private IBusiTransService busiTransService;

    @Autowired
    private IBizTransService bizTransService;

    @RequestMapping("init")
    @RequiresPermissions("mchntPlatform_bizTrans_search")
    public String init(){
        return  "mchntplatform/searchBusiTransDetail";
    }

    @OperateLogger(content = "查询商户交易",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("mchntPlatform_bizTrans_search")
    public @ResponseBody
    ResponseDTO<Pagination<BizTrans>> queryPage(Pagination<BizTrans> pagination, BizTrans bizTrans){
        ResponseDTO<Pagination<BizTrans>> responseDTO = new ResponseDTO<Pagination<BizTrans>>();
        try {
            bizTrans.setMerchantCode(SessionUtils.getMchntCode());
            bizTrans.setPageNum(pagination.getPageNum());
            bizTrans.setPageSize(pagination.getPageSize());
            busiTransService.queryPage(pagination,bizTrans);
            Map map = bizTransService.summaryBizTrans(bizTrans);
            responseDTO.setSumObject(map);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toQueryDetail")
    public String toQueryDetail() {
        return "mchntplatform/searchBusiTransDetailOne";
    }

    @OperateLogger(content = "查询单笔商户交易",operationType = OperateLogger.OperationType.R,tables = "T_BUSI_TRANS_DETAIL")
    @RequestMapping("queryBizTransDetail")
    @RequiresPermissions("mchntPlatform_bizTrans_search")
    @ResponseBody
    public ResponseDTO<BizTrans> queryBizTransDetail(BizTrans bizTrans) {
        ResponseDTO<BizTrans> responseDTO = new ResponseDTO<BizTrans>();
        try {
            bizTrans.setMerchantCode(SessionUtils.getMchntCode());
            BizTrans resultBizTrans = bizTransService.queryBizTransDetail(bizTrans);
            responseDTO.setSuccess(true);
            responseDTO.setObject(resultBizTrans);
        } catch (Exception e) {
            log.error("查询业务信息失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

}
