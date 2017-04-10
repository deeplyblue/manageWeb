package com.oriental.manage.service.base;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.AuthCfg;

import java.util.List;

/**
 * Created by lupf on 2016/4/22.
 */
public interface IAuthCfgService {
    void allocateResource(AuthCfg authCfg, String[] ids, ResponseDTO<String> responseDTO);

    List<AuthCfg> checkAuthCfg(String id);
}
