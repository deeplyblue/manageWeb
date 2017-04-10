package com.oriental.manage.dao.business;

import com.oriental.manage.pojo.business.MchntInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/25.
 */
public interface MchntInfoMapper {

    int insertMchntInfo(MchntInfo mchntInfo);

    List<MchntInfo> getAll(MchntInfo mchntInfo);

    int updateMchntInfo(MchntInfo mchntInfo);

    int deleteMchntInfo(MchntInfo mchntInfo);


}
