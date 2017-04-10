package com.oriental.manage.controller.institution;

/**
 * Created by wangjun on 2016/5/9.
 */

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("institution/datadict")
public class DataDictConterller {
    @Autowired
    private IDataDictService dataDictService;

    @RequestMapping("init")
    public String init(){

        return "institution/searchDataDictByPay";
    }
    @OperateLogger(content = "查询支付机构交易代码信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("institution-datadict_select")
    @ResponseBody
    public ResponseDTO <Pagination<DataDict>> queryPage (DataDict dataDict,Pagination<DataDict> pagination){
        ResponseDTO <Pagination<DataDict>> responseDTO =new ResponseDTO<Pagination<DataDict>>();
        try {
            dataDict.setColName("TRANS_CODE");
            dataDict.setItemName("COMMON_VALUE");
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
    @RequiresPermissions("institution-datadict_add")
    public String toAdd(){

        return "institution/addDataDictByPay";
    }
    @RequestMapping("toUpdate")
    @RequiresPermissions("institution-datadict_update")
    public String update(){
        return  "institution/updateDataDictByPay";
    }

    @OperateLogger(content = "新增支付机构交易代码",operationType = OperateLogger.OperationType.C,tables = "T_DATA_DICT")
    @RequestMapping("add")
    @RequiresPermissions("institution-datadict_add")
    @ResponseBody
    public ResponseDTO<String> add(DataDict dataDict){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try  {
            dataDict.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            dataDict.setColName("TRANS_CODE");
            dataDict.setItemName("COMMON_VALUE");


            dataDict.setItemDesc(Constants.getDataDictMap().get(CacheKey.TRANS_CODE_ALL.name().toLowerCase()).get(dataDict.getItemVal()));
            dataDict.setCreateTime(new Date());
            dataDict.setLastUpdTime(new Date());

            if(dataDictService.searData(dataDict)>0){
                dataDictService.searData(dataDict);
                responseDTO.setMsg("支付机构交易代码已经存在");
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
    @OperateLogger(content = "修改支付机构交易代码",operationType = OperateLogger.OperationType.U,tables = "T_DATA_DICT")
    @RequestMapping("update")
    @RequiresPermissions("institution-datadict_update")
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
    @OperateLogger(content = "删除支付机构交易代码",operationType = OperateLogger.OperationType.D,tables = "T_DATA_DICT")
    @RequestMapping("delete")
    @RequiresPermissions("institution-datadict_delete")
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
