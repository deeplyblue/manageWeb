package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.SeqNames;
import com.oriental.manage.service.merchant.ISeqNamesService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangjun on 2016/5/23.
 * 查询支付机构序列关系配置
 */
@Slf4j
@Controller
@RequestMapping("merchant/seqNames")
public class SeqNamesController {

    @Autowired
    private ISeqNamesService seqNamesService;

    @RequestMapping("init")
    public String init(){
        return "merchant/searchSeqNames";
    }

    @OperateLogger(content = "查询支付机构序列关系配置",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("seqNames_select")
    @ResponseBody
    public ResponseDTO<Pagination<SeqNames>> queryPage(SeqNames seqNames, Pagination<SeqNames> pagination){
        ResponseDTO<Pagination<SeqNames>> responseDTO=new ResponseDTO<Pagination<SeqNames>>();
        try{
            seqNamesService.queryPage(pagination,seqNames);

            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return  responseDTO;
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("seqNames_add")
    public String toAdd(){
        return  "merchant/addSeqNames";
    }

    @OperateLogger(content = "新增支付机构序列关系",operationType = OperateLogger.OperationType.C,tables = "T_SEQ_NAMES")
    @RequestMapping("add")
    @RequiresPermissions("seqNames_add")
    @ResponseBody
    public ResponseDTO<String> add(SeqNames seqNames){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            seqNames.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            seqNamesService.addSeqNames(responseDTO,seqNames);
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
    @RequestMapping("toUpdate")
    @RequiresPermissions("seqNames_update")
    public String toUpdate(){
        return "merchant/addSeqNames";
    }

    @OperateLogger(content = "修改支付机构序列关系",operationType = OperateLogger.OperationType.U,tables = "T_SEQ_NAMES")
    @RequestMapping("update")
    @RequiresPermissions("seqNames_update")
    @ResponseBody
    public ResponseDTO<String> update(SeqNames seqNames){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();

        try{

            seqNamesService.updateSeqName(responseDTO,seqNames);
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @OperateLogger(content = "删除支付机构序列关系",operationType = OperateLogger.OperationType.D,tables = "T_SEQ_NAMES")
    @RequestMapping("delete")
    @RequiresPermissions("seqNames_delete")
    @ResponseBody
    public ResponseDTO<String> delete(SeqNames seqNames){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try{
            seqNamesService.deleteSeqName(responseDTO,seqNames);
        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
}
