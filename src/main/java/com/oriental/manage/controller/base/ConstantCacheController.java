package com.oriental.manage.controller.base;

import com.oriental.check.service.facade.manager.ChkFileDataCfgFacade;
import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.enums.UserType;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.schedule.NotifyFlashCacheSchedule;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.ApplicationContextUtil;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.paycenter.commons.mode.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/5/6.
 */
@Slf4j
@Controller
@RequestMapping("base/cache")
public class ConstantCacheController {

    @Autowired
    private Constants constants;
    @Autowired
    private IDataDictService dataDictService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired(required = false)
    private ChkFileDataCfgFacade chkFileDataCfgFacade;
    @Autowired
    private NotifyFlashCacheSchedule notifyFlashCacheSchedule;


    private ResponseDTO<Map<String, String>> getOne(String cacheKey) {
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        try {
            CacheKey key = CacheKey.valueOf(cacheKey.toUpperCase());
            if (StringUtils.equals("t_data_dict", key.getScope())) {
                //数据字典部分
                if (Constants.getDataDictMap().containsKey(cacheKey)) {
                    map = Constants.getDataDictMap().get(cacheKey);
                } else {
                    DataDict dataDict = new DataDict();
                    dataDict.setItemName(key.getItemName());
                    dataDict.setColName(key.getColName());
                    List<DataDict> list = dataDictService.queryAll(dataDict);
                    if (list != null) {
                        for (DataDict dict : list) {
                            map.put(dict.getItemVal(), dict.getItemDesc());
                        }
                        Constants.getDataDictMap().put(cacheKey, map);
                    }
                }
            } else if (StringUtils.equals("t_data_dict_more", key.getScope())) {
                if (Constants.getDataDictMap().containsKey(cacheKey)) {
                    map = Constants.getDataDictMap().get(cacheKey);
                } else {
                    DataDict dataDict = new DataDict();
                    dataDict.setItemName(key.getItemName());
                    dataDict.setColName(key.getColName());
                    String[] temp = cacheKey.split("_");
                    dataDict.setItemType(temp[temp.length - 1]);
                    List<DataDict> list = dataDictService.queryAll(dataDict);
                    if (list != null) {
                        for (DataDict dict : list) {
                            map.put(dict.getItemVal(), dict.getItemDesc());
                        }
                        Constants.getDataDictMap().put(cacheKey, map);
                    }
                }
            } else {
                //其余缓存信息
                switch (key) {
                    case ROLE_INFO:
                        map = Constants.getRoleNameMap();
                        break;
                    case ROLE_INFO_02:
                        SecurityUtils.getSubject().checkRole(UserType.MERCHANT_USER.getCode());
                        map = Constants.getRoleNameTypeMap();
                        break;
                    case COMANY_CODE:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        map = Constants.getOgranizeNameMap();
                        break;
                    case MERCHANT_CODE:
                        map = Constants.getMerchantMap();
                        break;
                    case MERCHANT_ABBR_NAME:
                        map = Constants.getMerchantAbbrName();
                        break;
                    case CLEAR_POINT:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        map = Constants.getClearPointMap();
                        break;
                    case CLEAR_VALUE_POINT:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        map = Constants.getClearPointValueMap();
                        break;
                    case CLEAR_VALUE_POINT_WEEK:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        map = Constants.getClearPointValueWeekMap();
                        break;
                    case BANK_INFO:
                        map = Constants.getBankInfoMap();
                        break;
                    case ORG_BANK_CODE_DESC:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        map = Constants.getOrgBankCodeDesc();
                        break;
                    case FILE_TEMPLATE_NO:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        Response<Map<String, String>> response =
                                chkFileDataCfgFacade.selectTemplateNoAndName("manage_system");
                        if (response != null && response.isSuccess()) {
                            map = response.getResult();
                        }
                        break;
                    case ALL_COMPANY_CODE:
                        SecurityUtils.getSubject().checkRole(UserType.SYS_USER.getCode());
                        for (Map.Entry<String, String> entry : Constants.getOgranizeNameMap().entrySet()) {
                            map.put(entry.getKey(), entry.getValue());
                        }
                        for (Map.Entry<String, String> entry : Constants.getMerchantAbbrName().entrySet()) {
                            map.put(entry.getKey(), entry.getValue());
                        }
                        break;
                }
            }

            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("缓存获取失败,cacheKey:{}", cacheKey);
            log.error("缓存获取失败", e);
            map.put("", "未找到相关数据");
            responseDTO.setSuccess(false);
        } finally {
            responseDTO.setObject(map);
        }
        return responseDTO;
    }

    @RequestMapping("getAll")
    @ResponseBody
    public ResponseDTO queryAll(String cacheKey) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CacheKey key = CacheKey.valueOf(cacheKey);
            List list;
            switch (key) {
                case ALL_USER:
                    SecurityUtils.getSubject().checkPermission("menu_search");

                    list = userInfoService.queryAll();
                    break;
                case ALL_ROLE:
                    SecurityUtils.getSubject().checkPermission("sys-role_search");

                    Class clazz = Class.forName(key.getItemName());
                    Method method = clazz.getMethod(key.getColName());
                    list = (List) method.invoke(ApplicationContextUtil.getBean(clazz));
                    break;
                case ALL_CITY:
                    list = constants.getAllCity();
                    break;
                case MCC_CODE:
                    list = constants.getMccCode();
                    break;
                case ALL_AREA:
                    list = constants.getAllArea();
                    break;
                default:
                    list = new ArrayList();
            }
            responseDTO.setSuccess(true);
            responseDTO.setObject(list);
        } catch (Exception e) {
            log.error("获取数据失败", e);
            responseDTO.setSuccess(false);
            log.error("获取数据失败,key:{}", cacheKey);
        }

        return responseDTO;
    }

    @RequestMapping("get")
    @ResponseBody
    public ResponseDTO<Map<String, String>> getCache(String cacheKey) {
        return this.getOne(cacheKey);
    }

    @RequestMapping("getMore")
    @ResponseBody
    public ResponseDTO getMoreCache(@RequestBody String[] keys) {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Map<String, String>> moreMap = new HashMap<>();
        try {
            for (String key : keys) {
                ResponseDTO temp = this.getOne(key);
                moreMap.put(key, (Map<String, String>) temp.getObject());
            }
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("获取数据失败", e);
            responseDTO.setSuccess(false);
        } finally {
            responseDTO.setObject(moreMap);
        }
        return responseDTO;
    }

    @RequestMapping("reFlashCache")
    @ResponseBody
    public ResponseDTO reFlashCache() {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            constants.getDataDictMap().clear();
            constants.init();
            notifyFlashCacheSchedule.sendNotify("all");
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("缓存刷新失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }
}
