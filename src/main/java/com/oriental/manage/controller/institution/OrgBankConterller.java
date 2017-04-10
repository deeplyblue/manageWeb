package com.oriental.manage.controller.institution;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.BankCodeUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.pojo.institution.OrgBank;
import com.oriental.manage.service.bank.IBankInfoService;
import com.oriental.manage.service.institution.IOrBankService;
import com.oriental.manage.service.institution.IOrgBankService;
import com.oriental.manage.service.institution.IPayChanStrategyCfgService;
import com.oriental.paycenter.commons.utils.BeanCopier;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjun on 2016/5/9.
 */
@Slf4j
@Controller
@RequestMapping("institution/orgbank")
public class OrgBankConterller {

    @Autowired
    private IOrBankService orBankService;

    @Autowired
    private IBankInfoService bankInfoService;

    @Autowired
    private IOrgBankService orgBankService;

    @Autowired
    private IPayChanStrategyCfgService payChanStrategyCfgService;

    @Getter
    @Setter
    private List<OrgBank> orgBankList;

    @RequestMapping("init")
    public String init() {
        return "institution/searchOrgBank";
    }

    @RequestMapping("getOrgBankInfoForPayChanExtend")
    public String getOrgBankInfoForPayChanExtend() {
        return "institution/searchPayChanStrategyCfg";
    }

    @OperateLogger(content = "查询机构银行", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("orgbank_select")
    @ResponseBody
    public ResponseDTO<Pagination<OrgBank>> queryPage(Pagination<OrgBank> pagination, OrgBank orgBank) {
        ResponseDTO<Pagination<OrgBank>> responseDTO = new ResponseDTO<Pagination<OrgBank>>();
        try {
            orBankService.queryPage(pagination, orgBank);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "查询支付策略", operationType = OperateLogger.OperationType.R, tables = "T_ORG_BANK")
    @RequestMapping("queryOrgBankInfoForPayChanExtendPage")
    @RequiresPermissions("payChanStrategyCfg_select")
    @ResponseBody
    public ResponseDTO<Pagination<OrgBank>> queryOrgBankInfoForPayChanExtendPage(Pagination<OrgBank> pagination, OrgBank orgBank) {
        ResponseDTO<Pagination<OrgBank>> responseDTO = new ResponseDTO<Pagination<OrgBank>>();
        try {
            orBankService.queryPageByStrategy(pagination, orgBank);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toAdd")
    @RequiresPermissions("orgbank_add")
    public String add() {
        return "institution/updateOrgBank";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("orgbank_update")
    public String toUpdate() {
        return "institution/updateOrgBank";
    }


    @RequestMapping("/getOrgBankInfo")
    @ResponseBody
    public ResponseDTO<List<OrgBank>> getBankInfoByBankTypeAjax(BankInfo bean) {
        ResponseDTO<List<OrgBank>> responseDTO = new ResponseDTO<List<OrgBank>>();
        try {
            List<BankInfo> bankInfoLists = bankInfoService.getAllBankInfo();
            Map<String, List<BankInfo>> bankInfoMap = BankCodeUtils.getBankMap(bankInfoLists);
            List<BankInfo> banks = BankCodeUtils.getBankInfoByBankType(bankInfoMap, bean.getBankType());
            List<OrgBank> list = new ArrayList<OrgBank>();
            if (banks != null && banks.size() > 0) {
                Map<String, OrgBank> orgBankMap = new HashMap<String, OrgBank>();
                OrgBank orgBank = new OrgBank();
                orgBank.setOrgCode(bean.getCompanyCode());
                List<OrgBank> orgBankList = orgBankService.getOrgBankByKey(orgBank);
                for (OrgBank ob : orgBankList) {
                    orgBank = new OrgBank();
                    BeanCopier.copy(ob, orgBank);
                    orgBankMap.put(ob.getBankType() + "#" + ob.getBankCode(), orgBank);
                }
                OrgBank ob;
                for (int i = 0; i < banks.size(); i++) {
                    BankInfo bankInfo = banks.get(i);
                    String bankCode = bankInfo.getBankCode();
                    if (orgBankMap.keySet().contains(bean.getBankType() + "#" + bankCode)) {
                        ob = new OrgBank();
                        BeanCopier.copy(orgBankMap.get(bean.getBankType() + "#" + bankCode), ob);
                        ob.setBankName(bankInfo.getBankName());
                        ob.setBankCode(bankCode);
                        ob.setChoose("1");
                    } else {
                        ob = new OrgBank();
                        ob.setBankName(bankInfo.getBankName());
                        ob.setBankCode(bankCode);
                        ob.setOrgCode(bean.getCompanyCode());
                        ob.setBankType(bean.getBankType());
                        ob.setRefundOlFlag("1");
                        ob.setChoose("0");
                        ob.setEnableFlag("1");
                        ob.setPayLvl("0");
                        ob.setPhoneLvl("0");
                    }
                    list.add(ob);
                }
                responseDTO.setSuccess(true);
                responseDTO.setObject(list);
            }
        } catch (Exception e) {
            log.error("查询机构银行异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("程序异常");
        }
        return responseDTO;
    }

    @RequestMapping("/queryOrgBankInfo")
    @ResponseBody
    public ResponseDTO<OrgBank> queryOrgBankInfo(OrgBank bean) {
        ResponseDTO<OrgBank> responseDTO = new ResponseDTO<OrgBank>();
        try {
            OrgBank orgBank = new OrgBank();
            orgBank.setOrgCode(bean.getOrgCode());
            List<OrgBank> orgBankList = orgBankService.getOrgBankByKey(orgBank);
            if (orgBankList != null && orgBankList.size() > 0) {
                responseDTO.setObject(orgBankList.get(0));
                responseDTO.setSuccess(true);
            } else {
                responseDTO.setObject(orgBank);
            }

        } catch (Exception e) {
            log.error("访问异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("访问异常");
        }
        return responseDTO;
    }

    @RequestMapping("/saveOrgBank")
    @RequiresPermissions(value = {"orgbank_update", "orgbank_add"}, logical = Logical.OR)
    @ResponseBody
    public ResponseDTO<String> saveOrgBank(@RequestBody OrgBank[] orgBanks) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        for (OrgBank orgBank : orgBanks) {
            orgBank.setCreator(SessionUtils.getUserName());
            orgBankService.updateOrgBank(orgBank);
        }
        responseDTO.setSuccess(true);
        return responseDTO;
    }

    @RequestMapping("updateEnableFlag")
    @RequiresPermissions("orgbank_updateEnableFlag")
    @ResponseBody
    public ResponseDTO<String> updateEnableFlag(OrgBank orgBank) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            orgBank.setModifier(SessionUtils.getUserName());
            orgBankService.updateEnableFlag(responseDTO, orgBank);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("操作失败");
        }

        return responseDTO;
    }


    /**
     * 新增支付策略一级页面
     **/
    @RequestMapping("/toAddForPay")
    public String toAddForPay() {
        return "institution/addPayChanStrategyCfgPre";
    }

    @RequestMapping("/queryOrgBankByBank")
    @RequiresPermissions("pay-strategy_update")
    @ResponseBody
    public ResponseDTO queryOrgBankByBank(@RequestBody OrgBank orgBank) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrgBank record = new OrgBank();
            record.setBankCode(orgBank.getBankCode());
            record.setConnChannel(orgBank.getConnChannel());
            record.setTransCode(orgBank.getTransCode());
            List<OrgBank> list = orgBankService.getBankInfoSelective(record);
            responseDTO.setSuccess(true);
            responseDTO.setObject(list);
        } catch (Exception e) {
            log.error("查询银行对应支付机构", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("/queryOrgBank")
    @ResponseBody
    public ResponseDTO queryOrgBank(@RequestBody OrgBank orgBank) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrgBank record = new OrgBank();
            record.setBankCode(orgBank.getBankCode());
            record.setEnableFlag("1");
            List<OrgBank> list = orgBankService.getOrgBankByKey(record);
            responseDTO.setSuccess(true);
            responseDTO.setObject(list);
        } catch (Exception e) {
            log.error("查询银行对应支付机构", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    /**
     * 新增支付策略二级页面
     **/
    @RequestMapping("/toAddForPayTwo")
    public String addForPayTwo() {
        return "institution/addPayChanStrategyCfg";
    }

    /**
     * 修改支付策略
     **/
    @RequestMapping("/toUpdateForPay")
    public String toUpdateForPay() {
        return "institution/updatePayChanStrategyCfg";
    }

}
