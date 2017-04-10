package com.oriental.manage.service.report;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.report.PayBusiCheckReportData;

/**
 * <ul>
 * <li>支付业务核对报表服务</li>
 * <li>User:蒯越 Date:2016/12/5 Time:15:37</li>
 * </ul>
 */
public interface IPayBusiCheckReportService {

    /**
     * 分页查询
     *
     * @param pagination 分页对象
     * @param query      查询条件
     */
    void pageQuery(Pagination<PayBusiCheckReportData> pagination, PayBusiCheckReportData query);
}
