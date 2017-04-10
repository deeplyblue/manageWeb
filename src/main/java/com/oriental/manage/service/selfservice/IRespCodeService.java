package com.oriental.manage.service.selfservice;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.selfservice.RespCode;

/**
 * Created by wangjun on 2016/7/20.
 * 错误响应码实现类
 */
public interface IRespCodeService {

    //查询错误码
    void queryPage (Pagination<RespCode> pagination, RespCode respCode);

    void addRespCode(ResponseDTO<String> responseDTO, RespCode respCode);

    void updateRespCode(ResponseDTO<String> responseDTO, RespCode respCode);

    void deleteRespCode(ResponseDTO<String> responseDTO, RespCode respCode);
}
