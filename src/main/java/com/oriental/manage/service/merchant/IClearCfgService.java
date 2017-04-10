package com.oriental.manage.service.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.ClearCfg;

/**
 * Created by wangjun on 2016/5/16.
 */
public interface IClearCfgService {

    void queryPage(Pagination<ClearCfg> pagination,ClearCfg clearCfg);

    void addClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg);

    void updateClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg);

    void deleteClearCfg(ResponseDTO<String> responseDTO, ClearCfg clearCfg);
}
