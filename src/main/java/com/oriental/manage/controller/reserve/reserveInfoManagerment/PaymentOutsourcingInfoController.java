package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.reserve.PaymentOutsourcingInfoPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinxin on 2016/12/22.
 *
 * 支付机构业务外包信息管理
 */
@Slf4j
@Controller
@RequestMapping("reserve/msg/paymentOutsourcingInfo")
public class PaymentOutsourcingInfoController {
    @RequestMapping("/init")
    public String init(){
        return "reserve/infoManagement/searchPaymentOutsourcingInfo";
    }

    @RequestMapping("/toAdd")
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_add")
    public String toAdd(){
        return "reserve/infoManagement/addPaymentOutsourcingInfo";
    }

    @RequestMapping("/toUpdate")
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_update")
    public String toUpdate(){
        return "reserve/infoManagement/updatePaymentOutsourcingInfo";
    }

    @RequestMapping("/toDetail")
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_update")
    public String toDetail(){
        return "reserve/infoManagement/paymentOutsourcingInfoDetail";
    }
    @OperateLogger(content = "新增支付机构业务外包信息管理",operationType = OperateLogger.OperationType.C,tables = "T_CONTACT_INFO")
    @RequestMapping("/add")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_add")
            public ResponseDTO<String> add(PaymentOutsourcingInfoPojo baseModel) {
                ResponseDTO<String> responseDTO = new ResponseDTO<String>();
                log.info("新增信息:{}",baseModel);
                try {
                    System.out.println("PaymentOutsourcingInfo-add================");
                } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }


    @OperateLogger(content = "修改支付机构业务外包信息管理",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("/update")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_update")
    public ResponseDTO<String> update( PaymentOutsourcingInfoPojo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
//        log.info("修改信息:{}",baseModel);
        System.out.println("进入修改界面PaymentOutsourcingInfoPojo");
        try {
        } catch (Exception e) {
//            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "支付机构业务外包信息管理查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentOutsourcingInfo_search")
    public ResponseDTO<Pagination<PaymentOutsourcingInfoPojo>>  search(Pagination<PaymentOutsourcingInfoPojo> pagination, PaymentOutsourcingInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentOutsourcingInfoPojo>> responseDTO=new ResponseDTO<Pagination<PaymentOutsourcingInfoPojo>>();
//        log.info("查询信息:{},{}",baseModel,pagination);
        List<PaymentOutsourcingInfoPojo> list = new ArrayList<>();
        PaymentOutsourcingInfoPojo p= new PaymentOutsourcingInfoPojo();
        p.setId("1");
        p.setOrgNo("1111");
        p.setOrgName("123");
        list.add(p);
        pagination.setList(list);
        try{
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
//            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @ResponseBody
//    @RequiresPermissions("reserve-PaymentOutsourcingInfoPojo_delete")
    public ResponseDTO<String> updateItemEnableFlag(PaymentOutsourcingInfoPojo PaymentOutsourcingInfoPojo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            System.out.println("进入删除界面PaymentOutsourcingInfoPojo");
            responseDTO.setSuccess(true);
            responseDTO.setMsg("删除成功");
        }catch (Exception e){
//            log.error("修改状态错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("删除失败");
        }
        return responseDTO;
    }
}
