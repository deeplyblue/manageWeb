package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.DataDict;

import java.util.List;

/**
 * Created by lupf on 2016/5/6.
 */
public interface IDataDictService {

    ResponseDTO<Pagination<DataDict>> queryPage(DataDict dataDict,ResponseDTO<Pagination<DataDict>> responseDTO);

    List<DataDict> queryAll(DataDict dataDict);

    void queryPageByPay(Pagination<DataDict> pagination,DataDict dataDict);

    void getAllDataDict(Pagination<DataDict> pagination,DataDict dataDict);

    void addDataDict(ResponseDTO<String> responseDTO,DataDict dataDict);

    void updateDataDict(ResponseDTO<String> responseDTO,DataDict dataDict);

    void deleteDataDict(ResponseDTO<String> responseDTO,DataDict dataDict);

    int searData(DataDict dataDict);

}
