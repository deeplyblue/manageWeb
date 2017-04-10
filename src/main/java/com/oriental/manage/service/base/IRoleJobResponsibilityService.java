package com.oriental.manage.service.base;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.RoleJobResponsibility;

import java.util.List;

/**
 * Created by jinxin on 2017/2/20.
 */
public interface IRoleJobResponsibilityService {

    void allocateResource(RoleJobResponsibility roleJobResponsibility, String[] ids, ResponseDTO<String> responseDTO);

    List<RoleJobResponsibility> queryChecked(RoleJobResponsibility roleJobResponsibility);

}
