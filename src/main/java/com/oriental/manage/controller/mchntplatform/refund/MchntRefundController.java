package com.oriental.manage.controller.mchntplatform.refund;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.refund.BusiRefundDetail;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.pojo.transaction.TBusiTransDetail;
import com.oriental.manage.service.refund.IRefundService;
import com.oriental.manage.service.transaction.IBizTransService;
import com.oriental.manage.service.transaction.IBusiTransService;
import com.oriental.manage.service.transaction.IOrderMainService;
import com.oriental.manage.service.transaction.IPayTransService;
import com.oriental.manage.service.transaction.impl.BusiTransImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/7/6.
 */
@Slf4j
@Controller
@RequestMapping("mchntplatform/mchntRefund")
public class MchntRefundController {

    @Autowired
    private IOrderMainService orderMainService;
    @Autowired
    private IBizTransService bizTransService;
    @Autowired
    private IPayTransService payTransService;
    @Autowired
    private IRefundService refundService;

    @RequestMapping("init")
    @RequiresPermissions("mchntplatform-mchntRefund_search")
    public String init() {
        return "mchntplatform/searchMchntRefundApply";
    }

    @OperateLogger(content = "退款申请查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("mchntplatform-mchntRefund_search")
    public ResponseDTO search(@RequestBody Pagination<TBusiTransDetail> pagination) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            TBusiTransDetail tBusiTransDetail = pagination.getQueryBean();
            tBusiTransDetail.setMchntCode(SessionUtils.getMchntCode());
            tBusiTransDetail.setPageNum(pagination.getPageNum());
            tBusiTransDetail.setPageSize(pagination.getPageSize());
            Map map=refundService.sumFindCommonRefundOrder(tBusiTransDetail);


            //@TODO 查业务交易表(普通订单) 关联业务退款申请（判断是否已退）
            refundService.findCommonRefundOrder(pagination, tBusiTransDetail);

            responseDTO.setSumObject(map);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("退款申请查询失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }
    @OperateLogger(content = "退款申请",operationType = OperateLogger.OperationType.U)
    @RequestMapping("refundApply")
    @ResponseBody
    @RequiresPermissions("mchntplatform-mchntRefund_refundApply")
    public ResponseDTO refundApplication(PayTrans payTrans, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            //@TODO 根据当前登录商户号 校验
            //获取主单
            OrderMain orderMainQuery = new OrderMain();
            orderMainQuery.setOurTransNo(payTrans.getOurTransNo());
            OrderMain orderMain = orderMainService.queryOrderMainByOurTransNo(orderMainQuery);
            //获取业务
            BizTrans bizTransQuery = new BizTrans();
            bizTransQuery.setOurTransNo(payTrans.getOurTransNo());
            BizTrans bizTrans = bizTransService.queryBizTransByOurTransNo(bizTransQuery);
            //获取可退支付记录
            List<PayTrans> list = payTransService.selectRecordByOurTransNo(payTrans);
            List<PayTrans> payTransList = refundService.validateOriginPay(list);

            //权限校验
            refundService.validateOrgTransRight(payTransList);
            //金额校验
            refundService.validateTotalAmt(orderMain, bizTrans, payTransList);

            //退款申请录入
            refundService.commonRefund(orderMain, bizTrans, payTransList, request);

            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("退款申请失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequestMapping("auditInit")
    @RequiresPermissions("mchntplatform-mchntRefund_auditSearch")
    public String auditRefundInit() {

        return "mchntplatform/searchMchntRefundAudit";
    }
    @OperateLogger(content = "退款审核查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("auditSearch")
    @ResponseBody
    @RequiresPermissions("mchntplatform-mchntRefund_auditSearch")
    public ResponseDTO searchBusiRefund(@RequestBody Pagination<BusiRefundDetail> pagination) {
        ResponseDTO responseDTO = new ResponseDTO();
        BusiRefundDetail busiRefundDetail = pagination.getQueryBean();
        try {
            busiRefundDetail.setMchntCode(SessionUtils.getMchntCode());
            busiRefundDetail.setPageNum(pagination.getPageNum());
            busiRefundDetail.setPageSize(pagination.getPageSize());
            refundService.searchBusiRefund(pagination, busiRefundDetail);
            Map map=refundService.sumBusiRefund(busiRefundDetail);
            responseDTO.setSumObject(map);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("退款审核，查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "退款审核",operationType = OperateLogger.OperationType.R,tables = "T_BUSI_REFUND_DETAIL")
    @RequestMapping("audit")
    @ResponseBody
    @RequiresPermissions("mchntplatform-mchntRefund_audit")
    public ResponseDTO auditRefund(@RequestBody BusiRefundDetail busiRefundDetail) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            refundService.audit(busiRefundDetail);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("退款审核失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }
}
