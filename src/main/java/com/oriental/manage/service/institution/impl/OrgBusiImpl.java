package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.institution.OrgBusiMapper;
import com.oriental.manage.pojo.institution.OrgBusi;
import com.oriental.manage.service.institution.IOrgBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/6/2.
 */
@Service
public class OrgBusiImpl implements IOrgBusiService {

    @Autowired
    private OrgBusiMapper orgBusiMapper;

    @Override
    public void queryPage(Pagination<OrgBusi> pagination, OrgBusi orgBusi) {
        List<OrgBusi> list= orgBusiMapper.getPage(orgBusi);
        if(list!=null && list.size()>0){
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    @Override
    public void addOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi) {
        if (orgBusiMapper.insertOrgBusi(orgBusi)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }

    @Override
    public void updateOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi) {
        if(orgBusiMapper.updateOrgBusi(orgBusi)>0){
            responseDTO.setSuccess(true);
        }responseDTO.setSuccess(false);
    }

    @Override
    public void deleteOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi) {
        if(orgBusiMapper.deleteById(orgBusi)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }
    }
}
