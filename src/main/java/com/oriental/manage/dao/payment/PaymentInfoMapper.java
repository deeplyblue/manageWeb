package com.oriental.manage.dao.payment;

import com.oriental.manage.pojo.payment.PaymentInfo;

import java.util.List;

public interface PaymentInfoMapper {

    List<PaymentInfo> selectBatchList(PaymentInfo paymentInfo);

    List<PaymentInfo> queryPaymentDetail(PaymentInfo paymentInfo);

    String checkAuditState(String batchNo);

    int updateAuditStateByBatchNo(PaymentInfo paymentInfo);

    int getCountByBatchNo(PaymentInfo paymentInfo);

    void updateBatchPaymentDetail(PaymentInfo paymentInfo);

    int isQueryOrderNoExist(String queryOrderNo);

    void batchInsert(List<PaymentInfo> paymentInfoList);

    String queryMaxBatchNo(String batch);

    List<PaymentInfo> queryListForDownLoad(PaymentInfo paymentInfo);

    List<PaymentInfo> paymentAgain(PaymentInfo paymentInfo);

    PaymentInfo queryCheckDate(String batchNo);
}