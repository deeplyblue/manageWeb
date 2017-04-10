package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.OrganizeInfo;

import java.util.List;

/**
 * Created by wangjun on 2016/5/4.
 */
public interface IOrganizeInfoService {


     void queryPage (Pagination<OrganizeInfo> pagination,OrganizeInfo organizeInfo);

    void addOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO);

    void updateOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO);

    void deleteOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO);

     List<OrganizeInfo> getCompnayCode();

    boolean check(OrganizeInfo organizeInfo, String id);

    OrganizeInfo queryChannelType(String code);
}
