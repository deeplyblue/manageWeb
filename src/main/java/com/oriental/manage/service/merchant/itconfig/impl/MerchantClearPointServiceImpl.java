package com.oriental.manage.service.merchant.itconfig.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.dao.merchant.itconfig.MerchantClearPointMapper;
import com.oriental.manage.pojo.merchant.itconfig.ClearPoint;
import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;
import com.oriental.manage.service.merchant.itconfig.IMerchantClearPointService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 14:05
 * Desc：
 */
@Slf4j
@Service
public class MerchantClearPointServiceImpl implements IMerchantClearPointService {


    @Autowired
    private MerchantClearPointMapper merchantClearPointMapper;

    @Override
    public void search(Pagination<ClearPoint> pagination, ClearPoint clearPoint) {
        List<ClearPoint> clearPointList = merchantClearPointMapper.queryClearPoint(clearPoint);
        if(clearPointList!=null && clearPointList.size() > 0){
            pagination.setRowCount(clearPointList.get(0).getRowCount());
            pagination.setList(clearPointList);
        }
    }

    @Override
    public void update(ClearPoint clearPoint, ResponseDTO<String> responseDTO) {
        int count = merchantClearPointMapper.updateClearPoint(clearPoint);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void add(ClearPoint clearPoint, ResponseDTO<String> responseDTO) {
        clearPoint.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
        int count = merchantClearPointMapper.addClearPoint(clearPoint);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void delete(ClearPoint clearPoint, ResponseDTO<String> responseDTO) {
        int count = merchantClearPointMapper.deleteClearPoint(clearPoint);
        if (count > 0){
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public List<ClearPoint> getAllClearPoint(){
        ClearPoint clearPoint = new ClearPoint();
        return merchantClearPointMapper.queryClearPoint(clearPoint);
    }

    @Override
    public boolean check(ClearPoint clearPoint ,String id) {
        if(null!=id && !"".equals(id)){
            ClearPoint clearPoint1=new ClearPoint();
            clearPoint1.setId(id);
            ClearPoint temp=merchantClearPointMapper.queryClearPoint(clearPoint1).get(0);
            if(temp.getItemKey().equals(clearPoint.getItemKey()) && temp.getItemValue().equals(clearPoint.getItemValue())){
                return  true;
            } else{
         if(merchantClearPointMapper.queryClearPoint(clearPoint)!=null &&merchantClearPointMapper.queryClearPoint(clearPoint).size()>0){
                    return false;
                }
            }

        }else {
        if(merchantClearPointMapper.queryClearPoint(clearPoint)!=null &&merchantClearPointMapper.queryClearPoint(clearPoint).size()>0){
            return false;
        }
        return true;
    }return true;
    }
}
