package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.dao.institution.BusiBankMapper;
import com.oriental.manage.pojo.institution.BusiBank;
import com.oriental.manage.service.institution.IBusiBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc: 业务资金源银行配置
 * Date: 2016/6/12
 * User: ZhouBin
 * See :
 */
@Service
public class BusiBankServiceImpl implements IBusiBankService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BusiBankMapper busiBankMapper;

    @Override
    @Transactional
    public boolean updateBusiBank(BusiBank busiBank) throws Exception {
        if (busiBank.getId() != -1) {
            busiBankMapper.deleteBusiBank(busiBank);
        }
        if ("1".equals(busiBank.getChoose())) {
            busiBankMapper.insertBusiBank(busiBank);
        }
        return true;
    }

    @Override
    public void queryPage(Pagination<BusiBank> pagination, BusiBank busiBank) throws Exception {
        List<BusiBank> list = busiBankMapper.queryPage(busiBank);
        if (list != null && list.size() > 0) {
            pagination.setRowCount(list.get(0).getRowCount());
            pagination.setList(list);
        }
    }

    @Override
    public List<BusiBank> queryBusiBank(BusiBank busiBank) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        return busiBankMapper.queryList(busiBank);
    }
}
