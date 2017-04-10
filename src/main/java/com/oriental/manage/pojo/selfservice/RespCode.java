package com.oriental.manage.pojo.selfservice;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjun on 2016/7/20.
 * 响应码(Cache)实体
 */
@Data
public class RespCode extends BaseModel {

    /**响应编码6位：2+4  2：机构类型4：错误码**/
    private String respCode;

    /**中文描述**/
    private String respDesc;

    /**dict:tab机构类型:10:内部平台20:支付机构30:业务平台**/
    private String orgType;

    /**机构代码**/
    private String orgCode;

    /**机构响应码**/
    private String orgRespCode;

    /**机构响应描述**/
    private String orgRespDesc;

    /**记录生成日期**/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date createTime;

    /**记录最后更新日期**/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date lastUpdTime;

    /**编号**/
    private String id;

    /**旧机构错误码**/
    private String oldBankErrCode;

    /**旧机构错误码描述**/
    private String oldBankErrDesc;
}
