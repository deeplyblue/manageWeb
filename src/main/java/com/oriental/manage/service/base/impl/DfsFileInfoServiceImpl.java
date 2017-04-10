package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.base.DfsFileInfoMapper;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.service.base.IDfsFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lupf on 2016/5/31.
 */
@Slf4j
@Service
public class DfsFileInfoServiceImpl implements IDfsFileInfoService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DfsFileInfoMapper dfsFileInfoMapper;

    @Override
    public void insert(DfsFileInfo dfsFileInfo) {
        dfsFileInfoMapper.insertSelective(dfsFileInfo);
    }

    @Override
    public List<DfsFileInfo> searchDfsFileInfo( DfsFileInfo dfsFileInfo) {
        return dfsFileInfoMapper.searchDfsFileInfo(dfsFileInfo);
    }
    @Override
    public void searchDfsFileInfo1(Pagination<DfsFileInfo> pagination, DfsFileInfo dfsFileInfo) {
        List<DfsFileInfo> list=dfsFileInfoMapper.searchDfsFileInfo(dfsFileInfo);
        if(list!=null && list.size()>0){
        pagination.setList(list);
        pagination.setRowCount(list.get(0).getRowCount());
        }else{
            pagination.setList(null);
        }


    }
    @Override
    public int update(DfsFileInfo dfsFileInfo) {
        return dfsFileInfoMapper.updateByPrimaryKeySelective(dfsFileInfo);
    }
}
