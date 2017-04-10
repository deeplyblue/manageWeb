package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.TransRight;

import java.util.List;

/**
 * Created by wangjun on 2016/5/11.
 * 支付机构交易权限配置
 */
public interface TransRightMapper {

    List<TransRight> getAll(TransRight transRight);

    TransRight queryOne(TransRight transRight);
}
