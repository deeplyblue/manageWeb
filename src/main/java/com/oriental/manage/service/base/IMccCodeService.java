package com.oriental.manage.service.base;

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.dao.base.DataDictMapper;
import com.oriental.manage.pojo.base.DataDict;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yutao on 2016/10/26.
 */
@Slf4j
@Service
public class IMccCodeService {
    @Autowired
    private DataDictMapper dataDictMapper;

    public List selectAllMccCode() {
        DataDict dataDict = new DataDict();
        dataDict.setItemName(CacheKey.MCC_CODE1.getItemName());
        dataDict.setColName(CacheKey.MCC_CODE1.getColName());
//        dataDict.setItemType("1");
        //一级列表
        List<DataDict> dataDicts1 = dataDictMapper.selectByPay(dataDict);
        List<Map<String, Object>> olist = new ArrayList();
        if (dataDicts1 != null && dataDicts1.size() > 0) {
            for (DataDict data1 : dataDicts1) {
                Map<String, Object> oMap = new HashMap<>();
                oMap.put("oValue", data1.getItemVal());
                oMap.put("des", data1.getItemDesc());
                olist.add(oMap);
            }
        }

        //二级列表
//        dataDict.setItemType("2");
        dataDict.setItemName(CacheKey.MCC_CODE2.getItemName());
        dataDict.setColName(CacheKey.MCC_CODE2.getColName());
        List<DataDict> dataDicts2 = dataDictMapper.selectByPay(dataDict);
        if (dataDicts2 != null && dataDicts2.size() > 0) {
            for (Map<String, Object> map : olist) {
                List tlist = new ArrayList();
                for (DataDict data2 : dataDicts2) {
                    Map<String, Object> tMap = new HashMap<>();
                    if (StringUtils.equals(data2.getItemType(), (String) map.get("oValue"))) {
                        tMap.put("tValue", data2.getItemVal());
                        tMap.put("des", data2.getItemDesc());
                        tlist.add(tMap);
                    }
                    map.put("tList", tlist);
                }

            }
            //三级列表
//            dataDict.setItemType("3");
            dataDict.setItemName(CacheKey.MCC_CODE3.getItemName());
            dataDict.setColName(CacheKey.MCC_CODE3.getColName());
            List<DataDict> dataDicts3 = dataDictMapper.selectByPay(dataDict);
            if (dataDicts3 != null && dataDicts3.size() > 0) {
                for (Map<String, Object> map : olist) {
                    for (Object tMap : (List) map.get("tList")) {
                        Map tMap1 = (Map) tMap;
                        List trlist = new ArrayList();

                        for (DataDict data3 : dataDicts3) {
                            Map<String, Object> trMap = new HashMap<>();
                            if (StringUtils.equals(data3.getItemType(), (String) tMap1.get("tValue"))) {
                                trMap.put("trValue", data3.getItemVal());
                                trMap.put("des", data3.getItemDesc());
                                trlist.add(trMap);
                            }
                        }
                        tMap1.put("trList", trlist);
                    }

                }
            }
        }

        return olist;
    }
}
