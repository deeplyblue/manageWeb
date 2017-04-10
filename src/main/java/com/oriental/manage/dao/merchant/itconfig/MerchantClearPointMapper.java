package com.oriental.manage.dao.merchant.itconfig;

import com.oriental.manage.pojo.merchant.itconfig.ClearPoint;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/31
 * Time: 14:06
 * Desc：
 */
public interface MerchantClearPointMapper {

    List<ClearPoint> queryClearPoint(ClearPoint clearPoint);

    int addClearPoint(ClearPoint clearPoint);

    int updateClearPoint(ClearPoint clearPoint);

    int deleteClearPoint(ClearPoint clearPoint);

    ClearPoint check(ClearPoint clearPoint);
}
