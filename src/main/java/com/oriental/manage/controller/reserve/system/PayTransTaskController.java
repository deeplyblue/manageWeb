package com.oriental.manage.controller.reserve.system;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.PayTransTaskService;
import com.oriental.reserve.model.paid.PayTransTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tanglu on 2017/1/10 0010.
 * 查看日志
 */
@Slf4j
@Controller
@RequestMapping("/reserve/inif")
public class PayTransTaskController {

    @Autowired
    private PayTransTaskService payTransTaskService;

    @RequestMapping("/init")
    public String init() {
        return "reserve/system/searchPayTransTask";
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<PayTransTaskDto>> search(Pagination<PayTransTaskDto> pagination, PayTransTaskDto payTransTaskDto){
        ResponseDTO<Pagination<PayTransTaskDto>> responseDTO = new ResponseDTO<Pagination<PayTransTaskDto>>();
        try{
            payTransTaskService.query(pagination,payTransTaskDto,responseDTO);
            log.info("查看日志:{},{}", payTransTaskDto, pagination);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch (Exception e){
            log.error("查看日志失败", e);
            e.printStackTrace();
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查看日志失败");
        }
        return responseDTO;
    }

}

