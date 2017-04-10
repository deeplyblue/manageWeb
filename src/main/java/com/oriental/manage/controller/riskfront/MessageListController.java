package com.oriental.manage.controller.riskfront;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.fileUtils.FileUtilsExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.service.base.IDfsFileInfoService;
import com.oriental.riskfront.api.model.*;
import com.oriental.riskfront.api.service.PayRiskFrontInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangjun on 2016/12/6.
 * 风控消息查询
 */
@Slf4j
@Controller
@RequestMapping("riskfront/messageList")
public class MessageListController {

    @Autowired(required = false)
    private PayRiskFrontInterface payRiskFrontInterface;

    @Autowired
    private IDfsFileInfoService iDfsFileInfoService;

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @Value("#{cfgProperties['downloadTempDir']}")
    private String downloadTempDir;

    @RequestMapping("init")
    @RequiresPermissions("messageList_search")
    public String init(){ return  "riskfront/searchMessageList";}

    @RequestMapping("toDetail")
    @RequiresPermissions("riskfront-messageList_detail")
    public String toDetail(){ return  "riskfront/searchMessageListDetail";}

    @RequestMapping("search")
    @ResponseBody
    @RequiresPermissions("messageList_search")
    @OperateLogger(content = "风险信息查询",operationType = OperateLogger.OperationType.R)
    public ResponseDTO<Pagination<RiskFrontMessage>> queryPage(@RequestBody Pagination <RiskFrontRequest> query){
        ResponseDTO<Pagination<RiskFrontMessage>> responseDTO=new ResponseDTO<>();
        try {
            Pagination<RiskFrontMessage> pagination =new Pagination<>();
            query.getQueryBean().setPageSize(query.getPageSize());
            query.getQueryBean().setPage(query.getPageNum());
            String star= query.getQueryBean().getStartDate();
            String end= query.getQueryBean().getEndDate();
            star=star.replace("Z", " UTC");
            end=end.replace("Z", " UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date starDate = format.parse(star);
            Date endDate = format.parse(end);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String sDate=sdf.format(starDate);
            String eDate1=sdf.format(endDate);
            if(sDate.equals(eDate1)){
                query.getQueryBean().setStartDate(sDate);
                query.getQueryBean().setEndDate(DateUtils.computeDate(sDate, 1, "yyyy-MM-dd", "yyyy-MM-dd"));
            }else{
                query.getQueryBean().setStartDate(sDate);
                query.getQueryBean().setEndDate(eDate1);
            }
            PayRiskFrontResponse<List<RiskFrontMessage>> response = payRiskFrontInterface.queryMessageList(query.getQueryBean());
           if(response.isSuccess()){
            pagination.setRowCount(response.getPageCount());
            pagination.setList(response.getResult());
               pagination.setPageNum(query.getPageNum());
               pagination.setPageSize(query.getPageSize());
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
           }else{
               responseDTO.setSuccess(false);
               responseDTO.setMsg(response.getResponseDesc());
           }

        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }


    @RequestMapping("toResult")
    @RequiresPermissions("riskfront-messageList_message")
    public String toResult(){ return  "riskfront/searchMessageListResult";}

    @RequestMapping("toMessage")
    @RequiresPermissions("riskfront-messageList_message")
    @ResponseBody
    public ResponseDTO toMessage(RiskFrontRequest riskFrontRequest){
        ResponseDTO responseDTO=new ResponseDTO<>();
       try{
           PayRiskFrontResponse<RiskFrontMesssageResp> response= payRiskFrontInterface.queryMessageRespByMsgId(riskFrontRequest );
           if(response.isSuccess()) {

               responseDTO.setSuccess(true);
               responseDTO.setObject(response.getResult());
           }else{
               responseDTO.setMsg(response.getResponseDesc());
               responseDTO.setSuccess(false);
           }
       }catch (Exception e){
           log.error("查询失败", e);
           responseDTO.setSuccess(false);
           responseDTO.setMsg(e.getMessage());
       }
        return responseDTO;
    }
    @RequestMapping("toMessageLog")
    @RequiresPermissions("riskfront-messageList_log")
    public String toMessageLog(){ return "riskfront/searchMessageLog";}

    @RequestMapping("MessageLog")
    @RequiresPermissions("riskfront-messageList_log")
    @ResponseBody
    public ResponseDTO toMessageLog(RiskFrontRequest riskFrontRequest){
        ResponseDTO responseDTO=new ResponseDTO<>();
        try{
            PayRiskFrontResponse<List<RiskFrontMessageLog>> response= payRiskFrontInterface.queryMessageLogByMsgId(riskFrontRequest );
            if(response.isSuccess()) {
                responseDTO.setObject(response.getResult());
                responseDTO.setSuccess(true);
            }else{
                responseDTO.setSuccess(false);
                responseDTO.setMsg(response.getResponseDesc());
            }
        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/downEnclosure")
    @RequiresPermissions("riskfront-messageList_down")
    public ResponseEntity<byte[]> downEnclosure(String id){

        try{
            RiskFrontRequest riskFrontRequest=new RiskFrontRequest();
            riskFrontRequest.setMsgId(id);
            PayRiskFrontResponse<List<RiskFrontMessageDfs>> list= payRiskFrontInterface.queryMessageDfsByMsgId(riskFrontRequest);

               String path = downloadTempDir.concat("/").concat(id.concat("/"));
               FileUtilsExt.writeFile(path);

               for (int i = 0; i < list.getResult().size(); i++) {

                   fastDFSPoolUtil.download(list.getResult().get(i).getGroubName(),list.getResult().get(i).getGroupId(),path.concat(list.getResult().get(i).getLocalFileName()));
               }

            String localFileName = downloadTempDir.concat("/").concat(id.concat("风险附件信息.zip"));
            FileUtilsExt.zipFile(Arrays.asList(new File(path).listFiles()),localFileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(id.concat("风险附件信息.zip").getBytes("UTF-8"),"ISO-8859-1"));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(localFileName)), headers, HttpStatus.CREATED);

        } catch (Exception e){
            log.error("下载失败：",e);
        }
        return null;
    }



}
