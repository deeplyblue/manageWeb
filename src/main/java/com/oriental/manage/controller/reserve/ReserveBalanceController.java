package com.oriental.manage.controller.reserve;


import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.ReserveBalanceService;
import com.oriental.reserve.model.ReserveBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Yang xp
 * Date: 2016/8/19
 * Time: 9:08
 * Desc 备付金余额操作
 */
@Slf4j
@Controller
@RequestMapping("/reserve/balance")
public class ReserveBalanceController {


    @Autowired
    private ReserveBalanceService reserveBalanceService;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchReserveBalance";
    }

    @RequiresPermissions("reserveBalance_update")
    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "reserve/updateReserveBalance";
    }

    @RequestMapping("/search")
    @RequiresPermissions("reserveBalance_search")
    @ResponseBody
    public ResponseDTO<Pagination<ReserveBalance>> search(@RequestBody  Pagination<ReserveBalance> pagination){
        ResponseDTO<Pagination<ReserveBalance>> responseDTO = new ResponseDTO<Pagination<ReserveBalance>>();
        try{
            reserveBalanceService.queryReserveBalance(pagination,responseDTO);
        }catch (Exception e){
            log.error("备付金余额查询失败",e);
            responseDTO.setMsg("查询失败");
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @RequiresPermissions("reserveBalance_update")
    @ResponseBody
    public ResponseDTO<String> update(@RequestBody ReserveBalance reserveBalance){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            reserveBalanceService.updateReserveBalance(responseDTO,reserveBalance);
        }catch (Exception e){
            log.error("{}:备付金余额修改异常:",reserveBalance,e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }
        return responseDTO;
    }

}
