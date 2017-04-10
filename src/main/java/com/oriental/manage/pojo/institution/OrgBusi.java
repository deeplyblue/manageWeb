package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangjun on 2016/6/2.
 * 业务机构基本信息表
 */
@Data
public class OrgBusi extends BaseModel {

    /**配置ID**/
    private String id;
    /**银行代码**/
    private String orgCode;
    /**业务渠道描述：代付：银行代码描述**/
    private String busiChannelDesc;
    /**业务渠道：代付：银行代码**/
    private String busiChannel;
    /**外部业务渠道：代付：外部银行代码**/
    private String orgBusiChannel;
    /**外部业务渠道描述代付：外部银行代码描述**/
    private String orgBusiChannelDesc;
    /**同步异步标识：00：同步01：异步**/
    private String synchronousFlag;
    /**业务类型：0011000：代付**/
    private String transCode;
    /**业务类型描述**/
    private String transCodeDesc;
    /**公私标识:00:对私01:对公**/
    private String busiFlag;
    /**交易渠道：01：web02：wap03：SMS04：ivr05：客户端**/
    private String connChannel;
    /**记录生成日期**/
    private Date createTime;
    /**最后修改时间**/
    private Date lastUpdTime;
    /**dict:comm启用标识:0:关闭1:启用**/
    private String enableFlag;
    /**优先级**/
    private String priorityLevel;
    /**类路径**/
    private String classPath;
    private String quotaFlag;
    private String tid;
}
