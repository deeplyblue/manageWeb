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
 * @time: 16:30
 * @see: 链接到其他资源
 * @since: 1.0
 */
@Data
public class BankDifferSearchRespPojo extends BaseModel {
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
     * 银行行号
     */
    private String bankCode;

    /**
     * 账号
     */
    private String accountNo;
}
