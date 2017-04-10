package com.oriental.manage.controller.check;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.check.service.facade.manager.MchntChkResultFacade;
import com.oriental.check.service.facade.manager.model.BankChkResultDTO;
import com.oriental.check.service.facade.manager.model.MchntChkResultDTO;
import com.oriental.manage.core.excelUtils.ExcelTemplateUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.core.utils.StringCommonUtils;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>商户对账结果控制器</li>
 * <li>User:蒯越 Date:2016/5/17 Time:10:40</li>
 * </ul>
 */
@Slf4j
@Controller
public class MchntChkResultController {

    @Autowired
    private MchntChkResultFacade mchntChkResultFacade;
    @Autowired
    private FileDownAjax fileDownAjax;
    @Autowired
    private com.oriental.manage.core.system.Constants Constants;

    @RequestMapping("/check/mchntChkResult/init")
    @RequiresPermissions("mchntChkResult_search")
    public String init() {
        return "check/searchMchntChkResult";
    }

    @OperateLogger(content = "商户对账结果查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequestMapping("/check/mchntChkResult/search")
    @RequiresPermissions("mchntChkResult_search")
    public ResponseDTO<Pagination<MchntChkResultDTO>> queryPage(Pagination<MchntChkResultDTO> pagination,
                                                                MchntChkResultDTO query,
                                                                String sDateStart,
                                                                String sDateEnd) {
        ResponseDTO<Pagination<MchntChkResultDTO>> responseDTO = new ResponseDTO<Pagination<MchntChkResultDTO>>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            Response<com.oriental.check.service.facade.model.Pagination<MchntChkResultDTO>> response
                    = mchntChkResultFacade.pageQuery(query, SessionUtils.getUserName());
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

    @ResponseBody
    @RequestMapping("/check/mchntChkResult/download")
    @RequiresPermissions("mchntChkResult_download")
    public ResponseEntity<byte[]> download(MchntChkResultDTO mchntChkResultDTO,
                                           String mchntCode,
                                           String oDateStart,
                                           String oDateEnd){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try{
            InputStream inputStream = new FileInputStream(Constants.getExcelPath() +"/商户对账结果模板.xlsx");
            tempFile = fileDownAjax.touch("商户对账结果-" + DateUtils.now() + ".xlsx");
            String titleTime= StringCommonUtils.insertStr(oDateStart,"-",4,7)+"——"+
                    StringCommonUtils.insertStr(oDateEnd,"-",4,7);
            mchntChkResultDTO.setStartRow(0);
            mchntChkResultDTO.setPageSize(10000);
            mchntChkResultDTO.setMchntCode(mchntCode);
            mchntChkResultDTO.setDateStart(DateUtil.parse(oDateStart.trim()));
            mchntChkResultDTO.setDateEnd(DateUtil.parse(oDateEnd.trim()));
            Response<com.oriental.check.service.facade.model.Pagination<MchntChkResultDTO>> response
                    = mchntChkResultFacade.pageQuery(mchntChkResultDTO, SessionUtils.getUserName());
            if(response.isSuccess()){

                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                //添加表头
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("商户", "mchntCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("清算日期", "settleDate"));
                tHeaders.add(new HeaderExt("笔数", "extTransCount"));
                tHeaders.add(new HeaderExt("金额(元)", "extTransAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCount"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "chkSuccCount"));
                tHeaders.add(new HeaderExt("金额(元)", "chkSuccAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "chkErrCount"));
                tHeaders.add(new HeaderExt("金额(元)", "chkErrAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCountC"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmtC", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("笔数", "transCountD"));
                tHeaders.add(new HeaderExt("金额(元)", "transAmtD", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("净额（元）","settleAmt",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("对账状态", "statusDesc"));
                int i = 1;
                for(MchntChkResultDTO mchntChkResultDTO1 : response.getResult().getPageList()){
                    Map map = Bean2MapUtil.convertBean(mchntChkResultDTO1);
                    map.put("index", i);
                    if(mchntChkResultDTO1.getTransAmtD()!=null&&mchntChkResultDTO1.getTransAmtC()!=null){
                        map.put("settleAmt",mchntChkResultDTO1.getTransAmtC()-mchntChkResultDTO1.getTransAmtD());
                    }
                    datas.add(map);
                    i++;
                }
                Map m = response.getResult().getSumData();
                m.put("settleDate","合计：");
                datas.add(m);
                ExcelWriterConfig config = new ExcelWriterConfig();
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                config.setStartWith(4);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                ExcelTemplateUtil.write(inputStream, outputStream, config,titleTime);

                responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }else{
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        }catch (Exception e){
            log.error("下载失败", e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally{
            fileDownAjax.forceDelete(tempFile);
        }

        return responseEntity;
    }
}
