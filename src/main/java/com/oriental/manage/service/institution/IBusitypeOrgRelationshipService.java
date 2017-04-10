package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.BusitypeOrgRelationship;

/**
 * Created by wangjun on 2016/6/24.
 */
public interface IBusitypeOrgRelationshipService {

    void queryPage(Pagination<BusitypeOrgRelationship> pagination, BusitypeOrgRelationship busitypeOrgRelationship);

    void addBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship);

    void updateBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship);

    void deleteBusiTypeOrgRelationship(ResponseDTO<String> responseDTO, BusitypeOrgRelationship busitypeOrgRelationship);

    int check(BusitypeOrgRelationship busitypeOrgRelationship);
}
