package com.oriental.manage.dao.selfservice;

import com.oriental.manage.pojo.selfservice.RespCode;

import java.util.List;

/**
 * Created by wangjun on 2016/7/20.
 */
public interface RespCodeMapper {

    List<RespCode> queryAllRespCode(RespCode respCode);

    int insertModel(RespCode respCode);

    int delete(RespCode respCode);

    int update(RespCode respCode);


}
