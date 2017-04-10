package com.oriental.manage.dao.business;

import com.oriental.manage.pojo.business.MchntBank;

import java.util.List;

/**
 * Created by wangjun on 2016/5/24.
 */
public interface MchntBankMapper {

    List<MchntBank> queryPage (MchntBank mchntBank);

    int deleteByMchntCode(MchntBank mchntBank);

    int insertMchntBank(MchntBank mchntBank);

    int updateMchntBankByMchntCode(MchntBank mchntBank);
}
