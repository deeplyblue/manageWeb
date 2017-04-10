package com.oriental.manage.service.report;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.report.GLCollectionReportData;

/**
 * <ul>
 * <li>总账收款核对报表服务</li>
 * <li>User:蒯越 Date:2016/12/1 Time:14:49</li>
 * </ul>
 */
public interface IGLCollectionReportService {

    /**
     * 分页查询
     *
     * @param pagination 分页对象
     * @param query      查询条件
     */
    void pageQuery(Pagination<GLCollectionReportData> pagination, GLCollectionReportData query);
}
