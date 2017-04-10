package com.oriental.manage.dao.base;

import com.oriental.manage.pojo.base.IplimitCfg;

import java.util.List;

public interface IplimitCfgMapper {
    int deleteByPrimaryKey(String id);

    int insert(IplimitCfg record);

    int insertSelective(IplimitCfg record);

    IplimitCfg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IplimitCfg record);

    int updateByPrimaryKey(IplimitCfg record);

    IplimitCfg selectIp(IplimitCfg queryIp);

    List<IplimitCfg> selectIplimit(IplimitCfg queryIp);

    List<IplimitCfg> selectByUser(IplimitCfg queryIp);
}