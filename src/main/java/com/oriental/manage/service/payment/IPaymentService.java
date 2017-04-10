package com.oriental.manage.service.payment;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.payment.PaymentInfo;

import java.util.List;


public interface IPaymentService {

    /**
     * 主页面查询
     *
     * @param pagination  页面对象
     * @param paymentInfo 请求对象
     */
    void queryPage(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo);

    /**
     * 详细页面查询
     *
     * @param pagination  页面对象
     * @param paymentInfo 请求对象
     */
    void queryPaymentDetail(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo);

    /**
     * 校验当前批次是否被审核
     *
     * @param batchNo 批次号
     * @return 是否被处理
     */
    String checkAuditState(String batchNo);

    /**
     * 更新审核状态
     *
     * @param paymentInfo 请求对象
     * @return 更新结果
     */
    boolean updateAuditStateByBatchNo(PaymentInfo paymentInfo);

    /**
     * 启动线程，发起代付
     *
     * @param paymentInfo 请求对象
     */
    void startBatchPaymentThread(PaymentInfo paymentInfo);

    /**
     * 文件上传解析
     *
     * @param originFileName  文件名称
     * @param dfsFullFilename dfs全路径
     * @param dfsGroupName    dfs分组
     */
    void uploadFile(String originFileName, String dfsFullFilename, String dfsGroupName) throws Exception;

    /**
     * 根据批次号查询所有数据
     *
     * @param paymentInfo 请求对象
     * @return 结果集
     */
    List<PaymentInfo> queryListForDownLoad(PaymentInfo paymentInfo);

    /**
     * 中断后补代付
     *
     * @param batchNo 批次号
     */
    void paymentAgain(String batchNo) throws Exception;

    /**
     * 查询审核时间
     *
     * @param batchNo 批次号
     */
    PaymentInfo queryCheckDate(String batchNo);

    /**
     * 确认文件上传解析
     *
     * @param originFileName  文件名称
     * @param dfsFullFilename dfs全路径
     * @param dfsGroupName    dfs分组
     */
    void confirmUploadFile(String originFileName, String dfsFullFilename, String dfsGroupName);
}
