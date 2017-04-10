package com.oriental.manage.service.refund;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.refund.BusiRefundDetail;
import com.oriental.manage.pojo.refund.OrgRefundDetail;
import com.oriental.manage.pojo.refund.OrgRefundInfo;
import com.oriental.manage.pojo.transaction.BizTrans;
import com.oriental.manage.pojo.transaction.OrderMain;
import com.oriental.manage.pojo.transaction.PayTrans;
import com.oriental.manage.pojo.transaction.TBusiTransDetail;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IRefundService {

    void queryPage(Pagination<OrgRefundInfo> pagination, OrgRefundInfo orgRefundInfo);

    Map summaryOrgRefund(OrgRefundInfo orgRefundInfo);

    void onlineRefund(OrgRefundInfo orgRefundInfo);

    List<PayTrans> validateOriginPay(List<PayTrans> list);

    void validateTotalAmt(OrderMain orderMain, BizTrans bizTrans, List<PayTrans> payTransList);

    void validateOrgTransRight(List<PayTrans> payTransList);

    void commonRefund(OrderMain orderMain, BizTrans bizTrans, List<PayTrans> payTransList, HttpServletRequest request) throws ParseException;

    void findCommonRefundOrder(Pagination<TBusiTransDetail> pagination, TBusiTransDetail busiRefundDetail);

    Map sumFindCommonRefundOrder( TBusiTransDetail busiRefundDetail);

    void searchBusiRefund(Pagination<BusiRefundDetail> pagination, BusiRefundDetail busiRefundDetail);

    Map sumBusiRefund(BusiRefundDetail busiRefundDetail);

    void audit(BusiRefundDetail busiRefundDetail);

    List<Map<String, Object>> selectRefundSucByDate(Date date);
}
