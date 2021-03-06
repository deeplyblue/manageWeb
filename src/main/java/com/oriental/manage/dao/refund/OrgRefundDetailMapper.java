package com.oriental.manage.dao.refund;

import com.oriental.manage.pojo.refund.OrgRefundDetail;

import java.util.List;
import java.util.Map;

public interface OrgRefundDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    int insert(OrgRefundDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    int insertSelective(OrgRefundDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    OrgRefundDetail selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrgRefundDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_org_refund_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrgRefundDetail record);

    void updateByBusiIdKeySelective(OrgRefundDetail org);

    List<Map<String, Object>> selectReport(OrgRefundDetail query);
}