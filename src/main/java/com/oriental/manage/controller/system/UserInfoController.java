package com.oriental.manage.controller.system;

import com.oriental.manage.core.authorize.UserRealm;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.pojo.base.UserRole;
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
 * Created by lupf on 2016/4/27.
 */
@Slf4j
@Controller
@RequestMapping("system/user")
public class UserInfoController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping("init")
    public String init() {

        return "system/searchUserInfo";
    }

    @OperateLogger(content = "查询用户", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<UserInfo>> queryPage(Pagination<UserInfo> pagination, UserInfo userInfo, String roleId) {
        ResponseDTO<Pagination<UserInfo>> responseDTO = new ResponseDTO<Pagination<UserInfo>>();
        try {
            //查询条件roleId转换
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

    @RequiresPermissions("sys-user-info_toAddUser")
    @RequestMapping("toAdd")
    public String toAdd() {

        return "system/addUserInfo";
    }

    @RequiresPermissions("sys-user-info_toAddUser")
    @OperateLogger(content = "新增用户", operationType = OperateLogger.OperationType.C, tables = "T_USER_INFO")
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {

            userInfo.setUserPwd(userInfoService.generatePwd());
            userInfoService.addNewUser(userInfo, responseDTO);
            //@TODO 这里可以发送一下短信，通知账户开通成功

        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("sys-user-info_doUpdateUser")
    @RequestMapping("toUpdate")
    public String toUpdate() {

        return "system/updateUserInfo";
    }

    @RequiresPermissions("sys-user-info_doUpdateUser")
    @OperateLogger(content = "修改用户", operationType = OperateLogger.OperationType.U, tables = "T_USER_INFO")
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfoService.updateUserInfo(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("sys-user-info_doDeleteUser")
    @OperateLogger(content = "删除用户", operationType = OperateLogger.OperationType.D, tables = "T_USER_INFO")
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(UserInfo userInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            userInfoService.deleteUserInfo(userInfo, responseDTO);
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("sys-user-info_toAudit")
    @OperateLogger(content = "审核用户", operationType = OperateLogger.OperationType.U, tables = "T_USER_INFO")
    @RequestMapping("audit")
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

    @RequiresPermissions("user-info_toUserRole")
    @RequestMapping("toAllocate")
    public String toAllocateRole() {
        return "system/roleAllocate";
    }

    @RequiresPermissions("user-info_toUserRole")
    @OperateLogger(content = "角色分配", operationType = OperateLogger.OperationType.U, tables = "T_USER_ROLE")
    @RequestMapping("allocate")
    @ResponseBody
    public ResponseDTO allocate(String userId, @RequestParam(value = "roles[]") String[] roles) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            userRoleService.insertRoleWithUserId(userId, roles);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("角色分配失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("toResetPwd")
    public String toResetPwd() {
        return "system/resetPwd";
    }

    @OperateLogger(content = "管理员修改密码", operationType = OperateLogger.OperationType.U, tables = "T_USER_ROLE")
    @RequestMapping("resetPwd")
    @ResponseBody
    public ResponseDTO resetPwd(@RequestBody UserInfo userInfo) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            //生成新密码
            String newPwd = userInfoService.generatePwd();

            Date now = new Date();
            UserInfo updateModel = new UserInfo();
            updateModel.setUserId(userInfo.getUserId());
            updateModel.setUserPwd(newPwd);
            updateModel.setModifier(SessionUtils.getUserName());
            updateModel.setLastUpdTime(now);
            updateModel.setPwdLastUpd(now);
            userInfoService.updateUserInfo(updateModel, responseDTO);

            //短信发送密码
            userInfoService.sendPwd(userInfo.getUserId(), newPwd);
        } catch (Exception e) {
            log.error("修改密码失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }


    @RequestMapping("checkUserName")
    @ResponseBody
    public boolean checkUserName(String userName) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        return userInfoService.checkUserName(userInfo);
    }
}
