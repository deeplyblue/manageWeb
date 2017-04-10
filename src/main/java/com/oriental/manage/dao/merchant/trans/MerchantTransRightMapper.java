package com.oriental.manage.dao.merchant.trans;

import com.oriental.manage.pojo.institution.TransRight;

import java.util.List;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2016/5/24
 * Time: 18:56
 * Desc：商户交易权限
 */
public interface MerchantTransRightMapper {


    /**
     * 查询商户交易权限
     * @param transRight 交易权限
     * @return 商户交易权限
     */
    List<TransRight> searchMerchantTransRight(TransRight transRight);

    /**
     * 删除该商户所有权限
     * @param companyCode 商户号
     * @return 商户交易权限
     */
    int deleteTransRightByCompanyCode(String companyCode);

    /**
     * 新增商户权限
     * @param transRight 交易权限
     * @return 商户交易权限
     */
    int insertTransRight(TransRight transRight);

    /**
     * 批量新增
     */
    int insertTransRightList(List<TransRight> transRightList);

    int deleteTransRight(TransRight transRight);
}
