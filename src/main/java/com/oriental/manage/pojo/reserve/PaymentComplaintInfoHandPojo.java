package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/22
 * @time: 13:49
 * @see: 链接到其他资源
 * @since: 1.0
 * 投诉信息管理_处理报文
 */
@Slf4j
@Data
public class PaymentComplaintInfoHandPojo extends BaseModel{

    private String id;
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
    private String handleTime;

    /**
     * 处理人电话
     */
    private String handleTel;
    /**
     * 处理结果
     */
    private String handleRes;
}

