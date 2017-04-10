package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.BankCodeUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.pojo.institution.BusiBank;
import com.oriental.manage.pojo.institution.OrgBank;
import com.oriental.manage.service.bank.IBankInfoService;
import com.oriental.manage.service.institution.IBusiBankService;
import com.oriental.paycenter.commons.utils.BeanCopier;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Desc:
 * Date: 2016/6/12
 * User: ZhouBin
 * See :
 */
@Slf4j
@Controller
@RequestMapping("institution/busiBank")
public class BusiBankController {

    @Autowired
    IBusiBankService busiBankService;

    @Autowired
    private IBankInfoService bankInfoService;

    @RequestMapping("init")
    public String init(){
        return "institution/searchBusiBank";
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("busiBank_add")
    public String add(){
        return "institution/updateBusiBank";
    }


    @OperateLogger(content = "查询业务资金源配置信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("busiBank_select")
    @ResponseBody
    public ResponseDTO<Pagination<BusiBank>> queryPage(Pagination<BusiBank> pagination, BusiBank busiBank){
        ResponseDTO <Pagination<BusiBank>>  responseDTO=new ResponseDTO<Pagination<BusiBank>>();
        try {
            busiBankService.queryPage(pagination,busiBank);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/getBusiBankInfo")
    @ResponseBody
    public ResponseDTO <List<BusiBank>> getBankInfoByBankTypeAjax(BusiBank bean){
        ResponseDTO<List<BusiBank>> responseDTO = new ResponseDTO<List<BusiBank>>();
        try {
            List<BankInfo> bankInfoLists = bankInfoService.getAllBankInfo();
            Map<String,List<BankInfo>> bankInfoMap = BankCodeUtils.getBankMap(bankInfoLists);
            List<BankInfo> banks = BankCodeUtils.getBankInfoByBankType(bankInfoMap, bean.getBankType());
            List<BusiBank> list = busiBankService.queryBusiBank(bean);
            List<BusiBank> totalList= new ArrayList<BusiBank>();
            Map<String,BusiBank> map = new HashMap<String,BusiBank>();
            for(BusiBank bank:list){
                map.put(bank.getBankType()+"#"+bank.getBankCode(),bank);
            }
            for(BankInfo bankInfo:banks){
                BusiBank busiBank= new BusiBank();
                if (map.keySet().contains(bean.getBankType()+"#"+bankInfo.getBankCode())){
                    BeanCopier.copy(map.get(bean.getBankType()+"#"+bankInfo.getBankCode()),busiBank);
                    busiBank.setChoose("1");
                }else{
                    busiBank.setChoose("0");
                    busiBank.setBankCode(bankInfo.getBankCode());
                    busiBank.setBankCodeDesc(bankInfo.getBankName());
                    busiBank.setBankType(bean.getBankType());
                    busiBank.setBusiType(bean.getBusiType());
                }
                totalList.add(busiBank);
            }
            responseDTO.setObject(totalList);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询业务银行异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询业务银行异常");
        }
        return responseDTO;

    }

    @OperateLogger(content = "修改业务资金源",operationType = OperateLogger.OperationType.U,tables = "T_BUSITYPE_BANK")
    @RequestMapping("/saveBusiBank")
    @RequiresPermissions("busiBank_add")
    @ResponseBody
    public ResponseDTO <String> saveOrgBank(@RequestBody BusiBank[] busiBanks){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            for(BusiBank busiBank:busiBanks){
                busiBank.setEnableFlag("1");
                busiBank.setCreator(SessionUtils.getUserName());
                busiBankService.updateBusiBank(busiBank);
            }
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("业务资金源配置修改异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("业务资金源配置修改异常");
        }
        return responseDTO;
    }

}
