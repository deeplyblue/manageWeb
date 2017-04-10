package com.oriental.manage.dao.download;

import com.oriental.manage.pojo.download.UserDownload;

import java.util.List;

/**
 * Created by wangjun on 2016/7/21.
 *
 */
public interface UserDownloadMapper {

    List<UserDownload> getPage(UserDownload userDownload);

    int insertUserDownload(UserDownload userDownload);

    int updateUserDownload(UserDownload userDownload);
}
