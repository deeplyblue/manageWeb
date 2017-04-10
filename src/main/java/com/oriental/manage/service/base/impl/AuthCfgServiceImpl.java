package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.authorize.UserRealm;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.base.AuthCfgMapper;
import com.oriental.manage.pojo.base.AuthCfg;
import com.oriental.manage.pojo.base.UserRole;
import com.oriental.manage.service.base.IAuthCfgService;
import com.oriental.manage.service.base.IUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lupf on 2016/4/22.
 */
@Service
public class AuthCfgServiceImpl implements IAuthCfgService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AuthCfgMapper authCfgMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private UserRealm leopardRealm;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void allocateResource(AuthCfg authCfg, String[] ids, ResponseDTO<String> responseDTO) {

        if (authCfgMapper.checkAuth(authCfg.getRoleId()) != null && authCfgMapper.checkAuth(authCfg.getRoleId()).size() > 0) {
            authCfgMapper.deleteByPrimaryRole(authCfg.getRoleId());
        }
        Date date = new Date();
        authCfg.setCreateTime(date);
        authCfg.setLastUpdTime(date);
        for (String rscCode : ids) {
            authCfg.setId(UUID.randomUUID().toString());
            authCfg.setRsrcCode(rscCode);
            authCfgMapper.insert(authCfg);
        }
        responseDTO.setSuccess(true);

        /**
         * 刷新拥有该角色的用户权限
         */
        List<UserRole> userInfos = userRoleService.getUserByRole(authCfg.getRoleId());
        for (UserRole userRole : userInfos) {
            if (StringUtils.isNotBlank(userRole.getUserName())) {

                leopardRealm.doClearCache(userRole.getUserName());
            }
        }
    }

    @Override
    public List<AuthCfg> checkAuthCfg(String id) {
        return authCfgMapper.checkAuth(id);
    }

}
