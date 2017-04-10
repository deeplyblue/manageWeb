package com.oriental.manage.service.base;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.RoleInfo;

import java.util.List;

/**
 * Created by lupf on 2016/4/26.
 */
public interface IRoleInfoService {
    List<RoleInfo> getRolesByUserId(String userId);

    List<RoleInfo> getAllRole();

    void queryPage(Pagination<RoleInfo> pagination, RoleInfo roleInfo);

    void insert(RoleInfo roleInfo, ResponseDTO<String> responseDTO);

    void update(RoleInfo roleInfo, ResponseDTO<String> responseDTO);

    void delete(RoleInfo roleInfo, ResponseDTO<String> responseDTO);

    RoleInfo getRolesById(String id);
    List<RoleInfo> getRoleByType(String type);

    boolean checkRoleNam(RoleInfo roleInfo);
}
