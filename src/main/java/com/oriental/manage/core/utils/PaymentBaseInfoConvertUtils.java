package com.oriental.manage.core.utils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.oriental.manage.pojo.reserve.ServiceType;
import com.oriental.manage.pojo.reserve.SponsorPojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinxin on 2017/1/3.
 * 支付机构基本信息属性转化工具类
 */
public class PaymentBaseInfoConvertUtils {

    /**
     *主要出资人及持股比例字符串转化为集合
     * @return
     */
    public static List<SponsorPojo> stringToListForSponsor(String sponsor){
        if(sponsor==null||sponsor.length()==0){return null;}
        List<SponsorPojo> list = new ArrayList<>();
        //以|切割
        String [] sponsorArr=sponsor.split("\\|");
        for(int i=0;i<sponsorArr.length;i++){
            SponsorPojo s= new SponsorPojo();
            String [] sArr=null;
            if(sponsorArr[i].contains(",")){
                sArr= sponsorArr[i].split(",");
            }else{
                sArr= sponsorArr[i].split("，");
            }
            try{
                s.setShareholderName(sArr[0]);
                s.setShareholding(sArr[1]);
                s.setIdNo(sArr[2]);
            }catch (Exception e){
                s.setShareholderName(sArr[0]);
                try{
                    s.setShareholding(sArr[1]);
                }catch(Exception e1){
                }
            }
            list.add(s);
        }
        return list;
    }
    /**
     *主要出资人及持股比例集合转化为字符串
     * @return
     */
    public static String listToStringForSponsor(List<SponsorPojo> sponsorPojoList){
        StringBuilder sponsor=new StringBuilder();
        for (int i=0; i<sponsorPojoList.size();i++){
            SponsorPojo sponsorPojo=sponsorPojoList.get(i);
            if(i==sponsorPojoList.size()-1){
                sponsor.append(sponsorPojo.getShareholderName()).append(",").append(sponsorPojo.getShareholding()).
                        append(",").append(sponsorPojo.getIdNo());
                break;
            }
            sponsor.append(sponsorPojo.getShareholderName()).append(",").append(sponsorPojo.getShareholding()).
                    append(",").append(sponsorPojo.getIdNo()).append("|");
        }
        return sponsor.toString();
    }

    /**
     * 业务类型与范围  json转list
     */
    public static String listToJsonForServiceTypeAndRange( List<ServiceType> serviceTypePojos){
        try {
            return JSON.json(serviceTypePojos);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 业务类型与范围  list转json
     */
    public static List<ServiceType> jsonToListForServiceTypeAndRange(String serviceTypeAndRange){
        try {
            JSONArray jsonArray = (JSONArray) JSON.parse(serviceTypeAndRange);
            List<ServiceType> list = new ArrayList<>();
            for (int i=0 ;i <jsonArray.length();i++){
                ServiceType s=JSON.parse(JSON.json(jsonArray.get(i)),ServiceType.class);
                list.add(s);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
