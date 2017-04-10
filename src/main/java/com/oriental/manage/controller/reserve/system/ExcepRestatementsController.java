package com.oriental.manage.controller.reserve.system;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.ExcepRestatements;
import com.oriental.reserve.api.sys.ExcepRestatementsInterface;
import com.oriental.reserve.api.sys.SystParticipantsMsgInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.sys.ExcepRestatementsDto;
import com.oriental.reserve.model.sys.SystParticipantsMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yutao on 2016/12/21.
 */
@Controller
@Slf4j
@RequestMapping("reserve/dayEnd/excepRestatements")
public class ExcepRestatementsController {

    @Autowired
    private ExcepRestatementsInterface excepRestatementsInterface;

    @Autowired
    private SystParticipantsMsgInterface systParticipantsMsgInterface;


    @RequestMapping("init")
    public String init(){ return "reserve/system/searchExcepRestatements";}

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<ExcepRestatements>> query (@RequestBody Pagination<ExcepRestatements> pagination){
        ResponseDTO<Pagination<ExcepRestatements>> responseDTO = new ResponseDTO<>();
        try{
            ExcepRestatementsDto excepRestatementsDto = BeanMapperUtil.objConvert(pagination.getQueryBean(), ExcepRestatementsDto.class);
            log.info("查询日终异常重报申请参数信息:{}", pagination);
            ResponseModel<List<ExcepRestatementsDto>> responseModel = excepRestatementsInterface.queryExcepRestatements(excepRestatementsDto);
            if(responseModel.isSuccess()){
                List<ExcepRestatements> list = BeanMapperUtil.mapList(responseModel.getResult(), ExcepRestatements.class);
                pagination.setList(list);
                if(list.size()>0){
                    pagination.setRowCount(list.get(0).getRowCount());
                }
                responseDTO.setObject(pagination);
                responseDTO.setSuccess(true);
            }else {
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

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "reserve/system/addExcepRestatements";
    }
    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO<String> add(@RequestBody ExcepRestatements excepRestatements ){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("新增日终异常重报申请:{}",excepRestatements);
            ExcepRestatementsDto excepRestatementsDto = BeanMapperUtil.objConvert(excepRestatements, ExcepRestatementsDto.class);
            excepRestatementsDto.setCreateBy(SessionUtils.getUserName());
            ResponseModel<String> responseModel = excepRestatementsInterface.excepRestatementsApply(excepRestatementsDto);
            responseModel.setSuccess(true);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("新增成功");
            }else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }

        }catch(Exception e){
            log.error("新增失败",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }

        return responseDTO;
    }

    @RequestMapping("toUpdate")
    public String toUpdate(){
        return "/reserve/system/updateExcepRestatements";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseDTO<ExcepRestatements> updateExcepRestatements(@RequestBody ExcepRestatements excepRestatements){
        ResponseDTO<ExcepRestatements> responseDTO = new ResponseDTO<>();
        try{
            log.info("修改日终异常重报申请信息:{}",excepRestatements);
            ExcepRestatementsDto excepRestatementsDto = BeanMapperUtil.objConvert(excepRestatements, ExcepRestatementsDto.class);
            ResponseModel<String> responseModel = excepRestatementsInterface.updateExcepRestatements(excepRestatementsDto);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg(responseModel.getResult());
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg("修改失败");
            }
        }catch (Exception e){
            log.error("修改失败:{}",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @RequestMapping("getOrgCache")
    @ResponseBody
    public ResponseDTO<Map<String, String>> getOrgCache(){
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        try{
            SystParticipantsMsgDto systParticipantsMsgDto = new SystParticipantsMsgDto();
            systParticipantsMsgDto.setDeleteFlag("0");
            ResponseModel<List<SystParticipantsMsgDto>> responseModel = systParticipantsMsgInterface.query(systParticipantsMsgDto);
            if(responseModel.isSuccess()){
                List<SystParticipantsMsgDto> list = responseModel.getResult();
                if(list !=null) {
                    for (SystParticipantsMsgDto sysParticipantsMsgDto : list) {
                        map.put(sysParticipantsMsgDto.getCode(),sysParticipantsMsgDto.getCode());
                    }
                }
            }
        }catch (Exception e){
            log.info("日终异常重报申请获取缓存异常:{}",e);
            map.put("", "未找到相关数据");
            responseDTO.setSuccess(false);
        }finally {
            responseDTO.setObject(map);
        }
        return responseDTO;
    }

}
