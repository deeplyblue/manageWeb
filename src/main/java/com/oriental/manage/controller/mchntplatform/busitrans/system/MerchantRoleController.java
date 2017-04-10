package com.oriental.manage.controller.mchntplatform.busitrans.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.ResourceInfoMapper;
import com.oriental.manage.pojo.base.*;
import com.oriental.manage.service.base.IAuthCfgService;
import com.oriental.manage.service.base.IResourceInfoService;
import com.oriental.manage.service.base.IRoleInfoService;
import com.oriental.manage.service.base.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjun on 2016/7/8.
 * 商户侧查询角色
 */
@Slf4j
@Controller
@RequestMapping("mchntplatform/merchantRole")
public class MerchantRoleController {
    @Autowired
    private IRoleInfoService roleInfoService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IResourceInfoService resourceInfoService;
    @Autowired
    private IAuthCfgService authCfgService;

    @RequestMapping("init")
    @RequiresPermissions("merchantRole_search")
    public String init(){
        return "mchntplatform/system/searchMerchantRoleInfo";
    }
    @RequestMapping("toAdd")
    @RequiresPermissions("merchantRole_add")
    public String toAdd(){ return  "mchntplatform/system/addMerchantRole";}
    @RequestMapping("toUpdate")
    @RequiresPermissions("merchantRole_update")
    public String toUpdate(){ return  "mchntplatform/system/addMerchantRole";}

    @OperateLogger(content = "查询商户测角色",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("merchantRole_search")
    public ResponseDTO<Pagination<RoleInfo>> queryPage(Pagination<RoleInfo> pagination, RoleInfo roleInfo) {
        ResponseDTO<Pagination<RoleInfo>> responseDTO = new ResponseDTO<Pagination<RoleInfo>>();
        try {
            UserInfo user=userInfoService.getUserByName(String.valueOf(SessionUtils.getUserName()));
            roleInfo.setRoleType(user.getUserType());
            roleInfo.setAreaCode(SessionUtils.getMchntCode());
            roleInfoService.queryPage(pagination, roleInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
    @OperateLogger(content = "新增商户测角色",operationType = OperateLogger.OperationType.C,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("merchantRole_add")
    public ResponseDTO<String> add(RoleInfo roleInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            UserInfo user=userInfoService.getUserByName(String.valueOf(SessionUtils.getUserName()));
            roleInfo.setRoleType(user.getUserType());
            roleInfoService.insert(roleInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "修改商户测角色",operationType = OperateLogger.OperationType.U,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("merchantRole_update")
    public ResponseDTO<String> update(RoleInfo roleInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            roleInfoService.update(roleInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "删除商户测角色",operationType = OperateLogger.OperationType.D,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("delete")
    @ResponseBody
    @RequiresPermissions("merchantRole_delete")
    public ResponseDTO<String> delete(RoleInfo roleInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            roleInfoService.delete(roleInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toAllocate")
    @RequiresPermissions("merchantRole_toAllocate")
    public String toAllocate() {

        return "system/resourceAllocate";
    }
    @OperateLogger(content = "商户测角色权限分配",operationType = OperateLogger.OperationType.U,tables = "T_ROLE_INFO")
    @RequestMapping("allocate")
    @ResponseBody
    @RequiresPermissions("merchantRole_toAllocate")
    public ResponseDTO<String> allocate(String id, @RequestParam(value = "ids[]") String[] ids) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            AuthCfg authCfg = new AuthCfg();
            authCfg.setRoleId(id);
            authCfgService.allocateResource(authCfg, ids, responseDTO);

        } catch (Exception e) {
            responseDTO.setSuccess(false);
            log.error("操作失败", e);
        }
        return responseDTO;
    }

    @OperateLogger(content = "初始化角色权限",operationType = OperateLogger.OperationType.R,tables = " T_RESOURCE_INFO")
    @RequestMapping("initResource")
    @ResponseBody
    public ResponseDTO<List<TreeView>> initResource(String id) {
        ResponseDTO<List<TreeView>> responseDTO = new ResponseDTO<List<TreeView>>();
        try {
            ResourceInfo temp=new ResourceInfo();
           temp.setResourceType("02");
            //获取树形
            List<ResourceInfo> resourceInfoList = resourceInfoService.getAllResourceInfoByType(temp);
            List<TreeView> treeList = resourceInfoService.sortAndAssemble(resourceInfoList);
            //加载已有权限
            RoleInfo roleInfo = roleInfoService.getRolesById(id);
            List<ResourceInfo> infoList = resourceInfoService.getResourceWithRoleId(id);
            //转map
            Map<String, TreeView> treeViewMap = new HashMap<String, TreeView>();
            for (TreeView treeView : treeList) {
                getTreeDetail(treeView, treeViewMap);
            }
            //isSelected
            for (ResourceInfo resourceInfo : infoList) {
                if (treeViewMap.get(resourceInfo.getRsrcCode()) == null) {
                    log.error("菜单树拼装异常,子:{},父:{}", resourceInfo.getRsrcCode() + resourceInfo.getRsrcName(), resourceInfo.getParentRsrcCode());
                } else {
                    treeViewMap.get(resourceInfo.getRsrcCode()).getState().put("checked", true);
                }
            }

            responseDTO.setSuccess(true);
            responseDTO.setObject(treeList);
        } catch (Exception e) {
            log.error("操作失败", e);
        }
        return responseDTO;
    }

    private void getTreeDetail(TreeView treeView, Map<String, TreeView> map) {
        map.put(treeView.getRsrcCode(), treeView);
        if (CollectionUtils.isEmpty(treeView.getNodes())) {
            return;
        } else {
            for (TreeView tree : treeView.getNodes()) {
                getTreeDetail(tree, map);
            }
        }
    }
}
