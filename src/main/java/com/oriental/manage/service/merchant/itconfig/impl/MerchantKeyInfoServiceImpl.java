package com.oriental.manage.service.merchant.itconfig.impl;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.enums.CompanyType;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.EncryptionTools;
import com.oriental.manage.core.utils.RSAUtil;
import com.oriental.manage.dao.merchant.baseinfo.MerchantInfoMapper;
import com.oriental.manage.dao.merchant.itconfig.MerchantKeyInfoMapper;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.pojo.merchant.itconfig.KeyInfo;
import com.oriental.manage.pojo.merchant.itconfig.MerchantInterface;
import com.oriental.manage.service.base.IDfsFileInfoService;
import com.oriental.manage.service.merchant.itconfig.IMerchantKeyInfoService;
import com.oriental.paycenter.commons.utils.ConvertUtil;
import com.oriental.paycenter.commons.utils.CryptTool;
import com.oriental.paycenter.commons.utils.DateUtil;
import com.oriental.paycenter.commons.utils.RandomMath;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/5/30
 * Time: 19:14
 * Desc：商户密钥配置
 */
@Slf4j
@Service
public class MerchantKeyInfoServiceImpl implements IMerchantKeyInfoService {

    @Autowired
    private MerchantKeyInfoMapper merchantKeyInfoMapper;
    @Autowired
    private IDfsFileInfoService dfsFileInfoService;
    @Value("#{cfgProperties['downloadTempDir']}")
    @Setter
    private String downloadTempDir;
    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @Override
    public void search(Pagination<KeyInfo> pagination, KeyInfo keyInfo) {
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        List<KeyInfo> keyInfoList = merchantKeyInfoMapper.queryMerchantKeyInfo(keyInfo);
        if (keyInfoList != null && keyInfoList.size() > 0) {
            for(int i=0;i<keyInfoList.size();i++){
                keyInfoList.get(i).setDataKey(null);
                keyInfoList.get(i).setTransPwd(null);
            }
            pagination.setRowCount(keyInfoList.get(0).getRowCount());
            pagination.setList(keyInfoList);
        }
    }

    @Override
    public void update(KeyInfo keyInfo, ResponseDTO<String> responseDTO) {
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        if(keyInfo.getRsaRemoteAddr()!=null){
            try {
                initRsaKey(keyInfo);
            } catch (Exception e) {
                log.error("商户rsaKey处理失败", e);
            }
        }
        int count = merchantKeyInfoMapper.updateMerchantKeyInfo(keyInfo);

        if (count > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public void add(KeyInfo keyInfo, ResponseDTO<String> responseDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        List<KeyInfo> baseKey = merchantKeyInfoMapper.queryMerchantKeyInfo(buildKeyInfo());
        if (null == baseKey || baseKey.size() != 1){
            log.error("默认商户查询失败");
            throw new BusiException("默认商户查询失败");
        }
        initDataKey(keyInfo,baseKey);
        initRsaKey(keyInfo);
        keyInfo.setId(DateUtil.getCurrent().concat(RandomMath.getNum(8)));
        keyInfo.setDataKeyStatus("00");
        keyInfo.setRsaKeyStatus("00");
        keyInfo.setTransPwdStatus("00");
        int count = merchantKeyInfoMapper.createMerchantKeyInfo(keyInfo);
        if (count > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    @Override
    public KeyInfo searchDataKey( KeyInfo keyInfo) {
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        return merchantKeyInfoMapper.queryDataKey(keyInfo);
    }

    @Override
    public KeyInfo searchRsaKey(KeyInfo keyInfo) {
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        return merchantKeyInfoMapper.queryRsaKey(keyInfo);
    }

    private void initDataKey(KeyInfo keyInfo,List<KeyInfo> baseKey) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        int keySize = 24;
        String keyStr = String.valueOf(System.currentTimeMillis()).concat(keyInfo.getCompanyCode());
        if (keySize < keyStr.length()) {
            keyStr = StringUtils.left(keyStr, keySize);
        }
        if (keySize > keyStr.length()) {
            keyStr = StringUtils.rightPad(keyStr, keySize, " ");
        }


        byte [] baseKeyByte = EncryptionTools.hexStringToBytes(baseKey.get(0).getDataKey());
        String merchantDataKey = EncryptionTools.bytesToHexString(EncryptionTools.des3ECB(baseKeyByte,keyStr));
        keyInfo.setDataKey(merchantDataKey);
    }

    private void initRsaKey(KeyInfo keyInfo) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        RSAUtil rsaUtil = new RSAUtil();
        try {
            DfsFileInfo dfsFileInfo = new DfsFileInfo();
            dfsFileInfo.setDfsFullFilename(keyInfo.getRsaRemoteAddr());
            List<DfsFileInfo> dfs = dfsFileInfoService.searchDfsFileInfo(dfsFileInfo);
            dfsFileInfo = dfs.get(0);
            File file = new File(downloadTempDir + dfsFileInfo.getLocalFilename());

                int i = fastDFSPoolUtil.download(dfsFileInfo.getDfsGroupname(), dfsFileInfo.getDfsFullFilename(),
                        downloadTempDir + dfsFileInfo.getLocalFilename());
                if (i == -1) {
                    throw new BusiException("file not found!");
                }
            InputStream in = new FileInputStream(file);
            String publiceKey = RSAUtil.readCerficate(in);
            keyInfo.setRsaPublicKey(publiceKey);
            in.close();
            } catch (Exception e) {
            log.error("读取文件错误", e);
        }
        }

    private KeyInfo buildKeyInfo(){
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.setCompanyCode("888888888888888");
        keyInfo.setCompanyType(CompanyType.MERCHANT.getCode());
        return keyInfo;
    }

}