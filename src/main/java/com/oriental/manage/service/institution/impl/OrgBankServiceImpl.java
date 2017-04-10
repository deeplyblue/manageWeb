package com.oriental.manage.service.institution.impl;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.*;
import com.oriental.manage.dao.institution.OrgBankMapper;
import com.oriental.manage.pojo.business.MchntBank;
import com.oriental.manage.pojo.institution.OrgBank;
import com.oriental.manage.service.institution.IOrgBankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Service("orgBankService")
@Slf4j
public class OrgBankServiceImpl implements IOrgBankService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OrgBankMapper orgBankMapper;

    @Override
    @Transactional
    public boolean addOrgBank(OrgBank bean) {
        if (null != bean.getId()) {
            orgBankMapper.deleteOrgBank(bean);
        }
        if (bean.getChoose().equals("1")) {
            bean.setId(GenerateIdUtil.generateId());
            orgBankMapper.insertOrgBank(bean);
        }

        return true;
    }

    @Override
    public boolean updateOrgBank(OrgBank bean) {
        return addOrgBank(bean);
    }

    @Override
    public boolean updatePhoneCfg(OrgBank bean, Map<String, OrgBank> oldMap) throws Exception {
        String bankCode = bean.getBankCode();
        String[] bankCodes = StringCommonUtils.splitExtends(bankCode, ",");
        String[] payLvls = StringCommonUtils.splitExtends(bean.getPayLvl(), ",");
        String[] phoneLvls = StringCommonUtils.splitExtends(bean.getPhoneLvl(), ",");
        String[] isChangeds = StringCommonUtils.splitExtends(bean.getIsChanged(), ",");
        boolean res = true;
        boolean isChange = false;
        Map<String, OrgBank> newMap = new HashMap<String, OrgBank>();
        Map<Integer, String> indexMap = new HashMap<Integer, String>();
        OrgBank orgBank = new OrgBank();
        orgBank.setOrgCode(bean.getOrgCode());
        for (int i = 0; i < bankCodes.length; i++) {
            indexMap.put(i, bankCodes[i]);
        }
        if (isChangeds != null && isChangeds.length > 0) {
            for (int i = 0; i < isChangeds.length; i++) {
                if ("1".equals(isChangeds[i])) {
                    isChange = true;
                }
            }
        }
        log.info("机构银行配置修改 是否被修改isChange:{}", isChange);
        for (int i = 0; i < bankCodes.length; i++) {
            if (oldMap.containsKey(bankCodes[i]) && !newMap.containsKey(bankCodes[i])) {
                String[] iPayLvls = new String[payLvls.length];
                String[] iPhoneLvls = new String[phoneLvls.length];
                newMap.put(bankCodes[i], oldMap.get(bankCodes[i]));
                log.info("机构银行配置修改 银行代码bankCode:{}", bankCodes[i]);
                String payLvl = oldMap.get(bankCodes[i]).getPayLvl();
                String phoneLvl = oldMap.get(bankCodes[i]).getPhoneLvl();
                log.info("机构银行配置修改 payLvl:{},phoneLvl:{}", payLvl, phoneLvl);

                orgBank.setPayLvl(payLvl);
                int j = 0;
                if (indexMap.containsValue(bankCodes[i])) {
                    for (Map.Entry<Integer, String> entry : indexMap.entrySet()) {
                        String tempPay = payLvls[entry.getKey()];
                        if (tempPay.length() == 2 && "0".equals(tempPay.substring(0, 1))) {
                            continue;
                        } else if (tempPay.length() == 2 && "1".equals(tempPay.substring(0, 1))) {
                            tempPay = tempPay.substring(1);
                        }
                        if (entry.getValue().equals(bankCodes[i])) {
                            iPayLvls[j++] = tempPay;
                        }
                    }
                }
                for (String payLvl1 : iPayLvls) {
                    String tempPay = StringUtils.isEmpty(payLvl1) ? payLvl : payLvl1;
                    if (!StringUtils.equals(payLvl, tempPay)) {
                        orgBank.setPayLvl(tempPay);
                        break;
                    }
                }
                orgBank.setPhoneLvl(phoneLvl);
                int k = 0;
                if (indexMap.containsValue(bankCodes[i])) {
                    for (Map.Entry<Integer, String> entry : indexMap.entrySet()) {
                        String tempPhone = phoneLvls[entry.getKey()];
                        if (tempPhone.length() == 2 && "0".equals(tempPhone.substring(0, 1))) {
                            continue;
                        } else if (tempPhone.length() == 2 && "1".equals(tempPhone.substring(0, 1))) {
                            tempPhone = tempPhone.substring(1);
                        }
                        if (entry.getValue().equals(bankCodes[i])) {
                            iPhoneLvls[k++] = tempPhone;
                        }
                    }
                }
                for (String phoneLvl1 : iPhoneLvls) {
                    String tempPhone = StringUtils.isEmpty(phoneLvl1) ? phoneLvl : phoneLvl1;
                    if (!StringUtils.equals(phoneLvl, tempPhone)) {
                        orgBank.setPhoneLvl(tempPhone);
                        break;
                    }
                }
                orgBank.setBankCode(bankCodes[i]);
                ObjectUtils.printObjectValue(orgBank);
                if (isChange) {
                    res = orgBankMapper.editPhoneCfg(orgBank) == 1;
                }
            }
        }
        return res;
    }

    @Override
    public List<OrgBank> getOrgBankByBankCode(OrgBank bean) throws Exception {
        return orgBankMapper.getOrgBankByBankCode(bean);
    }

    @Override
    public List<OrgBank> getOrgBankByKey(OrgBank bean) throws Exception {
        return orgBankMapper.searchByKey(bean);
    }

    @Override
    public List<OrgBank> getOrgBankByMchntCode(MchntBank bean) throws Exception {
        return orgBankMapper.getOrgBankByMchntCode(bean);
    }

    @Override
    public List<OrgBank> getBankInfoByConnChannel(OrgBank bean)
            throws Exception {
        return orgBankMapper.getBankInfoByConnChannel(bean);
    }


    @Override
    public OrgBank getOrgBankByCodeAndType(OrgBank orgBank) throws Exception {

        return orgBankMapper.searchByParams(orgBank);
    }

    @Override
    public List<OrgBank> getBankInfoAndOrgCode(OrgBank bean) throws Exception {

        return orgBankMapper.getBankInfoAndOrgCode(bean);
    }

    @Override
    public Map<String, OrgBank> getOriginOrgBankConfig(String orgCode, String[] bankCodes) throws Exception {
        TreeSet<String> ts = new TreeSet<String>();
        String bankCodes2[];
        Map<String, OrgBank> map = new HashMap<String, OrgBank>();
        if (ObjectUtils.isNullOrEmpty(bankCodes)) {
            throw new RuntimeException("银行代码为空");
        } else {
            for (String bankCode : bankCodes) {
                ts.add(bankCode);
            }
            bankCodes2 = new String[ts.size()];
            for (int i = 0; i < bankCodes2.length; i++) {
                bankCodes2[i] = ts.pollFirst();
            }
        }

        if (StringUtils.isEmpty(orgCode)) {
            throw new RuntimeException("机构代码为空");
        }
        OrgBank orgBank = new OrgBank();
        orgBank.setOrgCode(orgCode);
        for (String bankCode : bankCodes2) {
            orgBank.setBankCode(bankCode);
            List<OrgBank> orgBankList = orgBankMapper.searchByKey(orgBank);
            if (orgBankList != null && orgBankList.size() > 0) {
                OrgBank tBank = new OrgBank();
                tBank.setPayLvl(orgBankList.get(0).getPayLvl());
                tBank.setPhoneLvl(orgBankList.get(0).getPhoneLvl());
                map.put(bankCode, tBank);
            }
        }
        return map;
    }

    @Override
    public void updateEnableFlag(ResponseDTO<String> responseDTO, OrgBank orgBank) {
        if(orgBankMapper.auditOrgBank(orgBank)>0){
            responseDTO.setSuccess(true);
        }else{
            responseDTO.setSuccess(false);
        }

    }

    @Override
    public List<OrgBank> getAll(){
        return orgBankMapper.getAllInfo();
    }

    @Override
    public List<OrgBank> getBankInfoSelective(OrgBank orgBank) {
        return orgBankMapper.getRecordByBank(orgBank);

    }
}
