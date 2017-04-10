package com.oriental.manage.controller.refund;

import com.oriental.manage.core.excelUtils.ExcelUtil;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.refund.OrgRefundInfo;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.service.refund.IRefundService;
import com.oriental.manage.service.transaction.IBizTransService;
import com.oriental.manage.service.transaction.IPayTransService;
import com.oriental.manage.service.transaction.impl.BizTransServiceImpl;
import com.oriental.paycenter.commons.exception.BizException;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.settlementfront.service.facade.manager.BankTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.MchntTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.MchntTransDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * User: hj
 * Date: 2016/5/26 16:00
 * Desc:
 */
@Slf4j
@Controller
@RequestMapping("refund")
public class RefundController {

    @Autowired
    private IRefundService refundService;

    @Autowired
    private MchntTransDetailFacade mchntTransDetailFacade;

    @Autowired
    private IBizTransService bizTransService;

    @Autowired
    private IPayTransService payTransService;

    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("init")
    @RequiresPermissions("refund_search")
    public String init() {
        return "refund/queryRefundList";
    }

    @OperateLogger(content = "退款信息查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryRefundList")
    @RequiresPermissions("refund_search")
    @ResponseBody
    public ResponseDTO<Pagination<OrgRefundInfo>> queryRefundList(Pagination<OrgRefundInfo> pagination, OrgRefundInfo orgRefundInfo) {
        ResponseDTO<Pagination<OrgRefundInfo>> responseDTO = new ResponseDTO<Pagination<OrgRefundInfo>>();
        try {
            refundService.queryPage(pagination, orgRefundInfo);
            Map map = refundService.summaryOrgRefund(orgRefundInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setSumObject(map);
        } catch (Exception e) {
            log.error("查询退款列表失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @OperateLogger(content = "线上退款", operationType = OperateLogger.OperationType.U, tables = "T_ORG_REFUND_DETAIL")
    @RequestMapping("onlineRefund")
    @RequiresPermissions("refund_onlineRefund")
    @ResponseBody
    public ResponseDTO<String> onlineRefund(Pagination<OrgRefundInfo> pagination, OrgRefundInfo orgRefundInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if (StringUtils.isBlank(orgRefundInfo.getId())) {
                throw new BizException("请求参数错误");
            }
            refundService.onlineRefund(orgRefundInfo);
            responseDTO.setSuccess(true);
        } catch (BizException b) {
            log.error("线上退款异常", b);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(b.getCode());
        } catch (Exception e) {
            log.error("线上退款异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("reportInit")
    @RequiresPermissions("reportInit_search")
    public String reportInit() {
        return "refund/searchRefundReport";
    }

    @RequestMapping("searchReport")
    @RequiresPermissions("reportInit_search")
    @ResponseBody
    public ResponseDTO<List<Map<String, Object>>> searchReport(String sDate) {
        ResponseDTO<List<Map<String, Object>>> responseDTO = new ResponseDTO<>();
        try {
            Date date = DateUtil.parse(sDate);
            List<Map<String, Object>> orgRefundDetails = refundService.selectRefundSucByDate(date);
            long totalAmt = 0L;
            for (Map<String, Object> orgRefundDetail : orgRefundDetails) {
                String old_our_trans_no = orgRefundDetail.get("OLD_OUR_TRANS_NO").toString();
                // 查询业务流水表
                BizTrans query = new BizTrans();
                query.setOurTransNo(old_our_trans_no);
                BizTrans bizTrans = bizTransService.queryBizTransByOurTransNo(query);
                // 查询支付流水表
                PayTrans query2 = new PayTrans();
                query2.setOurTransNo(old_our_trans_no);
                List<PayTrans> payTransList = payTransService.selectRecordByOurTransNo(query2);
                // 查询清结算商户交易明细
                Response<List<MchntTransDetailDTO>> response = mchntTransDetailFacade.selectByInnerPayTransNo(
                        bizTrans.getMerchantCode(), bizTrans.getOrderNo(), SessionUtils.getUserName());
                if (!response.isSuccess()) {
                    throw new BizException(response.getErrorMsg());
                }
                // 组装参数
                if (response.getResult() != null && response.getResult().size() > 0) {
                    String busiTypeDesc = response.getResult().get(0).getBusiTypeDesc();
                    orgRefundDetail.put("BUSI_TYPE_DESC", busiTypeDesc);
                }
                orgRefundDetail.put("OLD_ORDER_NO", bizTrans.getOrderNo());
                orgRefundDetail.put("MCHNT_CODE", bizTrans.getMerchantCode());
                orgRefundDetail.put("PAY_BANK", payTransList.get(0).getBankCode());
                orgRefundDetail.put("PAY_AMT", payTransList.get(0).getTransAmt());
                totalAmt += Long.parseLong(orgRefundDetail.get("REFUND_AMT").toString());
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(orgRefundDetails);
            responseDTO.setSumObject(totalAmt);
        } catch (BizException e) {
            log.error("查询报表失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询报表失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("download")
    @ResponseBody
    @RequiresPermissions("refund-report_download")
    public ResponseEntity<byte[]> download(String oDate) {
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            Date date = DateUtil.parse(oDate);
            tempFile = fileDownAjax.touch("退款报表-" + DateUtils.now() + ".xlsx");
            //创建表格
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();
            tHeaders.add(new HeaderExt("业务类型", "BUSI_TYPE_DESC"));
            tHeaders.add(new HeaderExt("原订单号", "OLD_ORDER_NO"));
            tHeaders.add(new HeaderExt("商户", "MCHNT_CODE",ExcelContentExt.MCHNT));
            tHeaders.add(new HeaderExt("支付机构", "PAY_ORG_CODE",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("付款银行(发卡行)", "PAY_BANK",ExcelContentExt.BANK_INFO));
            tHeaders.add(new HeaderExt("退款流水号", "ORDER_NO"));
            tHeaders.add(new HeaderExt("申请人", "REFUND_APPLICANT"));
            tHeaders.add(new HeaderExt("审核人", "AUDITOR"));
            tHeaders.add(new HeaderExt("处理人", "PROC_EMP_ID"));
            tHeaders.add(new HeaderExt("付款金额", "PAY_AMT",ExcelContentExt.CURRENCY));
            tHeaders.add(new HeaderExt("退款金额", "REFUND_AMT",ExcelContentExt.CURRENCY));

            //查询数据
            List<Map<String, Object>> orgRefundDetails = refundService.selectRefundSucByDate(date);
            long totalAmt = 0L;
            if(orgRefundDetails !=null && orgRefundDetails.size()>0) {
                for (Map<String, Object> orgRefundDetail : orgRefundDetails) {
                    
                    String old_our_trans_no = orgRefundDetail.get("OLD_OUR_TRANS_NO").toString();
                    // 查询业务流水表
                    BizTrans query = new BizTrans();
                    PayTrans query2 = new PayTrans();
                    query.setOurTransNo(old_our_trans_no);
                    BizTrans bizTrans = bizTransService.queryBizTransByOurTransNo(query);
                    // 查询支付流水表
                    query2.setOurTransNo(old_our_trans_no);
                    List<PayTrans> payTransList = payTransService.selectRecordByOurTransNo(query2);
                    // 查询清结算商户交易明细
                    Response<List<MchntTransDetailDTO>> response = mchntTransDetailFacade.selectByInnerPayTransNo(
                            bizTrans.getMerchantCode(), bizTrans.getOrderNo(), SessionUtils.getUserName());
                    if (!response.isSuccess()) {
                        throw new BizException(response.getErrorMsg());
                    }
                    // 组装参数
                    if (response.getResult() != null && response.getResult().size() > 0) {
                        String busiTypeDesc = response.getResult().get(0).getBusiTypeDesc();
                        orgRefundDetail.put("BUSI_TYPE_DESC", busiTypeDesc);
                    }
                    orgRefundDetail.put("OLD_ORDER_NO", bizTrans.getOrderNo());
                    orgRefundDetail.put("MCHNT_CODE", bizTrans.getMerchantCode());
                    orgRefundDetail.put("PAY_BANK", payTransList.get(0).getBankCode());
                    orgRefundDetail.put("PAY_AMT", payTransList.get(0).getTransAmt());
                    totalAmt += Long.parseLong(orgRefundDetail.get("REFUND_AMT").toString());
                    datas.add(orgRefundDetail);

                }

                Map m = new HashMap();
                m.put("REFUND_APPLICANT", "总笔数:");
                m.put("AUDITOR", orgRefundDetails.size());
                m.put("PROC_EMP_ID", "退款总金额:");
                m.put("PAY_AMT", totalAmt);
                datas.add(m);
            }
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            POIExcelApi.getInstance().write(outputStream, ca);
            File file = ExcelUtils.addTitle(tempFile, "退款报表", fileDownAjax, tHeaders.size(), oDate, "");
            responseEntity = fileDownAjax.getResponseEntity(file);
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }


        return responseEntity;
    }
}
