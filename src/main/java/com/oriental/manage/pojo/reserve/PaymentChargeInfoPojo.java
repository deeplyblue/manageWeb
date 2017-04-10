package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/22
 * @time: 10:51
 * @see: 链接到其他资源
 * @since: 1.0
 * 支付机构收费管理
 */
@Slf4j
@Data
public class PaymentChargeInfoPojo extends BaseModel {
    /**
     * ID
     */
    private String id;
    /**
     * 机构编号
     */
    private String orgNo;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 收费项目
     */
    private String proName;
    /**
     * 收费开始时间
     */
    private String startDate;
    /**
     * 收费结束时间
     */
    private String endDate;
    /**
     * 收费内容
     */
    private String content;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 操作类型 00：新增 01：修改 02：删除
     */
    private String operateType;
    /**
     * 数据状态
     */
    private String dataStatus;
    /**
     * 操作人（创建人）
     */
    private String operator;
    /**
     * 操作时间（创建时间）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String operateTime;
    /**
     *修改人
     */
    private String updateBy;
    /**
     *修改时间  yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;
    /**
     * 备注
     */
    private String remarkDesc;
    /**
     * 删除标示
     */
    private String deleteFlag;
    /**
     *报送msgId
     */
    private String msgId;
}
