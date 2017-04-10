package com.oriental.manage.core.system;

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.enums.RedisKey;
import com.oriental.manage.pojo.bank.BankInfo;
import com.oriental.manage.pojo.bank.BankTypeRelation;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.pojo.base.RoleInfo;
import com.oriental.manage.pojo.institution.OrgBank;
import com.oriental.manage.pojo.institution.OrganizeInfo;
import com.oriental.manage.pojo.merchant.baseinfo.MerchantInfo;
import com.oriental.manage.pojo.merchant.itconfig.ClearPoint;
import com.oriental.manage.service.bank.IBankInfoService;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.manage.service.base.IGovAreaService;
import com.oriental.manage.service.base.IMccCodeService;
import com.oriental.manage.service.base.IRoleInfoService;
import com.oriental.manage.service.institution.IOrgBankService;
import com.oriental.manage.service.institution.IOrganizeInfoService;
import com.oriental.manage.service.merchant.IBankTypeRelationService;
import com.oriental.manage.service.merchant.baseinfo.IMerchantInfoService;
import com.oriental.manage.service.merchant.itconfig.IMerchantClearPointService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.webbuilder.utils.file.Resources;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * Created by lupf on 2016/5/10.
 */
@Slf4j
@Repository("Constants")
@Scope
public class Constants implements ApplicationListener {

    @Autowired
    private IRoleInfoService roleInfoService;
    @Autowired
    private IOrganizeInfoService organizeInfoService;

    @Autowired
    private IMerchantInfoService merchantService;
    @Autowired
    IBankTypeRelationService bankTypeRelationService;
    @Autowired
    IOrgBankService orgBankService;
    @Autowired
    private IMerchantClearPointService merchantClearPointService;

    @Autowired
    private IBankInfoService bankInfoService;

    @Autowired
    private ConstantsRedis constantsRedis;

    @Autowired
    private IDataDictService dataDictService;

    @Autowired
    private IGovAreaService govAreaService;

    @Autowired
    private IMccCodeService mccCodeService;

    @Value("#{cfgProperties['USER_PASSWORD_CRYPT_KEY']}")
    @Getter
    @Setter
    private String USER_PASSWORD_CRYPT_KEY;

    @Value("#{cfgProperties['PAY_TRANS_CRYPT_KEY']}")
    @Getter
    @Setter
    public String PAY_TRANS_CRYPT_KEY;

    //    @Value("#{cfgProperties['excelPath']}")
    @Getter
    @Setter
    private String excelPath;
    /**
     * 启动是个线程池
     **/
    public static final ThreadPoolManager THREAD_POOL = new ThreadPoolManager(Executors.newFixedThreadPool(40));
    @Getter
    private static final ConcurrentHashMap<String, Map<String, String>> dataDictMap
            = new ConcurrentHashMap<String, Map<String, String>>();
    @Getter
    private static final ConcurrentHashMap<String, String> roleNameMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> roleNameTypeMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> ogranizeNameMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> merchantMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> bankInfoMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> merchantAbbrName = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> clearPointMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> clearPointValueMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> clearPointValueWeekMap = new ConcurrentHashMap<String, String>();
    @Getter
    private static final ConcurrentHashMap<String, String> orgBankCodeDesc = new ConcurrentHashMap<String, String>();

    @Getter
    private static final ConcurrentHashMap<String, String> bankCodeRule = new ConcurrentHashMap<String, String>();
    @Getter
    private static final List<Map<String, Object>> allCity = new ArrayList<>();
    @Getter
    private static final List<Map<String, String>> allArea = new ArrayList<>();
    @Getter
    private static final List mccCode = new ArrayList();
    @Getter
    private static final List<BankTypeRelation> bankTypeRelationList = new ArrayList<BankTypeRelation>();


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }

    @PostConstruct
    public void init() throws IOException {
        log.info("-------constants init start-------");

        try {
            this.excelPath = Resources.getResourceAsFile("/").getParent() + "/template/";
            System.out.println(this.excelPath);
        } catch (IOException e) {
            log.error("初始化模板路径失败", e);
            throw e;
        }
        /**
         * warning:每个初始化方法，必须先清空其缓存
         */
        initRoleInfo();
        initOrganizeInfo();
        initMerchantInfo();
        initClearPoint();
        initBankCodeInfo();
        initBankInfo();
        initOrgBankCodeInfo();
        getRoleInfo();

        initCityArea();

        initDataDict();
        log.info("-------constants init end-------");

    }

    private synchronized void initCityArea() {
        try {
            allCity.clear();
            allCity.addAll(govAreaService.selectAllArea());

            allArea.clear();
            allArea.addAll(govAreaService.selectArea());
        } catch (Exception e) {
            log.error("省市代码初始化失败", e);
        }
    }

    private synchronized void initBankCodeInfo() {
        try {
            List<BankTypeRelation> bankTypeRelationListTemp = bankTypeRelationService.getAll();
            if (bankTypeRelationListTemp != null && bankTypeRelationListTemp.size() > 0) {
                bankTypeRelationList.clear();
                bankCodeRule.clear();
                BankTypeRelation btr = null;
                for (int i = 0; i < bankTypeRelationListTemp.size(); i++) {
                    btr = bankTypeRelationListTemp.get(i);
                    bankTypeRelationList.add(btr);
                    bankCodeRule.put(btr.getBankCode(), btr.getBankCodeSuffix());
                }
            }
        } catch (Exception e) {
            log.error("初始化银行代码失败", e);
        }
    }

    private void initOrgBankCodeInfo() {
        try {
            List<OrgBank> orgBankList = orgBankService.getAll();
            if (orgBankList != null && orgBankList.size() > 0) {
                orgBankCodeDesc.clear();
                OrgBank orgBank;
                for (int i = 0; i < orgBankList.size(); i++) {
                    orgBank = orgBankList.get(i);
                    if (StringUtils.isBlank(orgBank.getBankCode())) continue;
                    orgBankCodeDesc.put(orgBank.getBankCode(), orgBank.getOrgBankCodeDesc());
                }
                constantsRedis.init(RedisKey.BANK_NAME, orgBankCodeDesc);
            }
        } catch (Exception e) {
            log.error("初始化机构银行代码失败", e);
        }
    }

    public void initRoleInfo() {
        try {
            List<RoleInfo> list = roleInfoService.getAllRole();
            if (list != null && list.size() > 0) {
                roleNameMap.clear();
                for (RoleInfo roleInfo : list) {
                    roleNameMap.put(roleInfo.getRoleId(), roleInfo.getRoleName());
                }
            }
        } catch (Exception e) {
            log.error("--------初始化角色信息失败!--------", e);
        }
    }

    public void getRoleInfo() {
        try {
            List<RoleInfo> list = roleInfoService.getRoleByType("02");
            if (list != null && list.size() > 0) {
                roleNameTypeMap.clear();
                for (RoleInfo roleInfo : list) {
                    roleNameTypeMap.put(roleInfo.getRoleId(), roleInfo.getRoleName());
                }
            }
        } catch (Exception e) {
            log.error("--------初始化商户侧角色信息失败!--------", e);
        }
    }

    public void initOrganizeInfo() {
        try {
            List<OrganizeInfo> list = organizeInfoService.getCompnayCode();
            Map<String, String> settle = new HashMap<>();
            if (list != null && list.size() > 0) {
                ogranizeNameMap.clear();
                for (OrganizeInfo organizeinfo : list) {
                    ogranizeNameMap.put(organizeinfo.getOrgCode(), organizeinfo.getOrgName());
                    if (organizeinfo.getPayBankCode() == null) {
                        organizeinfo.setPayBankCode("");
                    }
                    settle.put(organizeinfo.getOrgCode(), organizeinfo.getPayBankCode());
                }
                //刷新redis缓存
                constantsRedis.init(RedisKey.PAY_BANK_CODE, settle);
                constantsRedis.init(RedisKey.ORGANIZE_NAME, ogranizeNameMap);
            }
        } catch (Exception e) {
            log.error("--------初始化支付机构失败!--------", e);
        }

    }

    public void initMerchantInfo() {
        try {
            List<MerchantInfo> list = merchantService.getMerchantInfo();
            if (list != null && list.size() > 0) {
                merchantAbbrName.clear();
                merchantMap.clear();
                for (MerchantInfo merchantInfo : list) {
                    merchantAbbrName.put(merchantInfo.getMerchantCode(), merchantInfo.getMerchantAbbrName());
                    merchantMap.put(merchantInfo.getMerchantCode(), merchantInfo.getMerchantName());
                }
                constantsRedis.init(RedisKey.MERCHANT_NAME, merchantMap);
                constantsRedis.init(RedisKey.MERCHANT_ABBR_NAME, merchantAbbrName);
            }
        } catch (Exception e) {
            log.error("--------初始化商户信息失败!--------", e);
        }

    }

    private void initClearPoint() {
        try {
            List<ClearPoint> clearPointList = merchantClearPointService.getAllClearPoint();
            if (clearPointList != null && clearPointList.size() > 0) {
                clearPointMap.clear();
                clearPointValueMap.clear();
                for (ClearPoint clearPoint : clearPointList) {

                    if (clearPoint.getItemKey().equals("06")) {
                        clearPointMap.put(clearPoint.getItemDesc(), clearPoint.getItemKey());
                        clearPointValueMap.put(clearPoint.getItemDesc(), clearPoint.getItemValue());
                    }
                    if (clearPoint.getItemKey().equals("05")) {
                        clearPointMap.put(clearPoint.getItemDesc(), clearPoint.getItemKey());
                        clearPointValueWeekMap.put(clearPoint.getItemDesc(), clearPoint.getItemValue());
                    }
                }
            }
        } catch (Exception e) {
            log.error("--------初始化商户信息失败!--------", e);
        }
    }

    public void initBankInfo() {
        try {
            List<BankInfo> bankInfoList = bankInfoService.getAllBankInfo();
            if (bankInfoList != null && bankInfoList.size() > 0) {
                bankInfoMap.clear();
                for (BankInfo bankInfo : bankInfoList) {
                    bankInfoMap.put(bankInfo.getBankCode(), bankInfo.getBankName());
                }
            }
        } catch (Exception e) {
            log.error("---------初始化银行名称失败！--------", e);
        }
    }

    public synchronized void initDataDict() {

        try {
            CacheKey[] keys = CacheKey.values();
            DataDict dataDict = new DataDict();
            for (CacheKey key : keys) {

                if (StringUtils.equals(key.getScope(), "t_data_dict")) {

                    Map<String, String> map = new HashMap();

                    dataDict.setItemName(key.getItemName());
                    dataDict.setColName(key.getColName());
                    List<DataDict> list = dataDictService.queryAll(dataDict);
                    if (list != null) {
                        for (DataDict dict : list) {
                            map.put(dict.getItemVal(), dict.getItemDesc());
                        }
                        Constants.dataDictMap.put(key.name().toLowerCase(), map);
                    }
                }

            }

            /**
             * 数据字典中（-----非通用方式-----）初始化的值
             * CacheKey.MCC_CODE
             */

            mccCode.clear();
            mccCode.addAll(mccCodeService.selectAllMccCode());
        } catch (Exception e) {
            log.error("数据字典初始化异常", e);
        }
    }
}
