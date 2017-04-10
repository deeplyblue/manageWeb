package com.oriental.manage.pojo.base;

import lombok.Data;

import java.util.Date;

@Data
public class RoleJobResponsibility {
    /**
     * 主键id
     *
     */
    private String id;

    /**
     * j角色id
     *
     */
    private String roleId;

    /**
     *岗位职责id
     *
     */
    private String jobId;

    /**
     * 创建时间
     *
     */
    private Date createTime;

    /**
     *更新时间
     *
     */
    private Date updateTime;


}