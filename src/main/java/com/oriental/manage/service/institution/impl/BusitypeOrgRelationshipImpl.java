package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.institution.BusitypeOrgRelationshipMapper;
import com.oriental.manage.pojo.institution.BusitypeOrgRelationship;
import com.oriental.manage.service.institution.IBusitypeOrgRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/6/24.
 */
@Service
public class BusitypeOrgRelationshipImpl implements IBusitypeOrgRelationshipService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BusitypeOrgRelationshipMapper busitypeOrgRelationshipMapper;

    @Override
    public void queryPage(Pagination<BusitypeOrgRelationship> pagination, BusitypeOrgRelationship busitypeOrgRelationship) {

        List<BusitypeOrgRelationship> list = busitypeOrgRelationshipMapper.selectAll(busitypeOrgRelationship);

        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);

        }

    }

    @Override
    public void addBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship) {
        if(busitypeOrgRelationshipMapper.insertSelective(busitypeOrgRelationship)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void updateBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship) {
        if(busitypeOrgRelationshipMapper.updateByPrimaryKeySelective(busitypeOrgRelationship)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void deleteBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship) {
        if(busitypeOrgRelationshipMapper.deleteByPrimaryKey(busitypeOrgRelationship)>0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public int check(BusitypeOrgRelationship busitypeOrgRelationship) {
        return busitypeOrgRelationshipMapper.checkConfig(busitypeOrgRelationship);
    }
}
