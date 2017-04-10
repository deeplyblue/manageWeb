package com.oriental.manage.controller.settlementfront;

import com.oriental.check.commons.util.DateUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.fileUtils.ExcelContentExt;
import com.oriental.manage.core.fileUtils.ExtExcelWriterCallBack;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.HeaderExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.Bean2MapUtil;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.paycenter.commons.mode.Response;
import com.oriental.settlementfront.service.facade.manager.MchntTransDetailFacade;
import com.oriental.settlementfront.service.facade.manager.model.MchntTransDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.office.excel.api.poi.POIExcelApi;
import org.webbuilder.office.excel.config.CustomCellStyle;
import org.webbuilder.office.excel.config.ExcelWriterConfig;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>商户交易明细控制器</li>
 * <li>User:蒯越 Date:2016/5/17 Time:10:44</li>
 * </ul>
 */
@Slf4j
@Controller
public class MchntTransDetailController {

    @Autowired
    private MchntTransDetailFacade mchntTransDetailFacade;

    @Autowired
    private FileDownAjax fileDownAjax;

    @RequestMapping("/settlementFront/mchntTransDetail/init")
    @RequiresPermissions("mchntTransDetail_init")
    public String init() {
        return "settlementfront/searchMchntTransDetail";
    }

    @OperateLogger(content = "商户交易查询",operationType = OperateLogger.OperationType.R)
    @ResponseBody
    @RequiresPermissions("mchntTransDetail_init")
    @RequestMapping("/settlementFront/mchntTransDetail/search")
    public ResponseDTO<Pagination<MchntTransDetailDTO>> queryPage(Pagination<MchntTransDetailDTO> pagination,
                                                                  MchntTransDetailDTO query,
                                                                  String sDateStart,
                                                                  String sDateEnd,
                                                                  String sSettleDateStart,
                                                                  String sSettleDateEnd,
                                                                  String sTransAmtStart,
                                                                  String sTransAmtEnd) {
        ResponseDTO<Pagination<MchntTransDetailDTO>> responseDTO = new ResponseDTO<>();
        try {
            query.setStartRow(pagination.getStartRow());
            query.setPageSize(pagination.getPageSize());
            query.setDateStart(DateUtil.parse(sDateStart));
            query.setDateEnd(DateUtil.parse(sDateEnd));
            query.setSettleDateStart(DateUtil.parse(sSettleDateStart));
            query.setSettleDateEnd(DateUtil.parse(sSettleDateEnd));
            if (StringUtils.isNotBlank(sTransAmtStart)) {
                query.setTransAmtStart(Long.parseLong(BigDecimalUtils.preciseMul(sTransAmtStart, "100", 0)));
            }
            if (StringUtils.isNotBlank(sTransAmtEnd)) {
                query.setTransAmtEnd(Long.parseLong(BigDecimalUtils.preciseMul(sTransAmtEnd, "100", 0)));
            }
            log.info("商户交易明细查询参数："+query);
            Response<com.oriental.settlementfront.service.facade.common.model.Pagination<MchntTransDetailDTO>> response
                    = mchntTransDetailFacade.pageQuery(query, SessionUtils.getUserName());
            log.info("商户交易明细查询结果："+response);
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

    @RequiresPermissions("mchntTransDetail_down")
    @RequestMapping("/settlementFront/mchntTransDetail/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(MchntTransDetailDTO query,
                                           String oDateStart,
                                           String oDateEnd,
                                           String oSettleDateStart,
                                           String oSettleDateEnd,
                                           String sTransAmtStart,
                                           String sTransAmtEnd){
        ResponseEntity<byte[]> responseEntity;
        File tempFile = null;
        try {
            tempFile = fileDownAjax.touch("商户交易查询-" + DateUtils.now() + ".xlsx");
            query.setStartRow(0);
            query.setPageSize(10000);
            query.setDateStart(DateUtil.parse(oDateStart));
            query.setDateEnd(DateUtil.parse(oDateEnd));
            query.setSettleDateStart(DateUtil.parse(oSettleDateStart));
            query.setSettleDateEnd(DateUtil.parse(oSettleDateEnd));
            if (StringUtils.isNotBlank(sTransAmtStart)) {
                query.setTransAmtStart(Long.parseLong(BigDecimalUtils.preciseMul(sTransAmtStart, "100", 0)));
            }
            if (StringUtils.isNotBlank(sTransAmtEnd)) {
                query.setTransAmtEnd(Long.parseLong(BigDecimalUtils.preciseMul(sTransAmtEnd, "100", 0)));
            }
            Response<com.oriental.settlementfront.service.facade.common.model.Pagination<MchntTransDetailDTO>> response
                    = mchntTransDetailFacade.pageQuery(query, SessionUtils.getUserName());

            if(response.isSuccess()){
                //表头
                LinkedList<Header> tHeaders = new LinkedList<>();
                //内容
                List<Object> datas = new ArrayList<>();
                tHeaders.add(new HeaderExt("序号", "index"));
                tHeaders.add(new HeaderExt("商户", "mchntCode", ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("发起方", "initiatorCode",ExcelContentExt.MCHNT));
                tHeaders.add(new HeaderExt("订单号", "orderNo"));
                tHeaders.add(new HeaderExt("订单时间", "orderTime"));
                tHeaders.add(new HeaderExt("订单金额(元)", "orderAmt", ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("接入渠道", "connChannelDesc"));
                tHeaders.add(new HeaderExt("交易类型", "transCodeDesc"));
                tHeaders.add(new HeaderExt("交易状态", "transStatusDesc"));
                tHeaders.add(new HeaderExt("交易金额(元)", "transAmt",ExcelContentExt.CURRENCY));
                tHeaders.add(new HeaderExt("业务请求流水号", "busiReqNo"));
                tHeaders.add(new HeaderExt("业务请求时间", "busiReqTime"));
                tHeaders.add(new HeaderExt("勾兑状态", "chkStatusDesc"));
                tHeaders.add(new HeaderExt("业务清算日期", "busiSettleDate"));
                tHeaders.add(new HeaderExt("是否参与清算", "settleFlag"));
                tHeaders.add(new HeaderExt("清算状态", "settleStatusDesc"));
                tHeaders.add(new HeaderExt("支付订单号", "innerPayTransNo"));
                tHeaders.add(new HeaderExt("是否已删除", "flag"));

                int i=1;
                for(MchntTransDetailDTO mchntTransDetailDTO : response.getResult().getPageList()){
                    Map map = Bean2MapUtil.convertBean(mchntTransDetailDTO);
                    if("1".equals(mchntTransDetailDTO.getDeleteFlag())){
                        map.put("flag", "删除");
                    }
                    if("0".equals(mchntTransDetailDTO.getDeleteFlag())){
                        map.put("flag", "正常");
                    }
                    if("0".equals(mchntTransDetailDTO.getSettleFlag())){
                        map.put("settleFlag", "无需清分");
                    }
                    if("1".equals(mchntTransDetailDTO.getSettleFlag())){
                        map.put("settleFlag", "参与清分");
                    }
                    if("2".equals(mchntTransDetailDTO.getSettleFlag())){
                        map.put("settleFlag", "止付");
                    }
                    if("3".equals(mchntTransDetailDTO.getSettleFlag())){
                        map.put("settleFlag", "冻结");
                    }
                    map.put("index", i);
                    datas.add(map);
                    i++;
                }
                ExcelWriterConfig config = new ExcelWriterConfig(){
                    @Override
                    public CustomCellStyle getCellStyle(int row, int column, String header, Object value) {
                        CustomCellStyle style = super.getCellStyle(row, column, header, value);
                        if (row > 0 && StringUtils.equals("orderTime", header) ) {
                            style.setFormat("yyyy-m-d h:mm:ss");
                        }
                        if(row>0 && StringUtils.equals("busiReqTime",header)){
                            style.setFormat("yyyy-m-d h:mm:ss");
                        }
                        return style;
                    }
                };
                Map m=response.getResult().getSumData();
                m.put("initiatorCode","总笔数:");
                m.put("orderNo",response.getResult().getPageList().size());
                m.put("orderTime","总计:");

                datas.add(m);
                config.setHeaders(tHeaders);
                config.setDatas(datas);
                ExtExcelWriterCallBack ca = new ExtExcelWriterCallBack(config);
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                POIExcelApi.getInstance().write(outputStream, ca);
                responseEntity = fileDownAjax.getResponseEntity(tempFile);
            }else{
                log.error("下载失败");
                responseEntity = fileDownAjax.getResponseEntityFail();
            }
        }catch (Exception e){
            log.error("下载失败",e);
            responseEntity = fileDownAjax.getResponseEntityFail();
        }finally {
            fileDownAjax.forceDelete(tempFile);
        }

        return responseEntity;
    }

}
