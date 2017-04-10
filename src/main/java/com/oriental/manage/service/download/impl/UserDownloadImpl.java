package com.oriental.manage.service.download.impl;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.download.UserDownloadMapper;
import com.oriental.manage.pojo.download.UserDownload;
import com.oriental.manage.service.download.IUserDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/7/21.
 */
@Slf4j
@Service
public class UserDownloadImpl implements IUserDownloadService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserDownloadMapper userDownloadMapper;

    @Override
    public void queryPage(Pagination<UserDownload> pagination, UserDownload userDownload) {
        List<UserDownload> list=userDownloadMapper.getPage(userDownload);
        if(list != null && list.size() >0 ){
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }

    @Override
    public int add(UserDownload userDownload) {
        return userDownloadMapper.insertUserDownload(userDownload);
    }

    @Override
    public void updateById(UserDownload userDownload) {
        userDownloadMapper.updateUserDownload(userDownload);
    }
}
