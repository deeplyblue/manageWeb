package com.oriental.manage.pojo.institution;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Created by wangjun on 2016/5/10.
 * 交易权限信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class TransRight  extends BaseModel {

    //
    /**
     * 编号
     */
    private String id;

    //:
    /**
     * dict:comm接入渠道
     */
    private String connChannel;

    //
    /**
     * 交易代码6位分两段,2位大类4位编号
     */
    private String transCode;

    //dict:comm代码类型,商户或机构:
    private String companyType;

    //商户或机构代码
    private String companyCode;

    //创建日期
    private Date createDate;

    //关闭日期
    private Date closeDate;

    //dict:tab当前状态:0:关闭1:启用
    private String rightStatus;

    //dict:tab是否可退款:0:可退1:不可退
    private String refundFlag;

    //dict:tab是否分账交易权限:0:否1:是
    private String splitRightFlag;

    //受理机构")
    private String acqOrgCode;

    //dict:comm正反交易标识:0:反交易1:正交易
    private String reverseFlag;

    //记录生成日期
    private Date createTime;

    //记录最后更新日期
    private Date lastUpdTime;

    //创建人
    private String creator;

    //修改人
    private String modifyUser;

    //模板No
    private String templateNo;

    private String connChannelName;

    private String transCodeName;

    private String companyName;

    private String searchType;

    private TransRightTemplate transRightTemplate;

    private int rowSpan;

    private String tempParams;

}


