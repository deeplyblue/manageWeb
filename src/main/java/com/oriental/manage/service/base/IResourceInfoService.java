package com.oriental.manage.service.base;

import com.oriental.manage.controller.system.MenuResourceController;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.ResourceInfo;
import com.oriental.manage.pojo.base.TreeView;

import java.util.List;

/**
 * Created by lupf on 2016/4/21.
 */
public interface IResourceInfoService {

    List<ResourceInfo> getResourceWithUserId(String userId);

    List<ResourceInfo> getAllResourceInfo();
    List<ResourceInfo> getAllResourceInfoByType(ResourceInfo resourceInfo);

    List<TreeView> sortAndAssemble(List<ResourceInfo> resourceInfoList);

    void addResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO);

    void updateResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO);

    void deleteResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO);

    List<ResourceInfo> getResourceWithRoleId(String id);
}
