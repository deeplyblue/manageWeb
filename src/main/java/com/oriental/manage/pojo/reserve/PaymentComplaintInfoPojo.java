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
 * @time: 10:42
 * @see: 链接到其他资源
 * @since: 1.0
 * 投诉信息管理
 */
@Slf4j
@Data
public class PaymentComplaintInfoPojo extends BaseModel{
    /**
     * id
     */
    private Integer id;
    /**
     * 被投诉对象类型
     */
    private String type;
    /**
     * 被投诉银行行号/机构编号
     */
    private String code;
    /**
     * 投诉类型 01：电话投诉02：信访投诉03：分支行投诉
     */
    private String accuseType;
    /**
     * 投诉信息
     */
    private String accuseInfo;
    /**
     * 投诉人
     */
    private String accuse;
    /**
     * 投诉电话
     */
    private String complainTel;
    /**
     * 投诉传真
     */
    private String accuseFax;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 投诉时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date accuseDate;
    /**
     *修改人
     */
    private String createBy;
    /**
     *创建时间  yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    /**
     *修改人
     */
    private String updateBy;
    /**
     *修改时间  yyyymmddHHMMSS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    /**
     * 原报文标示号
     */
    private String messageMK;
    /**
     * 处理人
     */
    private String handle;
    /**
     * 处理时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date handleTime;

    /**
     * 处理人电话
     */
    private String handleTel;
    /**
     * 处理结果
     */
    private String handleRes;
    /**
     * queryBean beginDate
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    /**
     * queryBean endDate
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date endTime;
    /**
     * 处理状态
     */
    private String handleStatus;
}
