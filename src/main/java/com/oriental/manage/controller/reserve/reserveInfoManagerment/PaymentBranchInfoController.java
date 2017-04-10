package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.reserve.PaymentBranchInfoPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinxin on 2016/12/22.
 *
 * 支付机构分公司信息管理
 */
@Slf4j
@Controller
@RequestMapping("reserve/msg/paymentBranchInfo")
public class PaymentBranchInfoController {
    @RequestMapping("/init")
    public String init(){
        return "reserve/infoManagement/searchPaymentBranchInfo";
    }

    @RequestMapping("/toAdd")
//    @RequiresPermissions("reserve-paymentBranchInfo_add")
    public String toAdd(){
        return "reserve/infoManagement/addPaymentBranchInfo";
    }

    @RequestMapping("/toUpdate")
//    @RequiresPermissions("reserve-paymentBranchInfo_update")
    public String toUpdate(){
        return "reserve/infoManagement/updatePaymentBranchInfo";
    }

    @RequestMapping("/toDetail")
//    @RequiresPermissions("reserve-paymentBranchInfo_update")
    public String toDetail(){
        return "reserve/infoManagement/paymentBranchInfoDetail";
    }
    @OperateLogger(content = "新增支付机构分公司信息管理",operationType = OperateLogger.OperationType.C,tables = "T_CONTACT_INFO")
    @RequestMapping("/add")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentBranchInfo_add")
            public ResponseDTO<String> add(PaymentBranchInfoPojo baseModel) {
                ResponseDTO<String> responseDTO = new ResponseDTO<String>();
                log.info("新增信息:{}",baseModel);
                try {
                    System.out.println("paymentBranchInfo-add================");
                } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("新增失败");
        }
        return responseDTO;
    }


    @OperateLogger(content = "修改支付机构分公司信息管理",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("/update")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentBranchInfo_update")
    public ResponseDTO<String> update( PaymentBranchInfoPojo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
//        log.info("修改信息:{}",baseModel);
        System.out.println("进入修改界面PaymentBranchInfoPojo");
        try {
        } catch (Exception e) {
//            log.error("更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "支付机构分公司信息管理查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentBranchInfo_search")
    public ResponseDTO<Pagination<PaymentBranchInfoPojo>>  search(Pagination<PaymentBranchInfoPojo> pagination, PaymentBranchInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentBranchInfoPojo>> responseDTO=new ResponseDTO<Pagination<PaymentBranchInfoPojo>>();
//        log.info("查询信息:{},{}",baseModel,pagination);
        List<PaymentBranchInfoPojo> list = new ArrayList<>();
        PaymentBranchInfoPojo p= new PaymentBranchInfoPojo();
        p.setId("1");
        p.setOrgNo("1111");
        p.setOrgName("123");
//        日期不能正常显示
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
//    @RequiresPermissions("reserve-paymentBranchInfo_delete")
    public ResponseDTO<String> updateItemEnableFlag(PaymentBranchInfoPojo PaymentBranchInfoPojo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            System.out.println("进入删除界面PaymentBranchInfoPojo");
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
