package com.oriental.manage.service.download;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.download.UserDownload;

/**
 * Created by wangjun on 2016/7/21.
 * 下载中心实现类
 */
public interface IUserDownloadService {

    void queryPage(Pagination<UserDownload> pagination,UserDownload userDownload);

    int add(UserDownload userDownload);

    void updateById(UserDownload userDownload);
}
