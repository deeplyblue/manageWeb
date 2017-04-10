package com.oriental.manage.controller.system;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.base.ResourceInfo;
import com.oriental.manage.pojo.base.TreeView;
import com.oriental.manage.service.base.IResourceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lupf on 2016/4/26.
 */
@Slf4j
@Controller
@RequestMapping("system/menu")
public class MenuResourceController {

    @Autowired
    private IResourceInfoService resourceInfoService;

    @RequestMapping("init")
    public String searchMenuResource() {

        return "system/searchMenuResource";
    }

    @RequiresPermissions("menu_search")
    @OperateLogger(content = "查询菜单", operationType = OperateLogger.OperationType.R)
    @RequestMapping("searchDetail")
    @ResponseBody
    public List<TreeView> searchMenuResourceDetail() {
        List<TreeView> treeList = null;
        try {
            List<ResourceInfo> resourceInfoList = resourceInfoService.getAllResourceInfo();
            treeList = resourceInfoService.sortAndAssemble(resourceInfoList);
        } catch (Exception e) {
            log.error("加载菜单树失败", e);
        }

        return treeList;
    }

    @RequiresPermissions("menu_search")
    @OperateLogger(content = "新增菜单", operationType = OperateLogger.OperationType.C, tables = "T_RESOURCE_INFO")
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO add(TreeView treeView) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ResourceInfo resourceInfo = new ResourceInfo();
            BeanUtils.copyProperties(resourceInfo, treeView);
            resourceInfo.setRsrcName(treeView.getText());
            resourceInfo.setRsrcUrl(treeView.getHref());
            resourceInfo.setRsrcDesc(treeView.getDesc());
            resourceInfo.setRsrcOperation(treeView.getOperation());

            resourceInfoService.addResourceInfo(resourceInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增资源失败", e);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }

    @RequiresPermissions("menu_search")
    @OperateLogger(content = "修改菜单", operationType = OperateLogger.OperationType.U, tables = "T_RESOURCE_INFO")
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO update(TreeView treeView) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ResourceInfo resourceInfo = new ResourceInfo();
            BeanUtils.copyProperties(resourceInfo, treeView);
            if (StringUtils.isBlank(resourceInfo.getParentRsrcCode())){
                resourceInfo.setParentRsrcCode("0");
            }
            resourceInfo.setRsrcName(treeView.getText());
            resourceInfo.setRsrcUrl(treeView.getHref());
            resourceInfo.setRsrcDesc(treeView.getDesc());
            resourceInfo.setRsrcOperation(treeView.getOperation());

            resourceInfoService.updateResourceInfo(resourceInfo, responseDTO);
        } catch (Exception e) {
            log.error("修改资源失败", e);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @RequiresPermissions("menu_search")
    @OperateLogger(content = "删除菜单", operationType = OperateLogger.OperationType.D, tables = "T_RESOURCE_INFO")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO delete(TreeView treeView) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ResourceInfo resourceInfo = new ResourceInfo();
            resourceInfo.setRsrcCode(treeView.getRsrcCode());
            resourceInfoService.deleteResourceInfo(resourceInfo, responseDTO);
        } catch (Exception e) {
            log.error("删除资源失败", e);
            responseDTO.setMsg("删除失败");
        }
        return responseDTO;
    }
}
