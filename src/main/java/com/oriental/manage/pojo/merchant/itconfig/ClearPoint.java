package com.oriental.manage.pojo.merchant.itconfig;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 12:32
 * Desc：结算点
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClearPoint extends BaseModel {

    private String id;

    /**
     * 结算周期的值，<br/>05：周结，<br/>06：月结
     */
    private String itemKey;

    /**
     * 结算周期描述
     */
    private String itemDesc;

    /**
     * 结算点的值，以，隔开
     */
    private String itemValue;

    /**
     * 记录生成日期
     */
    private Date createTime;

    /**
     * 记录最后更新日期
     */
    private Date lastUpdTime;

}
