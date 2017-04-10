package com.oriental.manage.dao.refund;

import com.oriental.manage.pojo.refund.BusiRefundInfo;
import com.oriental.manage.pojo.refund.OrgRefundInfo;

import java.util.List;
import java.util.Map;

public interface RefundMapper {

    List<OrgRefundInfo> queryPage(OrgRefundInfo orgRefundInfo);

    Map summaryOrgRefund(OrgRefundInfo orgRefundInfo);

    OrgRefundInfo queryOrgRefundById(String id);

    int updateOrgRefund(OrgRefundInfo orgRefundInfo);

    BusiRefundInfo queryBusiRefundInfoById(String busiRefundId);
}