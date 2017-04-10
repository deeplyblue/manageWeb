package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.institution.OrganizeInfoMapper;
import com.oriental.manage.pojo.institution.OrganizeInfo;
import com.oriental.manage.service.institution.IOrganizeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 2016/5/4.
 */
@Service
public class OrganizeInfoImpl implements IOrganizeInfoService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrganizeInfoMapper organizeInfoMapper;

    @Override
    public void queryPage (Pagination<OrganizeInfo> pagination,OrganizeInfo organizeInfo){

        List<OrganizeInfo> list=organizeInfoMapper.selectByPay(organizeInfo);

//        Pagination<OrganizeInfo> pagination = null;

        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }


    }
    @Override
    public void addOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO){

        if (organizeInfoMapper.insertOrganizeInfo(organizeInfo) > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
    @Override
    public void updateOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO){
        if (organizeInfoMapper.updateOrganizeInfo(organizeInfo) > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }
   public List<OrganizeInfo> getCompnayCode(){
       OrganizeInfo organizeInfo=new OrganizeInfo();
           return organizeInfoMapper.getAll();
  }

    @Override
    public boolean check(OrganizeInfo organizeInfo, String id) {
        if(null!=id && !"".equals(id)){
            OrganizeInfo temp=new OrganizeInfo();
            temp.setOrgCode(id);
            List<OrganizeInfo> list=organizeInfoMapper.checkOrg(temp);
            if(list.get(0).getOrgName().equals(organizeInfo.getOrgName()) || list.get(0).getOrgAbbrName().equals(organizeInfo.getOrgAbbrName()))
            {
                return false;
            }else if (organizeInfoMapper.checkOrg(organizeInfo)!=null&&organizeInfoMapper.checkOrg(organizeInfo).size()>0){
                return true;
            }
        }
        if(organizeInfoMapper.checkOrg(organizeInfo)!=null&&organizeInfoMapper.checkOrg(organizeInfo).size()>0){
            return true;
        }else{
        return false;
        }
    }

    @Override
    public OrganizeInfo queryChannelType(String code) {
        OrganizeInfo organizeInfo=new OrganizeInfo();
        organizeInfo.setOrgCode(code);
        return organizeInfoMapper.queryChannelType(organizeInfo);
    }

    public void deleteOrganizeInfo(OrganizeInfo organizeInfo, ResponseDTO<String> responseDTO){
          if(organizeInfoMapper.deleteByPrimaryKey(organizeInfo)>0){
              responseDTO.setSuccess(true);
          }else{
              responseDTO.setSuccess(false);
          }

    }
}
