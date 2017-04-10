package com.oriental.manage.dao.merchant;

import com.oriental.manage.pojo.merchant.ClearCfg;

import java.util.List;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface ClearCfgMapper {

    int insertClearCfg(ClearCfg clearCfg);

    List<ClearCfg> queryPage(ClearCfg clearCfg);

    int updateClearCfg(ClearCfg clearCfg);

    int deleteOldCfg(ClearCfg clearCfg);
}
