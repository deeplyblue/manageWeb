package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.institution.OrgCertificateInfoMapper;
import com.oriental.manage.pojo.institution.OrgCertificateInfo;
import com.oriental.manage.service.institution.IOrgCertificateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangjun on 2016/10/31.
 */
@Service
public class OrgCertificateInfoImpl implements IOrgCertificateInfoService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrgCertificateInfoMapper orgCertificateInfoMapper;

    @Override
    public void queryPage(Pagination<OrgCertificateInfo> pagination, OrgCertificateInfo orgCertificateInfo) {
        List<OrgCertificateInfo> list= orgCertificateInfoMapper.queryPage(orgCertificateInfo);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void add(OrgCertificateInfo orgCertificateInfo, ResponseDTO<String> responseDTO) {

        orgCertificateInfo.setId(UUID.randomUUID().toString());
        orgCertificateInfo.setStatus("1");
        orgCertificateInfo.setOperateStatus("00");
        orgCertificateInfo.setOperator(SessionUtils.getUserName());
        orgCertificateInfo.setCreateTime(new Date());
        orgCertificateInfo.setLastUpdTime(new Date());
        if (orgCertificateInfoMapper.insert(orgCertificateInfo)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateStatus(OrgCertificateInfo orgCertificateInfo, ResponseDTO<String> responseDTO) {
        orgCertificateInfo.setOperator(SessionUtils.getUserName());
        orgCertificateInfo.setLastUpdTime(new Date());
        if (orgCertificateInfoMapper.updateByPrimaryKey(orgCertificateInfo)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
}
