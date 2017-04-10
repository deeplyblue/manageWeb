package com.oriental.manage.controller.reserve.reserveInfoManagerment;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.PaymentBaseInfoConvertUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.reserve.PaymentBaseInfoPojo;
import com.oriental.manage.pojo.reserve.ServiceRange;
import com.oriental.manage.pojo.reserve.ServiceType;
import com.oriental.manage.pojo.reserve.SponsorPojo;
import com.oriental.reserve.api.infoManagement.PaymentBaseInfoInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.infoManagement.PaymentBaseInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinxin on 2016/12/20.
 *
 * 支付机构基本信息管理
 */
@Slf4j
@Controller
@RequestMapping("reserve/msg/paymentBaseInfo")
public class PaymentBaseInfoController {

    @Autowired
    PaymentBaseInfoInterface paymentBaseInfoInterface;

    @RequestMapping("/init")
    public String init(){
        return "reserve/infoManagement/searchPaymentBaseInfo";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "reserve/infoManagement/updatePaymentBaseInfo";
    }

    @RequestMapping("/toDetail")
    public String toDetail(){
        return "reserve/infoManagement/paymentBaseInfoDetail";
    }

    @OperateLogger(content = "修改支付机构基本信息",operationType = OperateLogger.OperationType.U,tables = "t_payment_base_info")
    @RequestMapping("/update")
    @ResponseBody
    public ResponseDTO<String> update(@RequestBody PaymentBaseInfoPojo baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if("02".equals(baseModel.getOperateType())){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该基本数据正在申请删除中，不允许修改");
                return responseDTO;
            }
            //数据校验
            //注册资本,实缴货币资本,整数位16位，小数位2位。
            if(baseModel.getAddrProvince()==null||baseModel.getAddrProvince()==""){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("住所省份不能为空");
                return responseDTO;
            }
            if(baseModel.getAddrCity()==null||baseModel.getAddrCity()==""){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("住所城市不能为空");
                return responseDTO;
            }
            if(baseModel.getRealProvince()==null||baseModel.getRealProvince()==""){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("实际经营省份不能为空");
                return responseDTO;
            }
            if(baseModel.getRealCity()==null||baseModel.getRealCity()==""){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("实际经营城市不能为空");
                return responseDTO;
            }

            String regx="\\d{1,18}.\\d{2}";
            Pattern pattern=Pattern.compile(regx);
            Matcher matcherReg=pattern.matcher(baseModel.getRegisteredCapital()+"");
            boolean b=matcherReg.matches();
            if(!b){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("注册资本格式错误，整数位18位，小数位2位");
                return responseDTO;
            }
            Matcher matcherMon=pattern.matcher(baseModel.getMoneyCapital()+"");
            boolean b2=matcherMon.matches();
            if(!b2){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("实缴货币资本格式错误，整数位18位，小数位2位");
                return responseDTO;
            }
            String telRegx="[^\\u4e00-\\u9fa5]{0,}";
            Pattern patten=Pattern.compile(telRegx);
            Matcher matcher=patten.matcher(baseModel.getOrgCode());
            boolean orgCode=matcher.matches();
            if(!orgCode){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("组织机构代码禁止中文");
                return responseDTO;
            }
            Matcher matcherTFax=patten.matcher(baseModel.getFax());
            boolean b3=matcherTFax.matches();
            if(!b3){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("传真禁止中文");
                return responseDTO;
            }
            Matcher matcherTel=patten.matcher(baseModel.getComplainTel());
            boolean b4=matcherTel.matches();
            if(!b4){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("投诉电话禁止中文");
                return responseDTO;
            }
            String spoRegx="(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
            Pattern spoPattern=Pattern.compile(spoRegx);
            //判端出资人持股比例  5%，主要出资人持股比例 10%
            List<SponsorPojo> sponsorPojoList = baseModel.getSponsorList();
            if(sponsorPojoList==null || sponsorPojoList.size()==0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("出资人持股比例不可为空");
                return responseDTO;
            }
            Set<String> set= new HashSet<>();
            int index=-1;
            double  shareholding=0;
            for(int i=0 ;i< sponsorPojoList.size(); i++){
                SponsorPojo sponsorPojo= sponsorPojoList.get(i);
                if(sponsorPojo.getShareholderName()==null || sponsorPojo.getShareholderName()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"出资人不能为空");
                    return responseDTO;
                }
                if(sponsorPojo.getIdNo()==null || sponsorPojo.getIdNo()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"身份证号不能为空");
                    return responseDTO;
                }
                if(sponsorPojo.getShareholding()==null || sponsorPojo.getShareholding()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"出资人持股比例不能为空");
                    return responseDTO;
                }

                Matcher matcherSpo=spoPattern.matcher(sponsorPojo.getIdNo());
                boolean b1=matcherSpo.matches();
                if(!b1){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"出资人身份证号格式不正确");
                    return responseDTO;
                }
                try{
                    double holding=Double.valueOf(sponsorPojoList.get(i).getShareholding());
                    shareholding+=holding;
                    if(holding<5){
                        index=i;
                        break;
                    }
                }catch(Exception e){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("出资人持股比例格式错误");
                    return responseDTO;
                }
                set.add(sponsorPojoList.get(i).getIdNo());
            }

            if(index>=0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("出资人持股比例必须大于或等于5%");
                return responseDTO;
            }
            if(shareholding>100){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("出资人持股比例大于100%");
                return responseDTO;
            }
            if(sponsorPojoList.size()!=set.size()){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("出资人身份证号重复");
                return responseDTO;
            }
            set.clear();

            List<SponsorPojo> masterSponsorPojoList = baseModel.getMastereSponsorList();
            if(masterSponsorPojoList==null || masterSponsorPojoList.size()==0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("主要出资人持股比例不可为空");
                return responseDTO;
            }
            shareholding=0;
            for(int i=0 ;i< masterSponsorPojoList.size(); i++){
                SponsorPojo sponsorPojo= masterSponsorPojoList.get(i);
                if(sponsorPojo.getShareholderName()==null || sponsorPojo.getShareholderName()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"主要出资人不能为空");
                    return responseDTO;
                }
                if(sponsorPojo.getIdNo()==null || sponsorPojo.getIdNo()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"主要身份证号不能为空");
                    return responseDTO;
                }
                if(sponsorPojo.getShareholding()==null || sponsorPojo.getShareholding()==""){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"主要出资人持股比例不能为空");
                    return responseDTO;
                }
                Matcher matcherSpo=spoPattern.matcher(sponsorPojo.getIdNo());
                boolean b1=matcherSpo.matches();
                if(!b1){
                    responseDTO.setSuccess(false);
                    i=i+1;
                    responseDTO.setMsg("第"+i+"主要出资人身份证号格式不正确");
                    return responseDTO;
                }
                try{
                    double holding=Double.valueOf(sponsorPojo.getShareholding());
                    shareholding+=holding;
                    if(holding<10){
                        index=i;
                        break;
                    }
                }catch(Exception e){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("主要出资人持股比例格式错误");
                    return responseDTO;
                }
                set.add(masterSponsorPojoList.get(i).getIdNo());
            }
            if(index>=0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("主要出资人持股比例必须大于或等于10%");
                return responseDTO;
            }
            if(shareholding>100){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("主要出资人持股比例大于100%");
                return responseDTO;
            }
            if(masterSponsorPojoList.size()!=set.size()){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("主要出资人身份证号重复");
                return responseDTO;
            }
            List<ServiceType> serviceTypePojoList=baseModel.getServiceList();
            if(serviceTypePojoList==null||serviceTypePojoList.size()==0){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("业务种类不能为空");
                return responseDTO;
            }
            Set<String> serviceType = new HashSet<>();
            Set<String> range= new HashSet<>();
            for (ServiceType s:serviceTypePojoList) {
                if(s.getServiceType()==null||s.getServiceType()==""){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("业务种类不能为空");
                    return responseDTO;
                }
                serviceType.add(s.getServiceType());
                List<ServiceRange> rangeList= s.getServiceRange();
                if(rangeList==null||rangeList.size()==0){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("业务范围不能为空");
                    return responseDTO;
                }
                for (ServiceRange r:rangeList) {
                    if(r.getRange()==null||r.getRange()==""){
                        responseDTO.setSuccess(false);
                        responseDTO.setMsg("业务范围不能为空");
                        return responseDTO;
                    }
                    range.add(r.getRange());

                }
                if(rangeList.size()!=range.size()){
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("业务范围重复："+s.getServiceType());
                    return responseDTO;
                }
                range.clear();
            }
            if(serviceType.size()!= serviceTypePojoList.size()){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("业务种类重复");
                return responseDTO;
            }
            baseModel.setOperator(SessionUtils.getUserName());
            baseModel.setUpdateBy(SessionUtils.getUserName());
            baseModel.setOperateType("01");
           // System.out.println(paymentBaseInfoJson);
            //PaymentBaseInfoPojo baseModel=JSON.parse(paymentBaseInfoJson,PaymentBaseInfoPojo.class);
            PaymentBaseInfoDto paymentBaseInfoDto = BeanMapperUtil.objConvert(baseModel, PaymentBaseInfoDto.class);
            paymentBaseInfoDto.setServiceTypeAndRange(PaymentBaseInfoConvertUtils.listToJsonForServiceTypeAndRange(baseModel.getServiceList()));
            paymentBaseInfoDto.setSponsor(PaymentBaseInfoConvertUtils.listToStringForSponsor(baseModel.getSponsorList()));
            paymentBaseInfoDto.setMastereSponsor(PaymentBaseInfoConvertUtils.listToStringForSponsor(baseModel.getMastereSponsorList()));
            System.out.println("===="+baseModel.getServiceList());
            log.info("支付机构基本信息修改：{}", paymentBaseInfoDto);
//            paymentBaseInfoDto.setLicenseDate(DateUtils.parse(baseModel.getLicenseDate(),DateUtils.DATESHORTFORMAT));
//            paymentBaseInfoDto.setLicenseGrantDate(DateUtils.parse(baseModel.getLicenseGrantDate(),DateUtils.DATESHORTFORMAT));
            ResponseModel<Integer> responseModel =   paymentBaseInfoInterface.editPaymentBaseInfo(paymentBaseInfoDto);
           if(responseModel.isSuccess()){
               responseDTO.setSuccess(true);
               responseDTO.setMsg("支付机构基本信息更新成功");
           } else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("支付机构基本信息更新失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("支付机构基本信息更新失败");
        }
        return responseDTO;
    }

    @OperateLogger(content = "支付机构基本信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentBaseInfoPojo>>  search(Pagination<PaymentBaseInfoPojo> pagination, PaymentBaseInfoPojo baseModel) {
        ResponseDTO<Pagination<PaymentBaseInfoPojo>> responseDTO=new ResponseDTO<Pagination<PaymentBaseInfoPojo>>();
        try{

            PaymentBaseInfoDto paymentBaseInfoDto = BeanMapperUtil.objConvert(baseModel, PaymentBaseInfoDto.class);

            log.info("支付机构基本信息查询：{}", paymentBaseInfoDto);
            ResponseModel<List<PaymentBaseInfoDto>> responseModel = paymentBaseInfoInterface.queryPaymentBaseInfo(paymentBaseInfoDto);
            List<PaymentBaseInfoDto> paymentBaseInfoDtoList=responseModel.getResult();
            List<PaymentBaseInfoPojo> paymentBaseInfoPojoList = new ArrayList<>();
            if (paymentBaseInfoDtoList.size() > 0) {
                for (PaymentBaseInfoDto pbiPo : paymentBaseInfoDtoList) {
                    PaymentBaseInfoPojo pbiPojo = BeanMapperUtil.objConvert(pbiPo, PaymentBaseInfoPojo.class);
                    //json转化
                    pbiPojo.setServiceList(PaymentBaseInfoConvertUtils.jsonToListForServiceTypeAndRange(pbiPo.getServiceTypeAndRange()));
                    //出资人转化
                    pbiPojo.setSponsorList(PaymentBaseInfoConvertUtils.stringToListForSponsor(pbiPo.getSponsor()));
                    pbiPojo.setMastereSponsorList(PaymentBaseInfoConvertUtils.stringToListForSponsor(pbiPo.getMastereSponsor()));
                    paymentBaseInfoPojoList.add(pbiPojo);
                }
            }
            pagination.setList(paymentBaseInfoPojoList);
            if(paymentBaseInfoPojoList!=null&&paymentBaseInfoDtoList.size()!=0){
                pagination.setRowCount(paymentBaseInfoPojoList.get(0).getRowCount());
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
            responseDTO.setMsg("查询成功");
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("基本信息查询失败");
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseDTO<String> delete(PaymentBaseInfoPojo paymentBaseInfoPojo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            paymentBaseInfoPojo.setOperateType("02");
            paymentBaseInfoPojo.setOperator(SessionUtils.getUserName());
            paymentBaseInfoPojo.setUpdateBy(SessionUtils.getUserName());
            PaymentBaseInfoDto paymentBaseInfoDto = BeanMapperUtil.objConvert(paymentBaseInfoPojo, PaymentBaseInfoDto.class);
            List<PaymentBaseInfoDto> paymentBaseInfoDtoList = new ArrayList<>();
            paymentBaseInfoDtoList.add(paymentBaseInfoDto);
            ResponseModel<Integer> responseModel=paymentBaseInfoInterface.deletePaymentBaseInfo(paymentBaseInfoDtoList);
            if(responseModel.isSuccess()){
                responseDTO.setSuccess(true);
                responseDTO.setMsg("基本信息删除成功");
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        }catch (Exception e){
//            log.error("修改状态错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("基本信息删除失败");
        }
        return responseDTO;
    }

}
