package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.OrgBusi;

/**
 * Created by wangjun on 2016/6/7.
 * 业务机构基本信息表
 *
 */
public interface IOrgBusiService {

    void queryPage(Pagination<OrgBusi> pagination, OrgBusi orgBusi);

    void addOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi);

    void updateOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi);

    void deleteOrgBusi(ResponseDTO<String> responseDTO, OrgBusi orgBusi);
}
