package com.oriental.manage.pojo.reserve;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2016/12/23
 * @time: 16:21
 * @see: 链接到其他资源
 * @since: 1.0
 */
@Slf4j
@Data
public class InfoManagementApplyPojo extends BaseModel {
    /**
     *ID
     */
    private String id;
    /**
     * msgId
     */
    private String msgId;
    /**
     *参与者类型
     */
    private String type;
    /**
     *行号/机构编号
     */
    private String code;
    /**
     *申请类型
     */
    private String applyMsgType;
    /**
     *删除标示
     */
    private String deleteFlag;
    /**
     * 创建人
     */
    private String creatMan;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 申请状态
     */
    private String applyStatus;
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
     * pkgNo
     */
    private String pkgNo;
}
