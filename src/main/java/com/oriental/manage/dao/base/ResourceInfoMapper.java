package com.oriental.manage.dao.base;

import com.oriental.manage.pojo.base.ResourceInfo;

import java.util.List;

public interface ResourceInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String rsrcCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    int insert(ResourceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    int insertSelective(ResourceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    ResourceInfo selectByPrimaryKey(String rsrcCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ResourceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_resource_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ResourceInfo record);

    List<ResourceInfo> selectUserResource(String userId);

    List<ResourceInfo> selectBySelective(ResourceInfo resourceInfo);

    List<ResourceInfo> queryByRoleId(String id);
}