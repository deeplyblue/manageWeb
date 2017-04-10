package com.oriental.manage.controller.merchant.settleManage;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.fileUtils.FileUtilsExt;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.pojo.merchant.ContractInfo;
import com.oriental.manage.service.base.IDfsFileInfoService;
import com.oriental.manage.service.merchant.IContractService;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by wangjun on 2016/6/8.
 * 商户合同信息
 *
 */
@Slf4j
@Controller
@RequestMapping("merchant/merchantContaract")
public class MerchantContranctController {

    private List<String> status = new ArrayList<String>(){{add("02");add("04");add("05");}};

    @Autowired
    private IContractService contractService;

    @Autowired
    private IDfsFileInfoService iDfsFileInfoService;

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @Value("#{cfgProperties['downloadTempDir']}")
    private String downloadTempDir;

    @RequestMapping("init")
    @RequiresPermissions("merchant-contaract_search")
    public String init(){return "merchant/settleManage/searchContract";}

    @RequestMapping("toAdd")
    public String toAdd(){return "merchant/settleManage/addContract";}

    @RequestMapping("toUpdate")
    public String toUpdate(){return "merchant/settleManage/updateContract";}


    @OperateLogger(content = "查询合同信息",operationType = OperateLogger.OperationType.R)
    @RequestMapping("search")
    @RequiresPermissions("merchant-contaract_search")
    @ResponseBody
    public ResponseDTO<Pagination<ContractInfo>> queryPage (ContractInfo contractInfo, Pagination<ContractInfo> pagination){

        ResponseDTO<Pagination<ContractInfo>> responseDTO =new ResponseDTO<Pagination<ContractInfo>>();
        try{

            contractInfo.setCompanyType(CompanyType.MERCHANT.getCode());
            contractService.queryPage(pagination,contractInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);

        }catch (Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "新增合同信息",operationType = OperateLogger.OperationType.C,tables = "T_CONTRACT_INFO")
    @RequestMapping("add")
    @RequiresPermissions("merchant-contaract_add")
    @ResponseBody
    public ResponseDTO<String> add(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if(this.check(contractInfo.getCompanyCode(),contractInfo.getContCode())){
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该合同配置已经存在");
            }else{
            contractInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(5)));
            contractInfo.setCompanyType(CompanyType.MERCHANT.getCode());
            contractService.addContract(responseDTO,contractInfo);
            }
        } catch (Exception e) {
            log.error("新增失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "修改合同信息",operationType = OperateLogger.OperationType.U,tables = "T_CONTRACT_INFO")
    @RequestMapping("update")
    @RequiresPermissions("merchant-contaract_update")
    @ResponseBody
    public ResponseDTO<String> update(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if (!status.contains(contractInfo.getAuditStatus())){
                contractInfo.setAuditStatus("01");
            }
            contractInfo.setCompanyType(CompanyType.MERCHANT.getCode());
            contractService.updateContract(responseDTO,contractInfo);
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @OperateLogger(content = "删除合同信息",operationType = OperateLogger.OperationType.D,tables = "T_CONTRACT_INFO")
    @RequestMapping("delete")
    @RequiresPermissions("merchant-contaract_delete")
    @ResponseBody
    public ResponseDTO<String> delete(ContractInfo contractInfo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractInfo.setCompanyType(CompanyType.MERCHANT.getCode());
            contractService.deleteContract(responseDTO,contractInfo);
        } catch (Exception e) {
            log.error("删除失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/updateItemEnableFlag")
    @RequiresPermissions(value = {"merchant-contaract_audit","merchant-contaract_fail","merchant-contaract_on","merchant-contaract_off"}, logical = Logical.OR)
    public ResponseDTO<String> updateItemEnableFlag(@RequestBody ContractInfo contractInfo){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            contractService.updateContract(responseDTO,contractInfo);
        }catch (Exception e){
            log.error("修改状态错误：",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统错误");
        }
        return responseDTO;
    }

    @RequestMapping("/downEnclosure")
    @RequiresPermissions("org-contaract_down")
    public ResponseEntity<byte[]> downEnclosure(String id){

        try{
            ContractInfo contractInfo = contractService.queryById(id);
            String path = downloadTempDir.concat("/").concat(contractInfo.getCompanyCode()).concat("/");
            FileUtilsExt.writeFile(path);
            String [] pathList = {contractInfo.getDfsBankFile(),contractInfo.getDfsBizLicenseCert(),
                    contractInfo.getDfsContAttach(),contractInfo.getDfsOpenBankCert()
                    ,contractInfo.getDfsOrganizationCodeCert(),contractInfo.getDfsRatePayerCert(),
                    contractInfo.getDfsTaxRegisterCert()};
            for (String dfsPath : pathList){
                if (StringUtils.isNotBlank(dfsPath)){
                    DfsFileInfo dfsFileInfo = new DfsFileInfo();
                    dfsFileInfo.setDfsFullFilename(dfsPath);
                    List<DfsFileInfo> fileInfoList = iDfsFileInfoService.searchDfsFileInfo(dfsFileInfo);
                    if (null != fileInfoList && fileInfoList.size()>0){
                        DfsFileInfo dfsFileInfo1 = fileInfoList.get(0);
                        fastDFSPoolUtil.download(dfsFileInfo1.getDfsGroupname(),dfsFileInfo1.getDfsFullFilename(),path.concat(dfsFileInfo1.getLocalFilename()));
                    }
                }
            }
            String localFileName = downloadTempDir.concat("/").concat(contractInfo.getCompanyCode()).concat("合同信息.zip");
            FileUtilsExt.zipFile(Arrays.asList(new File(path).listFiles()),localFileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(contractInfo.getCompanyCode().concat("合同信息.zip").getBytes("UTF-8"),"ISO-8859-1"));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(localFileName)), headers, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("下载失败：",e);
        }
        return null;
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
