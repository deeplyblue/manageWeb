package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Desc: 业务资金源Mapper
 * Date: 2016/6/12
 * User: ZhouBin
 * See :
 */

@Setter
@Getter
public class BusiBank extends BaseModel {
    private static final long serialVersionUID = 1L;

    // 0 未选中 1选中
    private String choose;


    private int id;

    //业务类型
    private String busiType;

    //银行类型
    private String bankType;

    //银行代码
    private String bankCode;

    //银行代码描述
    private String bankCodeDesc;

    //是否可用
    private String enableFlag;

    //修改时间
    private Date lastUpdTime;

    //创建时间
    private Date createTime;

    //修改人
    private String modifier;

    //创建人
    private String creator;


}