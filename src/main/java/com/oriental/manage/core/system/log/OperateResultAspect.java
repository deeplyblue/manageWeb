package com.oriental.manage.core.system.log;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.schedule.NotifyFlashCacheSchedule;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.OperateRecordMapper;
import com.oriental.manage.pojo.base.OperateRecord;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantContactInfo;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.pojo.merchant.settleManage.ClearAccount;
import com.oriental.reserve.api.business.BusinessWhiteListInterface;
import com.oriental.reserve.model.business.BusinessManageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by lupf on 2016/7/12.
 */
@Slf4j
@Aspect
@Component
public class OperateResultAspect {

    @Autowired(required = false)
    private OperateRecordMapper operateRecordMapper;
    @Autowired
    private Constants constants;
    @Autowired
    private NotifyFlashCacheSchedule notifyFlashCacheSchedule;
    @Autowired(required = false)
    private BusinessWhiteListInterface businessWhiteListInterface;

    @Around(value = "within(com.oriental.manage.controller..*) && @annotation(ol)")
    public Object methodAdvice(ProceedingJoinPoint joinPoint, OperateLogger ol) {
        System.out.println("----------------operateLogger----------------------");
        Object returnObj = null;
        try {
            returnObj = joinPoint.proceed(joinPoint.getArgs());
            if (ol == null) {

            } else {
                //当且仅当返回值是 ResponseDTO时记录返回结果
                if (returnObj.getClass() == ResponseDTO.class) {
                    ResponseDTO responseDTO = (ResponseDTO) returnObj;

                    OperateRecord operateRecord = new OperateRecord();
                    operateRecord.setId(MDC.get(LogIdInterceptor.log_id));
                    operateRecord.setUserId(SessionUtils.getUserId());
                    operateRecord.setLastUpdTime(new Date());
                    operateRecord.setOpResult(String.valueOf(responseDTO.isSuccess()));

                    operateRecordMapper.updateByPrimaryKeySelective(operateRecord);

                    //缓存刷新
                    if (StringUtils.isNotBlank(ol.tables())) {
                        String[] tables = ol.tables().split(",");
                        for (String table : tables) {
                            switch (ol.noticeSystem()) {
                                case IN:
                                    reFlashCache(table);
                                    break;
                                case OUT:
                                    notifyOutSystem(table,joinPoint,ol);
                                    break;
                                case ALL:
                                    reFlashCache(table);
                                    notifyOutSystem(table,joinPoint,ol);
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            log.error("切面处理失败", throwable);
        }

        return returnObj;
    }

    /**
     * @param table 通知外部系统
     *              （只有使用同一个数据库的外部系统可用）
     */
    public final void notifyOutSystem(final String table,ProceedingJoinPoint joinPoint, OperateLogger ol) {

        notifyFlashCacheSchedule.addToNotify(table);
        Object [] returnObj = null;

        try{
            String[]stringArray={"T_MCHNT_INFO","T_CONTACT_INFO","T_CLEAR_ACCOUNT"};
            returnObj = joinPoint.getArgs();
            BusinessManageDto businessManageDto = new BusinessManageDto();
            if(ol.operationType().name().equals("C")){
                businessManageDto.setOperateType("01");
            }

            if(ol.operationType().name().equals("U")){
                businessManageDto.setOperateType("02");
            }
            if(ol.operationType().name().equals("D")){
                businessManageDto.setOperateType("03");
            }

            if( Arrays.asList(stringArray).contains(table)){
                System.out.println("-------通知备付金-------");
               for(int i=0;i<returnObj.length;i++) {
                   //修改商户通知
                   if (returnObj[i].getClass().getName().equals(MerchantInfo.class.getName())) {
                       MerchantInfo mchntInfo = (MerchantInfo) returnObj[i];
                       businessManageDto.setMerchantNO(mchntInfo.getMerchantCode());}
                   //修改商户联系人通知
                   else if (returnObj[i].getClass().getName().equals(MerchantContactInfo.class.getName())) {
                       MerchantContactInfo info = (MerchantContactInfo) returnObj[i];
                       businessManageDto.setMerchantNO(info.getCompanyCode());}
                   //修改商户账户通知
                   else  if (returnObj[i].getClass().getName().equals(ClearAccount.class.getName())) {
                       ClearAccount info = (ClearAccount) returnObj[i];
                       businessManageDto.setMerchantNO(info.getMerchantCode());}
               }
                log.info("通知信息", businessManageDto);
                businessWhiteListInterface.selectBusinessMessage(businessManageDto);
            }

        }catch (Exception e){
            log.error("通知失败", e);
        }catch (Throwable throwable){
            log.error("通知失败", throwable);
        }
    }

    /**
     * @param table 表名
     *              <p>
     *              刷新系统内部缓存
     */
    private void reFlashCache(final String table) {
        if (StringUtils.equalsIgnoreCase(table, "t_data_dict")) {
            constants.getDataDictMap().clear();
            constants.initDataDict();
        } else {

        }
        switch (table.toLowerCase()) {
            case "t_data_dict":
                constants.getDataDictMap().clear();
                constants.initDataDict();
                break;
            case "t_mchnt_info":
                constants.initMerchantInfo();
                break;
            case "t_organize_info":
                constants.initOrganizeInfo();
                break;
            case "t_role_info":
                constants.initRoleInfo();
                constants.getRoleInfo();
                break;
            case "t_bank_info":
                constants.initBankInfo();
                break;
        }
    }
}
