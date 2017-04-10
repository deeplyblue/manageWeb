package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.OrgBusi;

import java.util.List;

/**
 * Created by wangjun on 2016/6/2.
 * 业务机构基本信息
 */
public interface OrgBusiMapper {

    int insertOrgBusi(OrgBusi orgBusi);

    int updateOrgBusi(OrgBusi orgBusi);

    int deleteById(OrgBusi orgBusi);

    List<OrgBusi> getPage(OrgBusi orgBusi);


}
