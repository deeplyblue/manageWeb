package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.FeeCfg;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface IFeeCfgSerivce {

    void queryPage(Pagination<FeeCfg> pagination, FeeCfg feeCfg);

    void addFeeCfg(FeeCfg feeCfg, ResponseDTO<String> responseDTO);

    void deleteFeeCfg(FeeCfg feeCfg,ResponseDTO<String> responseDTO);

    void upadteFeeCfg(FeeCfg feeCfg,ResponseDTO<String> responseDTO);

}
