package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.PaymentManagerInfoPojo;
import com.oriental.reserve.api.infoManagement.PaymentManageInfoInterface;
import com.oriental.reserve.enums.MessageStatus;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.infoManagement.PaymentManageInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinxin on 2016/12/21.
 *  3.5.4	支付机构董高监信息管理
 */
@Slf4j
@Controller
@RequestMapping("reserve/msg/paymentManagerInfo")
public class PaymentManagerInfoController {

    @Autowired
    PaymentManageInfoInterface paymentManageInfoInterface;

    @RequestMapping("/init")
    public String init(){
        return "reserve/infoManagement/searchPaymentManagerInfo";
    }


    @RequestMapping("/toAdd")
//    @RequiresPermissions("reserve-paymentManagerInfo_add")
    public String toAdd(){
        return "reserve/infoManagement/addPaymentManagerInfo";
    }

    @RequestMapping("/toUpdate")
//    @RequiresPermissions("reserve-paymentManagerInfo_update")
    public String toUpdate(){
        return "reserve/infoManagement/updatePaymentManagerInfo";
    }


    @RequestMapping("/toDetail")
//    @RequiresPermissions("reserve-paymentManagerInfo_update")
    public String toDetail(){
        return "reserve/infoManagement/paymentManagerInfoDetail";
    }

    @OperateLogger(content = "新增支付机构董高监信息",operationType = OperateLogger.OperationType.C,tables = "T_CONTACT_INFO")
    @RequestMapping("/add")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentManagerInfo_add")
    public ResponseDTO<String> add(@RequestBody PaymentManagerInfoPojo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("董高监信息新增:{}",baseModel);
        try {
                //数据校验
           String checkInfo= checkAttribute(baseModel);
            if(!"success".equals(checkInfo)){
                responseDTO.setSuccess(false);
                responseDTO.setMsg(checkInfo);
            }
            PaymentManageInfoDto paymentManageInfoDto=BeanMapperUtil.objConvert(baseModel, PaymentManageInfoDto.class);
            paymentManageInfoDto.setUpdateBy(SessionUtils.getUserName());
            paymentManageInfoDto.setOperator(SessionUtils.getUserName());
            paymentManageInfoDto.setCreateBy(SessionUtils.getUserName());
            paymentManageInfoDto.setOperateType("00");
            ResponseModel<Integer>  responseModel= paymentManageInfoInterface.insertPaymentManageInfo(paymentManageInfoDto);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("董高监信息新增成功");
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("董高监信息新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("董高监信息新增失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改支付机构董高监信息",operationType = OperateLogger.OperationType.U,tables = "T_CONTACT_INFO")
    @RequestMapping("/update")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentManagerInfo_update")
    public ResponseDTO<String> update( @RequestBody PaymentManagerInfoPojo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改信息:{}",baseModel);
        try {
            if("02".equals(baseModel.getOperateType())){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该董高监数据正在申请删除中，不允许修改");
                return responseDTO;
            }
            String checkInfo= checkAttribute(baseModel);
            if(!"success".equals(checkInfo)){
                responseDTO.setSuccess(false);
                responseDTO.setMsg(checkInfo);
            }
            PaymentManageInfoDto paymentManageInfoDto=BeanMapperUtil.objConvert(baseModel, PaymentManageInfoDto.class);
            paymentManageInfoDto.setUpdateBy(SessionUtils.getUserName());
//            新增的数据被审核拒绝后修改仍然为新增
            if ("00".equals(paymentManageInfoDto.getOperateType())&&(!MessageStatus.DEAL_SUCC.getCode().equals(paymentManageInfoDto.getAuditStatus()))){
                paymentManageInfoDto.setOperateType("00");
            }else{
                paymentManageInfoDto.setOperateType("01");
            }
            paymentManageInfoDto.setOperator(SessionUtils.getUserName());
            paymentManageInfoDto.setUpdateBy(SessionUtils.getUserName());
            ResponseModel<Integer> responseModel = paymentManageInfoInterface.updatePaymentManageInfo(paymentManageInfoDto);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("董高监信息修改成功");
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "支付机构董高监信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentManagerInfo_search")
    public ResponseDTO<Pagination<PaymentManagerInfoPojo>>  search(Pagination<PaymentManagerInfoPojo> pagination, PaymentManagerInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentManagerInfoPojo>> responseDTO=new ResponseDTO<Pagination<PaymentManagerInfoPojo>>();
        log.info("查询信息:{},{}",baseModel,pagination);
        try{
            PaymentManageInfoDto paymentManageInfoDto = BeanMapperUtil.objConvert(baseModel, PaymentManageInfoDto.class);
            log.info("支付机构基本信息查询：{}", paymentManageInfoDto);
            ResponseModel<List<PaymentManageInfoDto>> responseModel = paymentManageInfoInterface.queryPaymentManageInfo(paymentManageInfoDto);
            List<PaymentManageInfoDto> paymentBaseInfoDtoList=responseModel.getResult();
            List<PaymentManagerInfoPojo> paymentManagerInfoPojoList = new ArrayList<>();
            if (paymentBaseInfoDtoList.size() > 0) {
                for (PaymentManageInfoDto pmiDto : paymentBaseInfoDtoList) {
                    PaymentManagerInfoPojo pmiPojo = BeanMapperUtil.objConvert(pmiDto, PaymentManagerInfoPojo.class);
                    //json转化
                    //出资人转化
                    paymentManagerInfoPojoList.add(pmiPojo);
                }
            }
            pagination.setList(paymentManagerInfoPojoList);
            if(paymentManagerInfoPojoList!=null&&paymentBaseInfoDtoList.size()!=0){
                pagination.setRowCount(paymentManagerInfoPojoList.get(0).getRowCount());
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setMsg("董高监信息查询成功");
        }catch(Exception e){
            log.error("董高监信息查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("董高监信息查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @ResponseBody
//    @RequiresPermissions("reserve-paymentManagerInfo_delete")
    public ResponseDTO<String> updateItemEnableFlag(PaymentManagerInfoPojo paymentManagerInfoPojo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            paymentManagerInfoPojo.setOperateType("02");
            paymentManagerInfoPojo.setOperator(SessionUtils.getUserName());
            paymentManagerInfoPojo.setUpdateBy(SessionUtils.getUserName());
            PaymentManageInfoDto paymentManageInfoDto = BeanMapperUtil.objConvert(paymentManagerInfoPojo, PaymentManageInfoDto.class);
            List<PaymentManageInfoDto> paymentManageInfoDtoList = new ArrayList<>();
            paymentManageInfoDtoList.add(paymentManageInfoDto);
            ResponseModel<Integer> responseModel=paymentManageInfoInterface.deletePaymentManageInfo(paymentManageInfoDtoList);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("董高监信息删除成功");
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        }catch (Exception e){
            log.error("修改状态错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("董高监信息删除失败");
        }
        return responseDTO;
    }

    /**
     * 数据校验
     * @param paymentManagerInfoPojo
     * @return
     */
    private String checkAttribute(PaymentManagerInfoPojo paymentManagerInfoPojo){
        String telRegx="[^\\u4e00-\\u9fa5]{0,}";
        Pattern telPattern=Pattern.compile(telRegx);
        Matcher matcherTFax=telPattern.matcher(paymentManagerInfoPojo.getTel());
        boolean b=matcherTFax.matches();
        if(!b){
            return "电话格式不正确，禁止中文";
        }
     return "succss";
    }


}
