package com.oriental.manage.controller.business;

/**
 * Created by wangjun on 2016/6/7.
 * 业务机构交易代码信息
 */

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("business/datadict")
public class DataDictBusinessConterller {
    @Autowired
    private IDataDictService dataDictService;

    @RequestMapping("init")
    public String init(){

        return "business/searchDataDictByBusiness";
    }
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<DataDict>> queryPage (DataDict dataDict, Pagination<DataDict> pagination){
        ResponseDTO<Pagination<DataDict>> responseDTO =new ResponseDTO<Pagination<DataDict>>();
        try {

            dataDict.setItemName("COMMON_VALUE");
            dataDict.setColName("TRANS_CODE");
            dataDictService.queryPageByPay(pagination,dataDict);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @RequestMapping("toAdd")
    public String add(){

        return "business/addDataDictByBusiness";
    }
    @RequestMapping("toUpdate")
    public String update(){
        return  "business/updateDataDictByBusiness";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(DataDict dataDict){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try  {
            dataDict.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            dataDict.setColName("TRANS_CODE");
            dataDict.setItemName("COMMON_VALUE");


            dataDict.setItemDesc(Constants.getDataDictMap().get(CacheKey.TRANS_CODE_ALL.name()).get(dataDict.getItemVal()));
            dataDict.setCreateTime(new Date());
            dataDict.setLastUpdTime(new Date());

            if(dataDictService.searData(dataDict)>0){
                dataDictService.searData(dataDict);
                responseDTO.setSuccess(false);
            }else{
                dataDictService.addDataDict(responseDTO,dataDict);
            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;

    }
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(DataDict dataDict){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            dataDict.setLastUpdTime(new Date());
            dataDictService.updateDataDict(responseDTO,dataDict);
        }catch(Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(DataDict dataDict) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            dataDictService.deleteDataDict(responseDTO,dataDict);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

}
