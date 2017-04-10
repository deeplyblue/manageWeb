package com.oriental.manage.controller.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.base.*;
import com.oriental.manage.service.base.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by lupf on 2016/5/16.
 */
@Slf4j
@Controller
@RequestMapping("system/role")
public class RoleInfoController {

    @Autowired
    private IRoleInfoService roleInfoService;
    @Autowired
    private IResourceInfoService resourceInfoService;
    @Autowired
    private IAuthCfgService authCfgService;
    @Autowired
    private IJobResponsibilityService jobResponsibilityService;
    @Autowired
    private IRoleJobResponsibilityService roleJobResponsibilityService;

    @RequestMapping("init")
    public String init() {return "system/searchRoleInfo";}

    @RequestMapping("toJobAllocate")
    public String toJobAllocate() {return "system/roleJobResponsibilityAllcate";}

    @OperateLogger(content = "查询角色",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<RoleInfo>> queryPage(Pagination<RoleInfo> pagination, RoleInfo roleInfo) {
        ResponseDTO<Pagination<RoleInfo>> responseDTO = new ResponseDTO<Pagination<RoleInfo>>();
        try {
            roleInfoService.queryPage(pagination, roleInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequiresPermissions("sys-role_toRolesResources")
    @RequestMapping("toAllocate")
    public String toAllocate() {

        return "system/resourceAllocate";
    }

    @RequiresPermissions("sys-role_toRolesResources")
    @OperateLogger(content = "角色权限分配",operationType = OperateLogger.OperationType.U,tables = "T_AUTH_CFG")
    @RequestMapping("allocate")
    @ResponseBody
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

    @OperateLogger(content = "初始化角色权限",operationType = OperateLogger.OperationType.R)
    @RequestMapping("initResource")
    @ResponseBody
    public ResponseDTO<List<TreeView>> initResource(String id) {
        ResponseDTO<List<TreeView>> responseDTO = new ResponseDTO<List<TreeView>>();
        try {
            //获取树形
            List<ResourceInfo> resourceInfoList = resourceInfoService.getAllResourceInfo();
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


    @OperateLogger(content = "初始化岗位职责",operationType = OperateLogger.OperationType.R)
    @RequestMapping("initJobResponsibility")
    @ResponseBody
    public ResponseDTO<List<JobResponsibility>> initJobResponsibility(String roleId) {
        ResponseDTO<List<JobResponsibility>> responseDTO = new ResponseDTO<>();
        Pagination<JobResponsibility> pagination = new Pagination<>();
        try {
            //获取树形
            pagination.setPageNum(1);
            pagination.setPageSize(10000);
            jobResponsibilityService.queryPage(pagination,new JobResponsibility());
            RoleJobResponsibility roleJobResponsibility = new RoleJobResponsibility();
            roleJobResponsibility.setRoleId(roleId);
            List<JobResponsibility> jobResponsibilityList = pagination.getList();
            Set<String> jobId = new HashSet<String>();
            List<RoleJobResponsibility> roleJobResponsibilityList=roleJobResponsibilityService.queryChecked(roleJobResponsibility);
            if(roleJobResponsibilityList!=null){
                for (RoleJobResponsibility r:
                        roleJobResponsibilityList) {
                   jobId.add(r.getJobId()) ;
                }
            }
            for (JobResponsibility j:
                 jobResponsibilityList) {
                if(jobId.contains(j.getId())){
                    j.setAllocated(true);
                }
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination.getList());
        } catch (Exception e) {
            log.error("操作失败", e);
        }
        return responseDTO;
    }

    @OperateLogger(content = "岗位职责分配",operationType = OperateLogger.OperationType.U,tables = "T_AUTH_CFG")
    @RequestMapping("jobAllocate")
    @ResponseBody
    public ResponseDTO<String> jobAllocate(String id, @RequestParam(value = "ids[]") String[] ids) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            RoleJobResponsibility roleJobResponsibility = new RoleJobResponsibility();
            roleJobResponsibility.setRoleId(id);
            roleJobResponsibilityService.allocateResource(roleJobResponsibility,ids,responseDTO);
//            authCfgService.allocateResource(authCfg, ids, responseDTO);
        } catch (Exception e) {
            responseDTO.setSuccess(false);
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

    @RequestMapping("toAdd")
    public String toAdd() {

        return "system/editableRoleInfo";
    }
    @OperateLogger(content = "新增角色",operationType = OperateLogger.OperationType.C,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(RoleInfo roleInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            roleInfoService.insert(roleInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toUpdate")
    public String toUpdate() {

        return "system/editableRoleInfo";
    }
    @OperateLogger(content = "修改角色",operationType = OperateLogger.OperationType.U,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("update")
    @ResponseBody
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
    @OperateLogger(content = "删除角色",operationType = OperateLogger.OperationType.D,tables = "T_ROLE_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("delete")
    @ResponseBody
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

    @RequestMapping("checkRoleName")
    @ResponseBody
    public boolean checkRoleName(String roleName,String roleId) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName(roleName);
        if(roleId!=null){
        roleInfo.setRoleId(roleId);
        }
        return roleInfoService.checkRoleNam(roleInfo);
    }

}
