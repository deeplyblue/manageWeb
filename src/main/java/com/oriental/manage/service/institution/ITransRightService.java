package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.institution.TransRight;

/**
 * Created by wangjun on 2016/5/11.
 */

public interface ITransRightService {

    void queryPage (Pagination<TransRight> pagination, TransRight transRight);

    TransRight queryTransRight(TransRight transRight);
}
