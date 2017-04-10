package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.IplimitCfg;

/**
 * Created by yutao on 2016/10/31.
 */
public interface IpLimitCfgService {
    void ipValidate(IplimitCfg queryIp);

    void queryPage(Pagination<IplimitCfg> pagination, IplimitCfg iplimitCfg);

    void deleteIplimitCfg(IplimitCfg iplimitCfg, ResponseDTO<String> responseDTO);

    boolean selectByUser( IplimitCfg iplimitCfg);

    void addNewIplimit(IplimitCfg iplimitCfg, ResponseDTO<String> responseDTO) throws Exception;

    boolean isInnerIp(String ip);
}
