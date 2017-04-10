package com.oriental.manage.service.refund.impl;

import com.oriental.check.commons.enums.TransStatus;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.enums.refund.ErrType;
import com.oriental.manage.core.utils.*;
import com.oriental.manage.dao.base.CommonMapper;
import com.oriental.manage.dao.refund.BusiRefundDetailMapper;
import com.oriental.manage.dao.refund.OrgRefundDetailMapper;
import com.oriental.manage.dao.refund.RefundMapper;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.pojo.refund.BusiRefundDetail;
import com.oriental.manage.pojo.refund.BusiRefundInfo;
import com.oriental.manage.pojo.refund.OrgRefundDetail;
import com.oriental.manage.pojo.refund.OrgRefundInfo;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.pojo.transaction.TBusiTransDetail;
import com.oriental.manage.service.institution.ITransRightService;
import com.oriental.manage.service.refund.IRefundService;
import com.oriental.paycenter.commons.enums.DeleteFlag;
import com.oriental.paycenter.commons.enums.PayType;
import com.oriental.paycenter.commons.enums.TransInfoType;
import com.oriental.paycenter.commons.enums.chkerr.ChkErrHandleType;
import com.oriental.paycenter.commons.enums.refund.BusiRefundStatus;
import com.oriental.paycenter.commons.enums.refund.OrgRefundStatus;
import com.oriental.paycenter.commons.enums.refund.RefundFlag;
import com.oriental.paycenter.commons.enums.refund.RefundType;
import com.oriental.paycenter.commons.exception.BizException;
import com.oriental.paycenter.external.api.operations.ChkErrFacade;
import com.oriental.paycenter.external.api.operations.RefundFacade;
import com.oriental.paycenter.external.model.ResponseDTO;
import com.oriental.paycenter.external.model.chkerr.ChkErrHandleDTO;
import com.oriental.paycenter.external.model.refund.RefundReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RefundMapper refundMapper;

    @Autowired(required = false)
    private RefundFacade refundFacade;

    @Autowired(required = false)
    private ChkErrFacade chkErrFacade;

    @Autowired
    private ITransRightService transRightService;

    @Autowired(required = false)
    private CommonMapper commonMapper;

    @Autowired(required = false)
    private BusiRefundDetailMapper busiRefundDetailMapper;

    @Autowired(required = false)
    private OrgRefundDetailMapper orgRefundDetailMapper;

    @Override
    public void queryPage(Pagination<OrgRefundInfo> pagination, OrgRefundInfo orgRefundInfo) {
        List<OrgRefundInfo> list = refundMapper.queryPage(orgRefundInfo);
        if (null != list && list.size() > 0) {
            for (OrgRefundInfo orgRefundInfo1 : list) {
                orgRefundInfo1.setRefundAmt(BigDecimalUtils.changeF2Y(orgRefundInfo1.getRefundAmt()));
                orgRefundInfo1.setTotalAmt(BigDecimalUtils.changeF2Y(orgRefundInfo1.getTotalAmt()));
            }
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public Map summaryOrgRefund(OrgRefundInfo orgRefundInfo){
        Map map = refundMapper.summaryOrgRefund(orgRefundInfo);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            String sumAmt = map.get("sumTotalAmt").toString();
            Double sumTotalAmt = Double.valueOf(sumAmt) / 100;
            map.put("sumTotalAmt", sumTotalAmt.toString());
        }
        return map;
    }

    @Override
    public void onlineRefund(OrgRefundInfo orgRefundInfo) {
        String orgRefundId = orgRefundInfo.getId();
        orgRefundInfo = refundMapper.queryOrgRefundById(orgRefundId);
        if (null == orgRefundInfo) {
            log.error("id:{},未查询到机构退款申请表信息", orgRefundId);
            throw new BizException("未查询到机构退款申请表信息");
        }
        if (!"2".equals(orgRefundInfo.getRefundStatus()) && !"8".equals(orgRefundInfo.getRefundStatus())) {
            log.error("id:{},refundStatus:{},退款状态不可退", orgRefundId, orgRefundInfo.getRefundStatus());
            throw new BizException("退款状态不可退");
        }
        orgRefundInfo.setProcEmpId(SessionUtils.getUserName());
        orgRefundInfo.setRefundType(RefundType.ONLINE.getCode());
        orgRefundInfo.setProcDatetime(new Date());
        orgRefundInfo.setLastUpdTime(new Date());
        if (refundMapper.updateOrgRefund(orgRefundInfo) == 1) {
            switch (Integer.valueOf(orgRefundInfo.getErrType())) {
                case 1:
                    commonRefundOnline(orgRefundInfo);
                    break;
                default:
                    errorRefundOnline(orgRefundInfo);
            }
        }
    }

    /**
     * 普通退款
     *
     * @param orgRefundInfo 机构退款申请表
     */
    private void commonRefundOnline(OrgRefundInfo orgRefundInfo) {
        BusiRefundInfo busiRefundInfo = refundMapper.queryBusiRefundInfoById(orgRefundInfo.getBusiRefundId());
        if (null == busiRefundInfo) {
            log.error("id:{}，查询业务退款申请表失败", orgRefundInfo.getId());
            throw new BizException("查询业务退款申请表失败");
        }
        RefundReqDTO refundReqDTO = new RefundReqDTO();
        refundReqDTO.setMerchantCode(busiRefundInfo.getMchntCode());
        refundReqDTO.setOldOrderNo(busiRefundInfo.getOldOrderNo());
        refundReqDTO.setChannel(orgRefundInfo.getChannel());
        refundReqDTO.setTransAmt(orgRefundInfo.getRefundAmt());
        refundReqDTO.setRefundReqDate(DateUtils.getToDay(DateUtils.DATETIMESFORMAT));
        refundReqDTO.setOrderNo(orgRefundInfo.getOrderNo());
        refundReqDTO.setPayOrgCode(orgRefundInfo.getPayOrgCode());
        try {
            log.info("远程调用RefundFacade.commonRefund,参数：{}", refundReqDTO);
            ResponseDTO<Boolean> responseDTO = refundFacade.commonRefund(refundReqDTO);
            if (responseDTO.isSuccess()) {
                log.info("id:{},退款受理成功", orgRefundInfo.getId());
            } else {
                log.error("id:{},退款受理失败，responseDTO:{}", orgRefundInfo.getId(), responseDTO);
                throw new BizException("退款受理失败,请稍后查询订单信息");
            }
        } catch (Exception e) {
            log.error("id:{},退款受理失败，e:", orgRefundInfo.getId(), e);
            throw new BizException("退款受理失败,请稍后查询订单信息");
        }
    }

    /**
     * 差异退款
     *
     * @param orgRefundInfo 机构退款申请表
     */
    private void errorRefundOnline(OrgRefundInfo orgRefundInfo) {
        ChkErrHandleDTO chkErrHandleDTO = new ChkErrHandleDTO();
        chkErrHandleDTO.setChkErrHandleType(ChkErrHandleType.REFUND.getType());
        chkErrHandleDTO.setHandler(SessionUtils.getUserName());
        chkErrHandleDTO.setChannel(orgRefundInfo.getChannel());
        chkErrHandleDTO.setOrderNo(orgRefundInfo.getOrderNo());
        chkErrHandleDTO.setMerchantCode(orgRefundInfo.getMchntCode());
        chkErrHandleDTO.setSmallChkErrType(ErrType.ERR_TYPE_BANK_MORE.getCode());
        chkErrHandleDTO.setId(orgRefundInfo.getId());
        switch (Integer.valueOf(orgRefundInfo.getErrType())) {
            case 2:
                chkErrHandleDTO.setMasterChkErrType(ErrType.TYPE_ORG_ERR.getCode());
                break;
            case 3:
                chkErrHandleDTO.setMasterChkErrType(ErrType.TYPE_INNER_ERR.getCode());
                break;
        }
        try {
            log.info("id:{},差异处理调用,chkErrHandleDTO:{}", orgRefundInfo.getId(), chkErrHandleDTO);
            ResponseDTO<Boolean> responseDTO = chkErrFacade.handleChkErr(chkErrHandleDTO);
            log.info("id:{},差异处理返回,responseDTO:{}", orgRefundInfo.getId(), responseDTO);
            if (responseDTO.isSuccess()) {
                log.info("id:{},差异退款受理成功", orgRefundInfo.getId());
            } else {
                log.error("id:{},差异退款受理失败，responseDTO:{}", orgRefundInfo.getId(), responseDTO);
                throw new BizException("退款受理失败,请稍后查询订单信息");
            }
        } catch (Exception e) {
            log.error("id:{},退款受理失败，e:", orgRefundInfo.getId(), e);
            throw new BizException("退款受理失败,请稍后查询订单信息");
        }
    }

    @Override
    public List<PayTrans> validateOriginPay(List<PayTrans> list) {
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                PayTrans payTrans = (PayTrans) iterator.next();
                //判断原交易成功、未退款、未删除
                if (StringUtils.equals(payTrans.getTransStatus(), TransStatus.SUCCESS.getCode())
                        && StringUtils.equals(payTrans.getRefundFlag(), RefundFlag.INITIAL.getCode())
                        && StringUtils.equals(payTrans.getDeleteFlag(), DeleteFlag.NORMAL.getCode())) {
                    continue;
                } else {
                    iterator.remove();
                }
            }
            if (list.size() <= 0) {
                throw new BusiException("未找到可退的原交易!");
            } else {
                return list;
            }
        } else {
            throw new BusiException("未找到可退的原交易!");
        }

    }

    @Override
    public void validateOrgTransRight(List<PayTrans> payTransList) {
        for (PayTrans payTrans : payTransList) {
            TransRight transRight = new TransRight();
            transRight.setCompanyCode(payTrans.getPayOrgCode());
            transRight.setConnChannel(payTrans.getChannel());
            transRight.setTransCode(PayType.REFUND.getCode());

            TransRight right = transRightService.queryTransRight(transRight);
            if (right == null) {
                log.info("支付机构{}，渠道{},没有退款权限", payTrans.getPayOrgCode(), payTrans.getChannel());
                throw new BusiException("没有退款权限");
            }
        }
    }

    @Override
    public void validateTotalAmt(OrderMain orderMain, BizTrans bizTrans, List<PayTrans> payTransList) {
        String orderMainAmt = orderMain.getTotalAmt();
        String bizTransAmt = bizTrans.getTransAmt();
        String payTransAmt = "";
        for (PayTrans payTrans : payTransList) {
            payTransAmt = BigDecimalUtils.add(payTransAmt, payTrans.getTransAmt());
        }

        if (BigDecimalUtils.isCompareTo(orderMainAmt, bizTransAmt) != 0 || BigDecimalUtils.isCompareTo(orderMainAmt, payTransAmt) != 0) {
            throw new BusiException("订单金额校验失败！");
        }
    }

    @Override
    public void commonRefund(OrderMain orderMain, BizTrans bizTrans, List<PayTrans> payTransList, HttpServletRequest request) throws ParseException {
        BusiRefundDetail busiRefundDetail = new BusiRefundDetail();
        String orderNo = new StringBuilder()
                .append("refund_")
                .append(DateUtils.now())
                .append(commonMapper.getSeq("SEQ_ORDER_NO_8"))
                .toString();
        Date now = DateUtils.parse(new Date(), DateUtils.DATESHORTFORMAT);

        busiRefundDetail.setId(commonMapper.getCommonId("seq_common_8"));

        busiRefundDetail.setMchntCode(orderMain.getMerchantCode());
        busiRefundDetail.setOrderNo(orderNo);
        busiRefundDetail.setOrderDate(now);
        busiRefundDetail.setTotalAmt(new Long(bizTrans.getTransAmt()));
        busiRefundDetail.setRefundAmt(new Long(bizTrans.getTransAmt()));
        busiRefundDetail.setTotalRefundAmount(new Long(bizTrans.getTransAmt()));

        busiRefundDetail.setOldOurTransNo(bizTrans.getOurTransNo());
        busiRefundDetail.setOldOrderNo(bizTrans.getOrderNo());
        busiRefundDetail.setOrderAdjStatus("01");

        busiRefundDetail.setTransInfoType(TransInfoType.NORMAL_REFUND.getCode());
        busiRefundDetail.setRefundStatus(BusiRefundStatus.APPLY.getCode());
        busiRefundDetail.setRefundApplicant(SessionUtils.getUserName());
        busiRefundDetail.setApplicantIp(IPUtil.getIpAddrByRequest(request));


        busiRefundDetailMapper.insertSelective(busiRefundDetail);

        for (PayTrans payTrans : payTransList) {
            OrgRefundDetail detail = new OrgRefundDetail();

            detail.setId(commonMapper.getCommonId("seq_common_8"));
            detail.setOrderNo(orderNo);
            detail.setOrderDate(now);
            detail.setChannel("07");
            detail.setPayOrgCode(payTrans.getPayOrgCode());
            detail.setOrgBankCode(payTrans.getOrgBankCode());
            detail.setRefundAmt(new Long(payTrans.getTransAmt()));
            detail.setTotalAmt(new Long(orderMain.getTotalAmt()));
            detail.setBusiRefundId(busiRefundDetail.getId());

            detail.setOldOurTransNo(payTrans.getOurTransNo());
            detail.setOldPayReqNo(payTrans.getPayReqNo());
            detail.setOldPayTransNo(payTrans.getPayTransNo());

            detail.setRefundStatus(OrgRefundStatus.APPLY.getCode());
            detail.setOrderAdjStatus("01");
            detail.setRefundApplicant(SessionUtils.getUserName());
            detail.setApplicantIp(IPUtil.getIpAddrByRequest(request));
            detail.setTransInfoType(TransInfoType.NORMAL_REFUND.getCode());
            detail.setErrType("1");

            orgRefundDetailMapper.insertSelective(detail);
        }
    }

    @Override
    public void findCommonRefundOrder(Pagination<TBusiTransDetail> pagination, TBusiTransDetail tBusiTransDetail) {
        //普通订单
        tBusiTransDetail.setTransCode("0000001");

        List<TBusiTransDetail> list = busiRefundDetailMapper.selectForRefundApply(tBusiTransDetail);

        pagination.setList(list);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
        }

    }

    @Override
    public Map sumFindCommonRefundOrder(TBusiTransDetail busiRefundDetail) {
        busiRefundDetail.setTransCode("0000001");
        Map map = busiRefundDetailMapper.sumFindCommonRefundOrder(busiRefundDetail);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            String sumAmt = map.get("sumTotalAmt").toString();
            Double sumTotalAmt = Double.valueOf(sumAmt) / 100;
            map.put("sumTotalAmt", sumTotalAmt.toString());
        }
        return map;

    }

    @Override
    public void searchBusiRefund(Pagination<BusiRefundDetail> pagination, BusiRefundDetail busiRefundDetail) {
        List<BusiRefundDetail> list = busiRefundDetailMapper.selectBySelective(busiRefundDetail);

        pagination.setList(list);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public Map sumBusiRefund(BusiRefundDetail busiRefundDetail) {
        Map map = busiRefundDetailMapper.sumBusiRefund(busiRefundDetail);
        if (null == map.get("sumTotalAmt")) {
            map.put("sumTotalAmt", "0");
        } else {
            String sumAmt = map.get("sumTotalAmt").toString();
            Double sumTotalAmt = Double.valueOf(sumAmt) / 100;
            map.put("sumTotalAmt", sumTotalAmt.toString());
        }
        return map;
    }

    @Override
    public void audit(BusiRefundDetail busiRefundDetail) {
        BusiRefundDetail busi = new BusiRefundDetail();

        Date now = new Date();

        busi.setId(busiRefundDetail.getId());
        busi.setAuditor(SessionUtils.getUserName());
        busi.setRefundStatus(BusiRefundStatus.APPLY_VERIFY_PASS.getCode());
        busi.setAuditDate(now);
        busiRefundDetailMapper.updateByPrimaryKeySelective(busi);

        OrgRefundDetail org = new OrgRefundDetail();

        org.setBusiRefundId(busiRefundDetail.getId());
        org.setAuditor(SessionUtils.getUserName());
        org.setRefundStatus(OrgRefundStatus.APPLY_VERIFY_PASS.getCode());
        org.setAuditDate(now);
        orgRefundDetailMapper.updateByBusiIdKeySelective(org);
    }

    @Override
    public List<Map<String, Object>> selectRefundSucByDate(Date date) {
        OrgRefundDetail query = new OrgRefundDetail();
        query.setProcDatetime(date);
        return orgRefundDetailMapper.selectReport(query);
    }
}
