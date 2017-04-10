package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.FeeCfg;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface FeeCfgMapper {

    List<FeeCfg> queryPage(FeeCfg feeCfg);

    int insertFeeCfg(FeeCfg feeCfg);

    int deleteFeeCfg(FeeCfg feeCfg);

    int updateFeeCfg(FeeCfg feecfg);
}
