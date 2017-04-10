package com.oriental.manage.controller.reserve.system;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.reserve.DayCut;
import com.oriental.reserve.api.sys.DayCutInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.sys.DayCutDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yutao on 2016/12/20.
 */
@Controller
@Slf4j
@RequestMapping("/reserve/dayCut")
public class DayCutController {

    @Autowired
    private DayCutInterface dayCutInterface;

    @RequestMapping("/init")
    public String init() {
        return "reserve/system/searchDayCut";
    }

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<DayCut>> queryDayCut(@RequestBody Pagination<DayCut> pagination){
        ResponseDTO<Pagination<DayCut>> responseDTO = new ResponseDTO<>();

        try{
            DayCutDto dayCutDto = BeanMapperUtil.objConvert(pagination.getQueryBean(), DayCutDto.class);
            log.info("日切请求参数信息:{}",dayCutDto);
            ResponseModel<List<DayCutDto>> responseModel = dayCutInterface.queryDayCut(dayCutDto);
            if(responseModel.isSuccess()){
                List<DayCut> dayCuts = BeanMapperUtil.mapList(responseModel.getResult(), DayCut.class);
                pagination.setList(dayCuts);
                if(dayCuts.size()>0){
                    pagination.setRowCount(dayCuts.get(0).getRowCount());
                }
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }

        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }



}
