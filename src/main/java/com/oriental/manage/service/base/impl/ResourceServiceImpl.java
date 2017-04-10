package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.dao.base.ResourceInfoMapper;
import com.oriental.manage.pojo.base.ResourceInfo;
import com.oriental.manage.pojo.base.TreeView;
import com.oriental.manage.service.base.IResourceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lupf on 2016/4/21.
 */
@Slf4j
@Service
public class ResourceServiceImpl implements IResourceInfoService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ResourceInfoMapper resourceInfoMapper;

    @Override
    public List<ResourceInfo> getResourceWithUserId(String userId) {
        return resourceInfoMapper.selectUserResource(userId);
    }

    public List<ResourceInfo> getAllResourceInfo() {
        return resourceInfoMapper.selectBySelective(new ResourceInfo());
    }

    @Override
    public List<ResourceInfo> getAllResourceInfoByType(ResourceInfo resourceInfo) {
        return resourceInfoMapper.selectBySelective(resourceInfo);
    }

    @Override
    public List<TreeView> sortAndAssemble(List<ResourceInfo> resourceInfoList) {

        Collections.sort(resourceInfoList, new Comparator<ResourceInfo>() {
            @Override
            public int compare(ResourceInfo o1, ResourceInfo o2) {
                int sort1 = BigDecimalUtils.isCompareTo(o1.getParentRsrcCode(), o2.getParentRsrcCode());
                return sort1 != 0 ? sort1 : BigDecimalUtils.isCompareTo(o1.getRsrcDspOrder(), o2.getRsrcDspOrder());
            }
        });

        List<TreeView> treeViewList = new ArrayList<TreeView>();
        HashMap<String, TreeView> treeViewHashMap = new HashMap<String, TreeView>();

        for (ResourceInfo info : resourceInfoList) {
            TreeView treeView = new TreeView();
            treeView.setText(info.getRsrcName());
            treeView.setDesc(info.getRsrcDesc());
            treeView.setOperation(info.getRsrcOperation());
            treeView.setRsrcType(info.getRsrcType());
            treeView.setHref(info.getRsrcUrl());
            treeView.setResourceType(info.getResourceType());
            treeView.setRsrcCode(info.getRsrcCode());
            treeView.setParentRsrcCode(info.getParentRsrcCode());
            treeView.setRsrcDspOrder(info.getRsrcDspOrder());

            treeViewHashMap.put(info.getRsrcCode(), treeView);
        }

        for (ResourceInfo info : resourceInfoList) {
            if (StringUtils.equals(info.getParentRsrcCode(), "0")) {
                treeViewList.add(treeViewHashMap.get(info.getRsrcCode()));
            } else {
                TreeView tempParent = treeViewHashMap.get(info.getParentRsrcCode());
                if (tempParent == null) {
                    log.error("菜单树拼装异常,子:{},父:{}", info.getRsrcCode() + info.getRsrcName(), info.getParentRsrcCode());
                } else if (tempParent.getNodes() == null) {
                    List<TreeView> childs = new ArrayList<TreeView>();
                    childs.add(treeViewHashMap.get(info.getRsrcCode()));
                    tempParent.setNodes(childs);
                } else {
                    tempParent.getNodes().add(treeViewHashMap.get(info.getRsrcCode()));
                }
            }
        }
        return treeViewList;
    }

    @Override
    public void addResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO) {
        Date now = new Date();
        resourceInfo.setRsrcCode(DateFormatUtils.format(new Date(), DateUtils.DATETIMESFORMAT));
        resourceInfo.setRsrcStatus("A");
        resourceInfo.setRsrcVisableFlag("1");
        resourceInfo.setCreateTime(now);
        resourceInfo.setLastUpdTime(now);

        if (resourceInfoMapper.insertSelective(resourceInfo) > 0) {
            responseDTO.setMsg("新增成功");
        } else {
            responseDTO.setMsg("新增失败");
        }
    }

    @Override
    public void updateResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO) {
        resourceInfo.setLastUpdTime(new Date());

        if (resourceInfoMapper.updateByPrimaryKeySelective(resourceInfo) > 0) {
            responseDTO.setMsg("修改成功");
        } else {
            responseDTO.setMsg("修改失败");
        }
    }

    @Override
    public void deleteResourceInfo(ResourceInfo resourceInfo, ResponseDTO responseDTO) {
        resourceInfo.setLastUpdTime(new Date());

        if (resourceInfoMapper.deleteByPrimaryKey(resourceInfo.getRsrcCode()) > 0) {
            responseDTO.setMsg("删除成功");
        } else {
            responseDTO.setMsg("删除失败");
        }
    }

    @Override
    public List<ResourceInfo> getResourceWithRoleId(String id) {
        return resourceInfoMapper.queryByRoleId(id);
    }
}
