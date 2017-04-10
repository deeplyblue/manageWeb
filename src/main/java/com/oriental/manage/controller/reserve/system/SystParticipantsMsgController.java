package com.oriental.manage.controller.reserve.system;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.reserve.SysParticipantsMsg;
import com.oriental.reserve.api.sys.SystParticipantsMsgInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.sys.SystParticipantsMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yutao on 2016/12/21.
 */
@Controller
@Slf4j
@RequestMapping("reserve/sysParticipantsMsg")
public class SystParticipantsMsgController {

    @Autowired
    private SystParticipantsMsgInterface systParticipantsMsgInterface;

    @RequestMapping("/init")
    public String init(){ return "reserve/system/searchSysParticipantsMsg";}

    @RequestMapping("search")
    @ResponseBody
    public ResponseDTO<Pagination<SysParticipantsMsg>> query(@RequestBody  Pagination<SysParticipantsMsg> pagination){
        ResponseDTO<Pagination<SysParticipantsMsg>> responseDTO = new ResponseDTO<>();
        try{
            log.info("查询系统参与者参数信息:{}", pagination);
            SystParticipantsMsgDto systParticipantsMsgDto = BeanMapperUtil.objConvert(pagination.getQueryBean(), SystParticipantsMsgDto.class);
            ResponseModel<List<SystParticipantsMsgDto>> responseModel = systParticipantsMsgInterface.queryPage(systParticipantsMsgDto);
            log.info("系统参与者响应信息:{}",responseModel);
            if(responseModel.isSuccess()){
                List<SysParticipantsMsg> list = BeanMapperUtil.mapList(responseModel.getResult(), SysParticipantsMsg.class);
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

}
