package com.oriental.manage.pojo.base;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjun on 2017/1/17.
 */
@Data
public class JobResponsibility  extends BaseModel{

    private String id;
    /**职责名称*/
    private String jobName;
    /**优先级*/
    private String priority;
    /**步骤*/
    private String step;
    /**步骤内容描述*/
    private String stepContent;
    /**创建人*/
    private String creator;
    /**创建时间*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date createTime;
    /**修改人*/
    private String modifier;
    /**最后修改时间*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date lastUpdTime;
    /**allcateed*/
    private boolean allocated;
}
