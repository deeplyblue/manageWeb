package com.oriental.manage.controller.check;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.err.MchntChkErrFacade;
import com.oriental.check.service.facade.err.model.MchntChkErrDTO;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.IPUtil;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>商户对账差异控制器</li>
 * <li>User:蒯越 Date:2016/5/17 Time:10:38</li>
 * </ul>
 */
@Slf4j
@Controller
public class MchntChkErrController {

    @Autowired
    private MchntChkErrFacade mchntChkErrFacade;
    @Autowired
    private FileDownAjax fileDownAjax;


    @RequestMapping("/check/mchntChkErr/init")
    public String init() {
        return "check/searchMchntChkErr";
    }

    @OperateLogger(content = "商户对账差异查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/mchntChkErr/search")
    public ResponseDTO<Pagination<MchntChkErrDTO>> queryPage(Pagination<MchntChkErrDTO> pagination,
                                                             MchntChkErrDTO query,
                                                             String sDateStart,
                                                             String sDateEnd,
                                                             String sHandleDateStart,
                                                             String sHandleDateEnd) {
        ResponseDTO<Pagination<MchntChkErrDTO>> responseDTO = new ResponseDTO<Pagination<MchntChkErrDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            if (StringUtils.isNotBlank(sHandleDateStart)) {
                query.setHandleDateStart(DateUtil.parse(sHandleDateStart + "000000", DateUtil.yyyyMMddHHmmss));
            }
            if (StringUtils.isNotBlank(sHandleDateEnd)) {
                query.setHandleDateEnd(DateUtil.parse(sHandleDateEnd + "235959", DateUtil.yyyyMMddHHmmss));
            }
            Response<com.oriental.check.service.facade.model.Pagination<MchntChkErrDTO>> response
                    = mchntChkErrFacade.pageQuery(query, SessionUtils.getUserName());
            if (response.isSuccess()) {
                pagination.setRowCount(response.getResult().getTotalCount());
                pagination.setList(response.getResult().getPageList());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
                responseDTO.setSumObject(response.getResult().getSumData());
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }

    @OperateLogger(content = "处理商户对账差异",operationType = OperateLogger.OperationType.U)
    @ResponseBody
    @RequestMapping("/check/mchntChkErr/handle")
    public ResponseDTO<String> handle(String id, String handleType,HttpServletRequest request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            Response<String> response = mchntChkErrFacade.handle(id, handleType, SessionUtils.getUserName(),IPUtil.getIpAddrByRequest(request));
            if (response.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject(response.getResult());
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getErrorMsg());
            }
        } catch (BusiException e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("处理失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }
    @RequestMapping("/check/mchntChkErr/download")
    @ResponseBody
    @RequiresPermissions("mchntChkErr_download")
    public ResponseEntity<byte[]> download(MchntChkErrDTO query,
                                           String oDateStart,
                                           String oDateEnd){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            tempFile = fileDownAjax.touch("商户对账差异报表-" + DateUtils.now() + ".xlsx");
            query.setStartRow(0);
            query.setPageSize(10000);
            query.setDateStart(DateUtil.parse(oDateStart));
            query.setDateEnd(DateUtil.parse(oDateEnd));
            Response<com.oriental.check.service.facade.model.Pagination<MchntChkErrDTO>> response
                    = mchntChkErrFacade.pageQuery(query, SessionUtils.getUserName());
            if(response.isSuccess()){

                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("商户", "mchntCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("业务请求流水号", "busiReqNo"));
                tHeaders.add(new HeaderExt("业务响应流水号", "busiRespNo"));
                tHeaders.add(new HeaderExt("清算日期", "extSettleDate"));
                tHeaders.add(new HeaderExt("平台清算日期", "settleDate"));
                tHeaders.add(new HeaderExt("支付流水号", "innerPayTransNo"));
                tHeaders.add(new HeaderExt("交易类型", "transCodeDesc"));
                tHeaders.add(new HeaderExt("系统交易金额", "transAmt",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("外部系统交易金额", "extTransAmt",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("外部交易状态", "extTransStatus"));
                tHeaders.add(new HeaderExt("差异类型", "errTypeDesc"));
                tHeaders.add(new HeaderExt("处理状态", "handleStatusDesc"));
                tHeaders.add(new HeaderExt("处理人", "handler"));
                tHeaders.add(new HeaderExt("处理时间", "handleTime"));
                tHeaders.add(new HeaderExt("处理方式", "handleTypeDesc"));
                tHeaders.add(new HeaderExt("备注", "remark"));
                int i = 1;
                for(MchntChkErrDTO mchntChkErrDTO : response.getResult().getPageList()){
                    Map map = Bean2MapUtil.convertBean(mchntChkErrDTO);
                    map.put("index", i);
                    datas.add(map);
                    i++;
                }
                Map m=response.getResult().getSumData();
                m.put("settleDate","总笔数");
                m.put("innerPayTransNo",response.getResult().getPageList().size());
                m.put("transCodeDesc","总计");
                datas.add(m);

                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);
                File file = ExcelUtils.addTitle(tempFile, "商户对账差错报表", fileDownAjax, tHeaders.size(), oDateStart, oDateEnd);

                responseEntity = fileDownAjax.getResponseEntity(file);
            }else{
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();

        }finally {
            fileDownAjax.forceDelete(tempFile);
        }
        return responseEntity;
    }
}
