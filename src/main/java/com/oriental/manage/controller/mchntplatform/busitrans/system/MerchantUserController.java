package com.oriental.manage.controller.mchntplatform.busitrans.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.RoleInfo;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.pojo.base.UserRole;
import com.oriental.manage.service.base.IRoleInfoService;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.manage.service.base.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 2016/7/7.
 * 商户测用户查询
 *
 */

@Slf4j
@Controller
@RequestMapping("mchntplatform/merchantUser")
public class MerchantUserController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleInfoService roleInfoService;

    @RequestMapping("init")
    @RequiresPermissions("merchantUser_search")
    public String init(){
        return "mchntplatform/system/searchMerchantUser";
    }
    @RequestMapping("toAdd")
    @RequiresPermissions("merchantUser_add")
    public String toAdd(){ return  "mchntplatform/system/addMerchantUser";}
    @RequestMapping("toUpdate")
    @RequiresPermissions("merchantUser_update")
    public String toUpdate(){ return  "mchntplatform/system/updateMerchantUser";}
    @RequestMapping("toAllocate")
    @RequiresPermissions("merchantUser_roleAllocate")
    public String toAllocateRole() {return "mchntplatform/system/updateMerchantRoleAllocate";}

    @OperateLogger(content = "查询商户测用户",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("merchantUser_search")
    @ResponseBody
    public ResponseDTO<Pagination<UserInfo>> queryPage(Pagination<UserInfo> pagination, UserInfo userInfo, String roleId) {
        ResponseDTO<Pagination<UserInfo>> responseDTO = new ResponseDTO<Pagination<UserInfo>>();
        try {
              //查询
              userInfo.setCompanyCode(SessionUtils.getMchntCode());
              userInfo.setUserType("02");
            if (StringUtils.isNotBlank(roleId)) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                List userRoleList = new ArrayList();
                userRoleList.add(userRole);
                userInfo.setUserRoles(userRoleList);
             }
            userInfoService.queryPage(pagination, userInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "新增商户用户",operationType = OperateLogger.OperationType.C,tables = "T_USER_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("add")
    @RequiresPermissions("merchantUser_add")
    @ResponseBody
    public ResponseDTO<String> add(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfo.setCompanyCode(SessionUtils.getMchntCode());
            userInfo.setUserType("02");//添加商户代码
            userInfo.setCreator(SessionUtils.getUserName());//创建者
            userInfoService.addNewUser(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "修改商户用户",operationType = OperateLogger.OperationType.U,tables = "T_USER_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("update")
    @RequiresPermissions("merchantUser_update")
    @ResponseBody
    public ResponseDTO<String> update(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfoService.updateUserInfo(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "删除商户用户",operationType = OperateLogger.OperationType.D,tables = "T_USER_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("delete")
    @RequiresPermissions("merchantUser_delete")
    @ResponseBody
    public ResponseDTO<String> delete(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfoService.deleteUserInfo(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "审核商户用户",operationType = OperateLogger.OperationType.U,tables = "T_USER_INFO",noticeSystem = OperateLogger.NoticeSystem.ALL)
    @RequestMapping("audit")
    @RequiresPermissions("merchantUser_audit")
    @ResponseBody
    public ResponseDTO<String> audit(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfoService.auditUserInfo(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "商户角色分配",operationType = OperateLogger.OperationType.U,tables = "T_USER_ROLE")
    @RequestMapping("allocate")
    @RequiresPermissions("merchantUser_roleAllocate")
    @ResponseBody
    public ResponseDTO allocate(String userId, @RequestParam(value = "roles[]") String[] roles) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ResponseDTO check= this.cheackMerchantCde(userId,roles);
            if(check.isSuccess()){
            userRoleService.insertRoleWithUserId(userId, roles);
            responseDTO.setSuccess(true);
            }else{
                responseDTO.setMsg(check.getMsg());
                responseDTO.setSuccess(check.isSuccess());
            }

        } catch (Exception e) {
            log.error("角色分配失败",e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    public ResponseDTO deleteRole(UserInfo userInfo) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            userRoleService.deleteRoleWithUserID(userInfo.getUserId());
            responseDTO.setSuccess(true);
            responseDTO.setMsg("已清除改用户所有角色");

        } catch (Exception e) {
            log.error("角色分配失败",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("清除失败");
        }
        return responseDTO;
    }

    public ResponseDTO cheackMerchantCde(String userId,String[] roles){
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfo userInfo=userInfoService.getUserById(userId);
        if(roles!=null && roles.length>0){
            for (String roleId : roles) {
                RoleInfo roleInfo = roleInfoService.getRolesById(roleId);
                if(userInfo.getCompanyCode().equals(roleInfo.getAreaCode())){
                    responseDTO.setSuccess(true);
                }else{
                    responseDTO.setMsg("此用户没有分配该角色的权限");
                    responseDTO.setSuccess(false);
                    return  responseDTO;
                }
            }
        }

        return responseDTO;
    }
    @RequestMapping("toResetPwd")
    @RequiresPermissions("merchantUser_toResetUserPwd")
    public String toResetPwd() {
        return "system/resetPwd";
    }


    @RequestMapping("resetPwd")
    @ResponseBody
    @RequiresPermissions("merchantUser_toResetUserPwd")
    public ResponseDTO resetPwd(@RequestBody UserInfo userInfo) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            Date now = new Date();
            UserInfo updateModel = new UserInfo();
            updateModel.setUserId(userInfo.getUserId());
            updateModel.setUserPwd(userInfo.getUserPwd());
            updateModel.setLastUpdTime(now);
            updateModel.setModifier(SessionUtils.getUserName());
            updateModel.setPwdLastUpd(now);
            userInfoService.updateUserInfo(updateModel, responseDTO);
        } catch (Exception e) {
            log.error("修改密码失败",e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }
}
