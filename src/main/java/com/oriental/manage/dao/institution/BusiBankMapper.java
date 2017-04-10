package com.oriental.manage.dao.institution;

import com.oriental.manage.pojo.institution.BusiBank;
import com.oriental.manage.pojo.institution.OrgBank;

import java.util.List;
import java.util.Map;

/**
 * Desc: 业务资金源Mapper
 * Date: 2016/6/12
 * User: ZhouBin
 * See :
 */
public interface BusiBankMapper {


    List<BusiBank> queryPage(BusiBank busiBank);


    List<BusiBank> queryList(BusiBank busiBank);

    int deleteBusiBank(BusiBank busiBank);

    int insertBusiBank(BusiBank busiBank);





}
