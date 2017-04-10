package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.BusitypeOrgRelationship;

import java.util.List;

/**
 * Created by wangjun on 2016/6/24.
 * 业务资金源支付机构银行强绑关系
 */
public interface BusitypeOrgRelationshipMapper {

    /**
     * 查询强绑关系
     * @param busitypeOrgRelationship
     * @return
     */
    List<BusitypeOrgRelationship> selectAll(BusitypeOrgRelationship busitypeOrgRelationship);

    int updateByPrimaryKeySelective(BusitypeOrgRelationship busitypeOrgRelationship);

    int insertSelective(BusitypeOrgRelationship busitypeOrgRelationship);

    int deleteByPrimaryKey(BusitypeOrgRelationship busitypeOrgRelationship);
    //验证是否已经配置
    int checkConfig(BusitypeOrgRelationship busitypeOrgRelationship);

}
