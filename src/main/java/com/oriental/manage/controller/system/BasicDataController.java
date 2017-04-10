package com.oriental.manage.controller.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.StringCommonUtils;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/30.
 */
@Slf4j
@Controller
@RequestMapping("system/basicData")
public class BasicDataController {

    @Autowired
    private IDataDictService dataDictService;

    @RequestMapping("init")
    public String init(){
        return "system/searchDataDict";
    }

    @OperateLogger(content = "查询数据字典",operationType = OperateLogger.OperationType.R )
    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<DataDict>> queryPage(Pagination<DataDict> pagination, DataDict dataDict) {
        ResponseDTO<Pagination<DataDict>> responseDTO = new ResponseDTO<Pagination<DataDict>>();
        try {
           dataDictService.getAllDataDict(pagination,dataDict);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("basicData_add")
    @RequestMapping("toAdd")
    public String toAdd(){
        return "system/addBasicData";
    }

    @RequiresPermissions("basicData_add")
    @OperateLogger(content = "新增数据字典",operationType = OperateLogger.OperationType.C,tables = "T_DATA_DICT" )
    @RequestMapping("add")
    @ResponseBody
    public ResponseDTO<String> add(@RequestBody DataDict[] dataDict){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        String name=dataDict[0].getItemName();
        String itemCn=dataDict[0].getColNameCn();
        String colName=dataDict[0].getColName();
        try {
            if(dataDictService.searData(dataDict[0])>0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("已经配置过！");
            }else{
          for (DataDict temp:dataDict) {
              temp.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
              temp.setItemName(name);
              temp.setColNameCn(itemCn);
              temp.setColName(colName);
              temp.setLastUpdTime(new Date());
              temp.setCreateTime(new Date());
              dataDictService.addDataDict(responseDTO, temp);
          }
            }
        }
       catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @RequiresPermissions("basicData_update")
    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "system/updateBasicData";
    }

    @RequiresPermissions("basicData_update")
    @OperateLogger(content = "修改数据字典",operationType = OperateLogger.OperationType.U,tables = "T_DATA_DICT" )
    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<String> update(DataDict dataDict){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try {
            dataDict.setLastUpdTime(new Date());
            dataDictService.updateDataDict(responseDTO,dataDict);
        }catch (Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequiresPermissions("basicData_delete")
    @OperateLogger(content = "删除数据字典",operationType = OperateLogger.OperationType.D,tables = "T_DATA_DICT" )
    @RequestMapping("delete")
    @ResponseBody
    public ResponseDTO<String> delete(DataDict dataDict){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try {
            dataDictService.deleteDataDict(responseDTO,dataDict);
        }catch (Exception e){
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
