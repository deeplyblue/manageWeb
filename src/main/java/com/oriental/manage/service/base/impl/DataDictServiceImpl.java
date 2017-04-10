package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.base.DataDictMapper;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.base.IDataDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lupf on 2016/5/6.
 */
@Slf4j
@Service
public class DataDictServiceImpl implements IDataDictService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataDictMapper dataDictMapper;

    @Override
    public ResponseDTO<Pagination<DataDict>> queryPage(DataDict dataDict, ResponseDTO<Pagination<DataDict>> responseDTO) {
        return null;
    }

    @Override
    public List<DataDict> queryAll(DataDict record) {
        DataDict dataDict = new DataDict();
        dataDict.setItemName(record.getItemName());
        dataDict.setColName(record.getColName());
        dataDict.setItemType(record.getItemType());
        return dataDictMapper.selectBySelective(dataDict);
    }
    @Override
   public void queryPageByPay(Pagination<DataDict> pagination,DataDict dataDict){

        List<DataDict> list=dataDictMapper.selectByPay(dataDict);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }

    }


    @Override
    public void getAllDataDict(Pagination<DataDict> pagination, DataDict dataDict) {
        List<DataDict> list=dataDictMapper.getPage(dataDict);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void addDataDict(ResponseDTO<String> responseDTO, DataDict dataDict) {
        if(dataDictMapper.insert(dataDict)>0){
            responseDTO.setSuccess(true);
        }else{

            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateDataDict(ResponseDTO<String> responseDTO, DataDict dataDict) {
        if(dataDictMapper.updateByPrimaryKey(dataDict)>0){
            responseDTO.setSuccess(true);
        }else{

            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteDataDict(ResponseDTO<String> responseDTO, DataDict dataDict) {
        if(dataDictMapper.deleteByPrimaryKey(dataDict.getId())>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public int searData(DataDict dataDict) {
        return dataDictMapper.checkDataDict(dataDict);
    }







}
