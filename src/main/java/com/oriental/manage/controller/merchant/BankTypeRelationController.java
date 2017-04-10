package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.bank.BankTypeRelation;
import com.oriental.manage.service.merchant.IBankTypeRelationService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangjun on 2016/5/23.
 * 查询银行类型关系信息
 */

@Slf4j
@Controller
@RequestMapping("merchant/bankTypeRelation")
public class BankTypeRelationController {

    @Autowired
    private IBankTypeRelationService bankTypeRelationService;

    @RequestMapping("init")
    public String init(){
        return "merchant/searchBankTypeRelation";
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("bankTypeRelation_add")
    public String toAdd(){return "merchant/addBankTypeRelation";}

    @RequestMapping("toUpdate")
    @RequiresPermissions("bankTypeRelation_update")
    public String toUpdate(){return "merchant/updateBankTypeRelation";}

    @OperateLogger(content = "查询银行类型关系信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("bankTypeRelation_select")
    @ResponseBody
    public ResponseDTO<Pagination<BankTypeRelation>> queryPage(BankTypeRelation bankTypeRelation , Pagination<BankTypeRelation> pagination){
        ResponseDTO<Pagination<BankTypeRelation>> responseDTO=new ResponseDTO<Pagination<BankTypeRelation>>();
        try{
            bankTypeRelationService.queryPage(pagination,bankTypeRelation);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }
    @OperateLogger(content = "查询银行代码",operationType = OperateLogger.OperationType.R)
    @RequestMapping("/getAllBankCode")
    @ResponseBody
    public ResponseDTO<List<String>> getAllBankCode(){
        ResponseDTO<List<String>> responseDTO =new ResponseDTO<List<String>>();
        try{

            List<String> bankCode=bankTypeRelationService.getAllBankCode();
            responseDTO.setSuccess(true);
            responseDTO.setObject(bankCode);

        }catch (Exception e){
            log.error("查询银行代码异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("程序异常");
        }
        return  responseDTO;
    }
    @OperateLogger(content = "新增银行类型关系",operationType = OperateLogger.OperationType.C,tables = "T_BANK_TYPE_RELATION_INFO")
    @RequestMapping("/add")
    @RequiresPermissions("bankTypeRelation_add")
    @ResponseBody
    public ResponseDTO<String> add(@RequestBody List<BankTypeRelation> bankTypeRelations){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try{
           if(bankTypeRelations!=null && bankTypeRelations.size()>0) {
               if(bankTypeRelationService.checkBankType(bankTypeRelations.get(0))>0){
                   responseDTO.setSuccess(false);
                   responseDTO.setMsg("该银行类型已被配置,不能新增可修改");
               }
               else{
               for(int i=0;i<bankTypeRelations.size();i++){
                   BankTypeRelation bankTypeRelation=   bankTypeRelations.get(i);
                   bankTypeRelation.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
                   bankTypeRelationService.addBankTypeRelation(responseDTO, bankTypeRelation);
           }
               }
           }
            if(bankTypeRelations.size()==0){
                responseDTO.setMsg("银行代码必须勾选一条！");
            }

        }catch (Exception e){
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "删除银行类型关系",operationType = OperateLogger.OperationType.D,tables = "T_BANK_TYPE_RELATION_INFO")
    @RequestMapping("/delete")
    @RequiresPermissions("bankTypeRelation_delete")
    @ResponseBody
    public ResponseDTO<String> delete(BankTypeRelation bankTypeRelations){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try{
                bankTypeRelationService.deleteBankTypeRelation(responseDTO, bankTypeRelations);
        }catch (Exception e){
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
    @OperateLogger(content = "修改银行类型关系",operationType = OperateLogger.OperationType.U,tables = "T_BANK_TYPE_RELATION_INFO")
    @RequestMapping("/update")
    @RequiresPermissions("bankTypeRelation_update")
    @ResponseBody
    public ResponseDTO<String> update(@RequestBody List<BankTypeRelation> bankTypeRelations){
        ResponseDTO<String> responseDTO=new ResponseDTO<String>();
        try{
            if(bankTypeRelations!=null && bankTypeRelations.size()>0) {
                    BankTypeRelation bankTypeRelation= bankTypeRelations.get(0);
                    bankTypeRelationService.deleteBankTypeRelation(responseDTO, bankTypeRelation);
            }
            if(bankTypeRelations!=null && bankTypeRelations.size()>0) {
                for(int i=0;i<bankTypeRelations.size();i++){
                    BankTypeRelation bankTypeRelation=   bankTypeRelations.get(i);
                    bankTypeRelation.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
                    bankTypeRelationService.addBankTypeRelation(responseDTO, bankTypeRelation);
                }
            }
            if(bankTypeRelations.size()==0){
                responseDTO.setMsg("银行代码必须勾选一条！");
            }
        }catch (Exception e){
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }
}
