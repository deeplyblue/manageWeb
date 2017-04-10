package com.oriental.manage.pojo.download;

import com.oriental.manage.pojo.base.BaseModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wangjun on 2016/7/21.
 * 用户下载
 */
@Data
public class UserDownload extends BaseModel {

    /**编号*/
    private String id;
    /**用户账号*/
    private String userName;
    /**文件名称*/
    private String fileName;
    /**状态:  A:请求  B:成功  C:失败*/
    private String status;
    /**记录生成日期*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date createTime;
    /**记录最后更新日期*/
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date lastUpdTime;
    /**FastDFS上传至StorageService的文件ID*/
    private String remoteFileName;

}
