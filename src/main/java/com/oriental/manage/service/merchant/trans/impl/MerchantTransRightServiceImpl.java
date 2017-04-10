package com.oriental.manage.service.merchant.trans.impl;

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.DataDictMapper;
import com.oriental.manage.dao.merchant.trans.MerchantTransRightMapper;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.pojo.institution.TransRight;
import com.oriental.manage.service.merchant.trans.IMerchantTransRightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: Yang xp
 * Date: 2016/5/25
 * Time: 10:52
 * Desc：商户交易权限
 */
@Slf4j
@Service
public class MerchantTransRightServiceImpl implements IMerchantTransRightService {

    @Value("#{cfgProperties['REVERSE_TRANS_CODE_0']}")
    private String reverseTransCode;

    @Autowired
    private MerchantTransRightMapper merchantTransRightMapper;

    @Autowired
    private DataDictMapper dataDictMapper;

    @Override
    public void searchMerchantTransRight(Pagination<TransRight> pagination, TransRight transRight) {
        List<TransRight> merchantTransRightList = merchantTransRightMapper.searchMerchantTransRight(transRight);
        if (merchantTransRightList != null && merchantTransRightList.size() > 0) {
            pagination.setRowCount(merchantTransRightList.get(0).getRowCount());
            TransRight transRightTemp = merchantTransRightList.get(0);
            int rowSpan = 0;
            for (TransRight item : merchantTransRightList) {
                if (item.getCompanyCode().equals(transRightTemp.getCompanyCode())) {
                    rowSpan++;
                } else {
                    transRightTemp.setRowSpan(rowSpan);
                    transRightTemp = item;
                    rowSpan = 1;
                }
            }
            transRightTemp.setRowSpan(rowSpan);
            pagination.setList(merchantTransRightList);
        }
    }


    @Override
    public List<Map<String, String>> initResource(String merchantCode,String companyType) {
        DataDict dataDict = new DataDict();
        dataDict.setColName(CacheKey.CONN_CHANNEL.getColName());
        List<DataDict> channelList = dataDictMapper.selectByPay(dataDict);
        dataDict.setColName(CacheKey.TRANS_CODE_ALL.getColName());
        List<DataDict> transCodeList = dataDictMapper.selectByPay(dataDict);
        List<Map<String, String>> resource = new ArrayList<Map<String, String>>();
        Map<String, String> existTrans = existTransRight(merchantCode,companyType);
        Map<String, String> item;
        Map<String, String> channelItem;
        for (DataDict channel : channelList) {
            channelItem = new HashMap<String, String>();
            channelItem.put("id", channel.getItemVal());
            channelItem.put("pid", "0");
            channelItem.put("name", channel.getItemDesc());
            channelItem.put("open", "false");
            for (DataDict trans : transCodeList) {
                item = new HashMap<String, String>();
                item.put("id", trans.getItemVal());
                item.put("pid", channel.getItemVal());
                item.put("name", trans.getItemDesc());
                String refund;
                if (null != (refund = existTrans.get(
                        channel.getItemVal().concat("-").concat(trans.getItemVal())))) {
                    item.put("checked", "true");
                    item.put("refund", refund);
                }
                resource.add(item);
            }
            resource.add(channelItem);
        }
        return resource;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void updateMerchantTransRight(List<TransRight> transRightList) {
        //首先删除该商户的所有权限，然后再插入勾选中的权限
        if (transRightList.size() > 0) {
            merchantTransRightMapper.deleteTransRightByCompanyCode(transRightList.get(0).getCompanyCode());
            for (Iterator<TransRight> transRight = transRightList.iterator(); transRight.hasNext(); ) {
                TransRight tr = transRight.next();
                buildTransRight(tr);
                merchantTransRightMapper.insertTransRight(tr);
            }
        }
    }

    private void buildTransRight(TransRight transRight) {
        transRight.setId(UUID.randomUUID().toString());
        transRight.setRightStatus("1");
        if(reverseTransCode.contains(transRight.getTransCode())){
            transRight.setReverseFlag("0");
        }else{
            transRight.setReverseFlag("1");
        }
        transRight.setCreator(SessionUtils.getUserName());
        transRight.setModifyUser(SessionUtils.getUserName());
        transRight.setRefundFlag("0");
        transRight.setSplitRightFlag("1");
    }

    @Override
    public void addMerchantTransRight(List<TransRight> transRightList, ResponseDTO<String> responseDTO) {
        for (TransRight transRight : transRightList){
            buildTransRight(transRight);
        }
        int count = merchantTransRightMapper.insertTransRightList(transRightList);
         responseDTO.setSuccess(count>0);
    }

    @Override
    public void deleteMerchantTransRight(TransRight transRight, ResponseDTO<String> responseDTO) {
        if(merchantTransRightMapper.deleteTransRight(transRight)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }

    private Map<String, String> existTransRight(String merchantCode,String companyType) {
        TransRight transRight = new TransRight();
        Map<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(merchantCode)){
            transRight.setCompanyCode(merchantCode);
            transRight.setCompanyType(companyType);
            transRight.setPageSize(500);
            List<TransRight> transRightList = merchantTransRightMapper.searchMerchantTransRight(transRight);
            for (TransRight item : transRightList) {
                resultMap.put(item.getConnChannel().concat("-").concat(item.getTransCode()), item.getRefundFlag());
            }
        }

        return resultMap;
    }
}
