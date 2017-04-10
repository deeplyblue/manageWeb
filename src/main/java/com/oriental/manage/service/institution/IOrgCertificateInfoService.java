package com.oriental.manage.service.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.institution.OrgCertificateInfo;

/**
 * Created by wangjun on 2016/10/31.
 * 机构证书信息实现类
 */
public interface IOrgCertificateInfoService {

    /**
     * 查询机构证书信息
     */
    void queryPage(Pagination<OrgCertificateInfo> pagination, OrgCertificateInfo orgCertificateInfo);

    /**
     *新增修改机构证书信息
     */
    void add(OrgCertificateInfo orgCertificateInfo, ResponseDTO<String> responseDTO) ;

    /**
     * 修改证书状态
     * 00:初始状态 01:申请启用 02:确认启用（改变证书状态） 03:申请关闭 04:确认关闭
     */
    void updateStatus(OrgCertificateInfo orgCertificateInfo, ResponseDTO<String> responseDTO);
}
