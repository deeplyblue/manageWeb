package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/21
 * @time: 10:45
 * @see: 链接到其他资源
 * @since: 1.0
 * 支付机构联系人
 */
@Data
public class PaymentContactInfoPojo extends BaseModel {

    private String id;
    /**
     *联系人姓名
     */
    private String connName;
    /**
     *归属机构编号
     */
    private String ownOrgNO;
    /**
     *职务
     */
    private String rule;
    /**
     *办公电话 禁止中文
     */
    private String tel;
    /**
     *手机
     */
    private String mobile;
    /**
     *邮件
     */
    private String email;
    /**
     *备注
     */
    private String remarks;
    /**
     *主要联系人 01：是 02：否
     */
    private String mainConn;
    /**
     *离职标识 01：在职 02：离职中 03：已离职
     */
    private String offStatus;
    /**
     *操作类型 00：新增 01：修改 02：删除
     */
    private String operateType;
    /**
     * 数据状态 01：生效 02：未生效
     */
    private String dataStatus;
    /**
     *操作人（创建人）
     */
    private String operator;
    /**
     *操作时间（创建时间）  yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;
    /**
     *报送msgId
     */
    private String msgId;
    /**
     * beginTime
     */
    private String beginTime;
    /**
     * endTime
     */
    private String endTime;
    /**
     * 表示为人行下发数据 置为01
     */
    private String centerStatus;
    /**
     * 审核操作状态
     */
    private String auditStatus;
    /**
     * deleteFlag
     */
    private String deleteFlag;
}
