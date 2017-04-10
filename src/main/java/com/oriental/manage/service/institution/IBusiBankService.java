package com.oriental.manage.service.institution;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.pojo.institution.BusiBank;
import com.oriental.manage.pojo.institution.OrgBank;

import java.util.List;

/**
 * 业务类型实现类
 * @author haojunting
 */
public interface IBusiBankService{

    /**
     * 更新方式 ，有的先删除掉，没有的插入
     * @return
     * @throws Exception
     */
    public boolean updateBusiBank(BusiBank busiBank) throws Exception;

    /**
     * 分页查询
     *
     * @param pagination
     * @param busiBank
     * @return
     * @throws Exception
     */
    void queryPage(Pagination<BusiBank> pagination,BusiBank busiBank) throws Exception;


    /**
     * 查询业务银行列表
     * @return
     * @throws Exception
     */
    public List<BusiBank> queryBusiBank(BusiBank busiBank) throws Exception;

}
