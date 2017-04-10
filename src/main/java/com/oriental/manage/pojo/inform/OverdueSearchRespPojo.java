package com.oriental.manage.pojo.inform;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 类注释
 *
 * @author: hz
 * @date: 2017/1/13
 * @time: 16:33
 * @see: 链接到其他资源
 * @since: 1.0
 */
@Data
public class OverdueSearchRespPojo extends BaseModel {
    /**
     * 起始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 是否已说明
     * 01 未说明 02 已说明
     */
    private String isRemark;
}
