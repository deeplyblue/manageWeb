package com.oriental.manage.service.base.impl;

import com.google.gson.Gson;
import com.oriental.manage.dao.base.GovAreaMapper;
import com.oriental.manage.service.base.IGovAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: Yang xp
 * Date: 2016/5/20
 * Time: 11:16
 * Desc：
 */
@Service
@Slf4j
public class GovAreaServiceImpl implements IGovAreaService {

    @Autowired
    private GovAreaMapper govAreaMapper;

    @Override
    public Set<Map<String,Object>> selectAllArea() {
        Set<Map<String,Object>> result = new HashSet<Map<String, Object>>();
        List<Map<String,String>> areaMap = govAreaMapper.selectAllArea();
        Map<String,List<Map<String,String>>> cityList = new HashMap<String, List<Map<String, String>>>();
        Map<String,String> cityItem ;
        Map<String,Object> provinceItem ;

        for (Map<String,String> item : areaMap){
            provinceItem = new HashMap<String, Object>();
            provinceItem.put("areaCode",item.get("AREA_CODE"));
            provinceItem.put("areaName",item.get("AREA_NAME"));
            cityItem = new HashMap<String, String>();
            cityItem.put("cityCode",item.get("CITY_CODE"));
            cityItem.put("cityName",item.get("CITY_NAME"));
            List<Map<String,String>> city = cityList.get(item.get("AREA_CODE"));

            if (null == city){
                city = new ArrayList<Map<String, String>>();
            }
            result.add(provinceItem);
            city.add(cityItem);
            cityList.put(item.get("AREA_CODE"),city);
        }
        for (Map<String,Object> key : result){
            key.put("citys",cityList.get(key.get("areaCode")));
        }
        return result;
    }


    @Override
    public List<Map<String, String>> selectArea() {
        return govAreaMapper.selectAllArea();
    }
}
