package com.oriental.manage.controller.opcif;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.LogIdInterceptor;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.pojo.opcif.TCustomerBankPojo;
import com.oriental.opcif.common.model.RequestModel;
import com.oriental.opcif.common.model.ResponseModel;
import com.oriental.opcif.product.bizModel.manager.TCustomerBankDto;
import com.oriental.opcif.product.facade.manager.CustomerBankInfoFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxinhai on 2016/8/25.
 */
@Slf4j
@Controller
@RequestMapping("opcif/opcifBindCard")
public class OpcifBindCardController {
    @SuppressWarnings("SpringJavaAutowiringInspection")

    @Autowired(required = false)
    private CustomerBankInfoFacade customerBankInfoFacade;
    @Autowired
    private FileDownAjax fileDownAjax;


    @RequestMapping("init")
    public String init() {
        return "opcif/opcifBindCard";
    }

    @OperateLogger(content = "客户绑卡信息查询",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("customer-opcifBindCard_search")
    public ResponseDTO<Pagination<TCustomerBankDto>> queryPage(@RequestBody Pagination<TCustomerBankDto> pagination) {
        ResponseDTO<Pagination<TCustomerBankDto>> responseDTO = new ResponseDTO<Pagination<TCustomerBankDto>>();
        try {
            RequestModel<TCustomerBankDto> requestModel = new RequestModel<TCustomerBankDto>();
            requestModel.setPageNo(pagination.getPageNum());
            requestModel.setPageSize(pagination.getPageSize());
            requestModel.setRequest(pagination.getQueryBean());
            ResponseModel<TCustomerBankDto> responseModel = customerBankInfoFacade.selectTCustomerBankDtoList(requestModel,MDC.get(LogIdInterceptor.log_id));
            if (responseModel.getResponseResult()) {
                pagination.setRowCount(responseModel.getTotal());
                pagination.setList(responseModel.getList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getResponseMessage());
            }
        } catch (Exception e) {
            log.error("查询异常！", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败！");
        }
        return responseDTO;
    }


    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestBody TCustomerBankPojo tCustomerBankPojo) {

        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("客户绑卡信息" + DateUtils.now() + ".xlsx");
            //查询需要下载导出的结果

            TCustomerBankDto tCustomerBankDto=BeanMapperUtil.objConvert(tCustomerBankPojo,TCustomerBankDto.class);
            RequestModel<TCustomerBankDto> requestModel = new RequestModel<TCustomerBankDto>();
            requestModel.setRequest(tCustomerBankDto);
            requestModel.setPageNo(1);
            requestModel.setPageSize(10000);
            ResponseModel<TCustomerBankDto> responseModel=customerBankInfoFacade.selectTCustomerBankDtoList(requestModel,MDC.get(LogIdInterceptor.log_id));
            List<TCustomerBankDto> TCustomerBankDtoLIst =responseModel.getList();
            //表头
            LinkedList<Header> tHeaders = new LinkedList<>();
            //内容
            List<Object> datas = new ArrayList<>();

            //添加表头
            tHeaders.add(new HeaderExt("序号", "index"));
            tHeaders.add(new HeaderExt("协议号", "agreementNo"));
            tHeaders.add(new HeaderExt("客户号", "customerNo"));
            tHeaders.add(new HeaderExt("用户号", "entityNo"));
            tHeaders.add(new HeaderExt("协议类型", "agreementType"));
            tHeaders.add(new HeaderExt("签约时间", "signDatetime"));
            tHeaders.add(new HeaderExt("账户姓名", "accountName"));
            tHeaders.add(new HeaderExt("卡号后4位", "cardnosuf"));
            tHeaders.add(new HeaderExt("支付机构", "orgBankCode",ExcelContentExt.ORGANIZE));
            tHeaders.add(new HeaderExt("帐号类型", "bankCardType"));
            tHeaders.add(new HeaderExt("银行预留手机号", "mobile"));
            tHeaders.add(new HeaderExt("协议状态", "agreementStatus"));
            tHeaders.add(new HeaderExt("卡有效期", "accountValiddatetime"));
            tHeaders.add(new HeaderExt("发卡行城市", "cardIssuersCity"));
            tHeaders.add(new HeaderExt("银行名称", "bankName"));
            tHeaders.add(new HeaderExt("协议申请号", "authApplyId"));
            //循环添加表数据
            for(int i = 0;i<TCustomerBankDtoLIst.size();i++){
                Map map = Bean2MapUtil.convertBean(TCustomerBankDtoLIst.get(i));
               TCustomerBankDto tCustomerBankDto1= TCustomerBankDtoLIst.get(i);
                //协议类型
                if("HOUSE_TRUSTEESHIP".equals(tCustomerBankDto1.getAgreementType())){
                    map.put("agreementType","房产托管委托代扣");
                }
                if("QUICK".equals(tCustomerBankDto1.getAgreementType())){
                    map.put("agreementType","快捷支付");
                }
                if("FINANCE".equals(tCustomerBankDto1.getAgreementType())){
                    map.put("agreementType","理财");
                }
                if("CARD_AUTH".equals(tCustomerBankDto1.getAgreementType())){
                    map.put("agreementType","银行卡认证");
                }
//                账户类型 BANK_CARD_TYPE
                if("BA".equals(tCustomerBankDto1.getBankCardType())){
                    map.put("bankCardType","银行账户");
                }
                if("PB".equals(tCustomerBankDto1.getBankCardType())){
                    map.put("bankCardType","存折");
                }
                if("DB".equals(tCustomerBankDto1.getBankCardType())){
                    map.put("bankCardType","借记卡");
                }
                if("CD".equals(tCustomerBankDto1.getBankCardType())){
                    map.put("bankCardType","信用卡");
                }
//                协议状态  CLOSED
                if("CLOSED".equals(tCustomerBankDto1.getAgreementStatus())){
                    map.put("agreementStatus","解约");
                }
                if("SIGN_FAIL".equals(tCustomerBankDto1.getAgreementStatus())){
                    map.put("agreementStatus","签约失败");
                }
                if("REPEAT_SIGN".equals(tCustomerBankDto1.getAgreementStatus())){
                    map.put("agreementStatus","重复签约");
                }
                if("SIGNED".equals(tCustomerBankDto1.getAgreementStatus())){
                    map.put("agreementStatus","签约");
                }
                map.put("index", i+1);
                datas.add(map);
            }
            ExcelWriterConfig config = new ExcelWriterConfig();
            config.setHeaders(tHeaders);
            config.setDatas(datas);
            //合并单元格

            ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            POIExcelApi.getInstance().write(outputStream, ca);
            responseEntity = fileDownAjax.getResponseEntity(tempFile);

        } catch (Exception e) {
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        } finally {
            fileDownAjax.forceDelete(tempFile);
        }

        return responseEntity;
    }





}
