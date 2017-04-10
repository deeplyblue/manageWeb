package com.oriental.manage.pojo.merchant.trans;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Author: Yang xp
 * Date: 2016/5/23
 * Time: 18:03
 * Desc：商户业务类型
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class MerchantBusinessType extends BaseModel {

    private String id;
    /**
     * 商户代码
     */
    private String merchantCode;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 业务类型描述
     */
    private String businessTypeDesc;
    /**
     * 启用标识: <br/>
     * 0:关闭 <br/>
     * 1:启用  <br/>
     * 2:维护
     */
    private String enableType;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String modifierUser;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String lastUpdateTime;
}
