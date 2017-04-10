package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoxin on 2016/12/20.
 *
 */
@Slf4j
@Controller
@RequestMapping("/reserve")
public class ReserveBatchInformation {
/*
    @RequestMapping("batch/init")
    public String init(){
        return "reserve/searchBatchInformation";
    }

    @RequestMapping("batch/search")
    @ResponseBody
    public ResponseDTO<Pagination<List>> search(){
        ResponseDTO<Pagination<List>> responseDTO=new ResponseDTO<Pagination<List>>();
        Pagination<List> pagination= new    Pagination<List>();
        List list=new ArrayList();
        Map map = new HashMap();
        try{
            map.put("name","lifei");
            map.put("name1","lifei1");
            map.put("name2","lifei2");
            map.put("name3","lifei3");
            map.put("name4","lifei4");
            list.add(map);
            pagination.setList(list);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }*/
}
