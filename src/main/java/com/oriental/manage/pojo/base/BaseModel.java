package com.oriental.manage.pojo.base;

import lombok.Data;

/**
 * Created by lupf on 2016/4/28.
 */
@Data
public class BaseModel {

    //传入数据库
    private int startRow = 0;
    private int pageSize = 10;
    //当前页码
    private int pageNum = 1;
    private int rowCount;

    public int getStartRow() {
        return pageSize * (pageNum - 1);
    }
}
