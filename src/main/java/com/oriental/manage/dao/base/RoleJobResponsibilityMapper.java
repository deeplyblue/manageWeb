package com.oriental.manage.dao.base;

import com.oriental.manage.pojo.base.RoleJobResponsibility;

import java.util.List;

public interface RoleJobResponsibilityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_auth_cfg
     *
     * @mbggenerated
     */
    int delete(RoleJobResponsibility roleJobResponsibility);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_auth_cfg
     *
     * @mbggenerated
     */
    int insert(RoleJobResponsibility roleJobResponsibility);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_auth_cfg
     *
     * @mbggenerated
     */
    int update(RoleJobResponsibility roleJobResponsibility);

    List<RoleJobResponsibility> selectRoleJobResponsibility(RoleJobResponsibility roleJobResponsibility);

}