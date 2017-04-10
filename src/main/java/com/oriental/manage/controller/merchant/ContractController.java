package com.oriental.manage.controller.merchant;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.pojo.merchant.ContractInfo;
import com.oriental.manage.service.base.IDfsFileInfoService;
import com.oriental.manage.service.merchant.IContractService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by wangjun on 2016/5/16.
 * 查询支付机构合同信息
 */
@Slf4j
@Controller
@RequestMapping("merchant/contaract")
public class ContractController {

    @Autowired
    private IContractService contractService;

    @Autowired
    private IDfsFileInfoService dfsFileInfoService;

    @Value("#{cfgProperties['downloadTempDir']}")
    private String downloadTempDir;

    @Autowired
    private IDfsFileInfoService iDfsFileInfoService;

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @RequestMapping("init")
    public String init() {

        return "merchant/searchContract";
    }

    @OperateLogger(content = "查询合同信息", operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("org-contaract_select")
    @ResponseBody
    public ResponseDTO<Pagination<ContractInfo>> queryPage(ContractInfo contractInfo, Pagination<ContractInfo> pagination) {

        ResponseDTO<Pagination<ContractInfo>> responseDTO = new ResponseDTO<Pagination<ContractInfo>>();
        try {
            contractInfo.setCompanyType(CompanyType.PAY.getCode());
            contractService.queryPage(pagination, contractInfo);
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
    @RequiresPermissions("org-contaract_add")
    public String toAdd() {
        return "merchant/addContract";
    }

    @OperateLogger(content = "新增合同信息", operationType = OperateLogger.OperationType.C, tables = "T_CONTRACT_INFO")
    @RequestMapping("add")
    @RequiresPermissions("org-contaract_add")
    @ResponseBody
    public ResponseDTO<String> add(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if(this.check(contractInfo.getCompanyCode(),contractInfo.getContCode())){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该合同配置已经存在");
            }else{
                contractInfo.setModifier(SessionUtils.getUserName());
            contractInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            contractInfo.setCompanyType(CompanyType.PAY.getCode());
            contractService.addContract(responseDTO, contractInfo);

            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("toUpdate")
    @RequiresPermissions("org-contaract_update")
    public String toUpdate() {
        return "merchant/updateContract";
    }

    @OperateLogger(content = "修改合同信息", operationType = OperateLogger.OperationType.U, tables = "T_CONTRACT_INFO")
    @RequestMapping("update")
    @RequiresPermissions("org-contaract_update")
    @ResponseBody
    public ResponseDTO<String> update(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.updateContract(responseDTO, contractInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "删除合同信息", operationType = OperateLogger.OperationType.D, tables = "T_CONTRACT_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("org-contaract_delete")
    @ResponseBody
    public ResponseDTO<String> delete(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.deleteContract(responseDTO, contractInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "加载下载文件信息", operationType = OperateLogger.OperationType.R)
    @RequestMapping("getDfs")
    @ResponseBody
    public ResponseDTO<DfsFileInfo> getDfs(String id) {
        ResponseDTO<DfsFileInfo> responseDTO = new ResponseDTO<DfsFileInfo>();
        try {
            DfsFileInfo dfsFileInfo = new DfsFileInfo();
            dfsFileInfo.setDfsFullFilename(id);
            List<DfsFileInfo> dfs = dfsFileInfoService.searchDfsFileInfo(dfsFileInfo);
            responseDTO.setObject(dfs.get(0));
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("文件不存在", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }

        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/updateItemEnableFlag")
    @RequiresPermissions(value = {"org-contaract_audit", "org-contaract_fail", "org-contaract_on", "org-contaract_off", "org-contaract_down"}, logical = Logical.OR)
    public ResponseDTO<String> updateItemEnableFlag(@RequestBody ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.updateContract(responseDTO, contractInfo);
        } catch (Exception e) {
            log.error("修改状态错误：", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统错误");
        }
        return responseDTO;
    }

    @RequestMapping("check")
    @ResponseBody
    public boolean check(String companyCode,String contCode){
        ContractInfo contractInfo=new ContractInfo();
        if(companyCode!=null && !"".equals(companyCode)) {
            contractInfo.setCompanyCode(companyCode);
        }
        if(contCode!=null && !"".equals(contCode)) {
            contractInfo.setContCode(contCode);
        }
         return contractService.check(contractInfo);
    }

}
