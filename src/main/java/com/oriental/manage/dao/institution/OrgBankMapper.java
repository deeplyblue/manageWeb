package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.business.MchntBank;
import com.oriental.manage.pojo.institution.OrgBank;

import java.util.List;

/**
 * Created by wangjun on 2016/5/9.
 */
public interface OrgBankMapper {

    List<OrgBank> getAll(OrgBank orgBank);

    List<OrgBank>queryOrgBankInfoForPayChanExtendPage (OrgBank orgBank);

     int deleteOrgBank(OrgBank bean);
    /**
     * 通过商户代码得到商户对应的外系统银行配置
     */
    List<OrgBank> getOrgBankByMchntCode(MchntBank bean);

    List<OrgBank> searchByKey(OrgBank bean);

    List<OrgBank> getOrgBankByBankCode(OrgBank bean);

    List<OrgBank> getBankInfoByConnChannel(OrgBank bean);

    List<OrgBank> getBankInfoAndOrgCode(OrgBank bean);

    int insertOrgBank(OrgBank orgBank);

    OrgBank searchByParams(OrgBank bean);

    int editPhoneCfg(OrgBank orgBank);

    List<OrgBank> getAllInfo();

    List<OrgBank> getRecordByBank(OrgBank orgBank);

    int auditOrgBank(OrgBank orgBank);
}
