package com.oriental.manage.service.report;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.report.GLCollectionReportData;
import com.oriental.manage.pojo.report.GLPaymentReportData;

/**
 * <ul>
 * <li>总账付款核对报表服务</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:49</li>
 * </ul>
 */
public interface IGLPaymentReportService {

    /**
     * 分页查询
     *
     * @param pagination 分页对象
     * @param query      查询条件
     */
    void pageQuery(Pagination<GLPaymentReportData> pagination, GLPaymentReportData query);
}
