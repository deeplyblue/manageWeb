package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.ContactInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/5.
 */
public interface ContactInfoMapper {

    /**
     * 查询所有支付机构联系人
     * @param contactInfo
     * @return
     */
    List<ContactInfo> queryPage(ContactInfo contactInfo);

    int insertContractInfo(ContactInfo contactInfo);

    int updateContactInfo(ContactInfo contactInfo);

    int deleteByPrimaryKey(ContactInfo contactInfo);



}
