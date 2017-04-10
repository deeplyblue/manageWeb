package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.OrganizeInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/4.
 */
public interface OrganizeInfoMapper {

    //查询支付机构
    List<OrganizeInfo> selectByPay(OrganizeInfo organizeInfo);
    //新增支付机构
    int insertOrganizeInfo (OrganizeInfo organizeInfo);
    //修改支付机构
    int updateOrganizeInfo(OrganizeInfo organizeInfo);
    //删除
    int deleteByPrimaryKey(OrganizeInfo organizeInfo);

    //获取支付机构姓名
    List<OrganizeInfo> getByCompanyCode();
    List<OrganizeInfo> getAll();

    List<OrganizeInfo> checkOrg(OrganizeInfo organizeInfo);

    OrganizeInfo queryChannelType(OrganizeInfo organizeInfo);

}
