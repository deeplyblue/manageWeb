package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.RoleInfoMapper;
import com.oriental.manage.pojo.base.RoleInfo;
import com.oriental.manage.service.base.IRoleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lupf on 2016/4/26.
 */
@Service
@Slf4j
public class RoleInfoServiceImpl implements IRoleInfoService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public List<RoleInfo> getRolesByUserId(String userId) {
        List<RoleInfo> roleInfoList = roleInfoMapper.getRolesByUserId(userId);
        return roleInfoList;
    }

    @Override
    public List<RoleInfo> getAllRole() {
        RoleInfo roleInfo = new RoleInfo();
        return roleInfoMapper.selectBySelective(roleInfo);
    }

    @Override
    public void queryPage(Pagination<RoleInfo> pagination, RoleInfo roleInfo) {
        List<RoleInfo> list = roleInfoMapper.queryPage(roleInfo);
        if (!CollectionUtils.isEmpty(list)) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void insert(RoleInfo roleInfo, ResponseDTO<String> responseDTO) {
        roleInfo.setRoleId(UUID.randomUUID().toString());
        Date date = new Date();
        roleInfo.setCreateTime(date);
        roleInfo.setLastUpdTime(date);
        roleInfo.setCreator(SessionUtils.getUserName());

        //先写死
        if("02".equals(roleInfo.getRoleType())){
            roleInfo.setAreaCode(SessionUtils.getMchntCode());
        }else{
        roleInfo.setAreaCode("3101");}
        roleInfo.setReadonlyFlag("0");

        roleInfoMapper.insertSelective(roleInfo);

        responseDTO.setSuccess(true);
    }

    @Override
    public void update(RoleInfo roleInfo, ResponseDTO<String> responseDTO) {

        RoleInfo info = new RoleInfo();
        info.setRoleId(roleInfo.getRoleId());
        info.setRoleName(roleInfo.getRoleName());
        info.setRoleDesc(roleInfo.getRoleDesc());
        info.setRoleType(roleInfo.getRoleType());
        roleInfoMapper.updateByPrimaryKeySelective(info);

        responseDTO.setSuccess(true);
    }

    @Override
    public void delete(RoleInfo roleInfo, ResponseDTO<String> responseDTO) {

        roleInfoMapper.deleteByPrimaryKey(roleInfo.getRoleId());

        responseDTO.setSuccess(true);
    }

    @Override
    public RoleInfo getRolesById(String id) {
        return roleInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RoleInfo> getRoleByType(String type) {
        RoleInfo info = new RoleInfo();
        info.setAreaCode(SessionUtils.getMchntCode());
        info.setRoleType(type);
        return roleInfoMapper.selectByType(info);
    }

    @Override
    public boolean checkRoleNam(RoleInfo roleInfo) {
        if(roleInfo.getRoleId()!=null){
            RoleInfo temp=roleInfoMapper.selectByPrimaryKey( roleInfo.getRoleId());
            if(temp.getRoleName().equals(roleInfo.getRoleName()) && temp.getRoleId().equals(roleInfo.getRoleId())){
                return false;
            }else{
                List<RoleInfo> list=roleInfoMapper.selectBySelective(roleInfo);
                if(list!=null && list.size()>0){
                    if(list.get(0).getRoleName().equals(roleInfo.getRoleName())){
                        return true;
                    }
                }else{
                    return false;
                }
            }
        }else{
        if(roleInfoMapper.selectBySelective(roleInfo)!=null &&roleInfoMapper.selectBySelective(roleInfo).size()>0){
            return true;
        }
        }
        return false;
    }
}
