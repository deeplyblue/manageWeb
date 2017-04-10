package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.base.DfsFileInfo;

import java.util.List;

/**
 * Created by lupf on 2016/5/31.
 */
public interface IDfsFileInfoService {
    void insert(DfsFileInfo dfsFileInfo);
    List<DfsFileInfo> searchDfsFileInfo( DfsFileInfo dfsFileInfo);
    void searchDfsFileInfo1(Pagination<DfsFileInfo> pagination, DfsFileInfo dfsFileInfo);
    int update(DfsFileInfo dfsFileInfo);
}
