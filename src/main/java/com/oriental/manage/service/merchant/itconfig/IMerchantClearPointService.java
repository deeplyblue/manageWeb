package com.oriental.manage.service.merchant.itconfig;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.merchant.itconfig.ClearPoint;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 13:55
 * Desc：商户结算配置
 */
public interface IMerchantClearPointService {

    void search(Pagination<ClearPoint> pagination, ClearPoint clearPoint);

    void update(ClearPoint clearPoint, ResponseDTO<String> responseDTO);


    void add(ClearPoint clearPoint, ResponseDTO<String> responseDTO);

    void delete(ClearPoint clearPoint,ResponseDTO<String> responseDTO);

    List<ClearPoint> getAllClearPoint();

    boolean check(ClearPoint clearPoint,String id);
}
