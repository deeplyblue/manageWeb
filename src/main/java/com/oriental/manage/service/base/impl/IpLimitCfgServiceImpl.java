package com.oriental.manage.service.base.impl;

import com.oriental.manage.core.enums.CacheKey;
import com.oriental.manage.core.enums.ErrorCodeManage;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.IplimitCfgMapper;
import com.oriental.manage.pojo.base.IplimitCfg;
import com.oriental.manage.service.base.IpLimitCfgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by yutao on 2016/10/31.
 */
@Service
@Slf4j
public class IpLimitCfgServiceImpl implements IpLimitCfgService {
    @Autowired(required = false)
    private IplimitCfgMapper ipLimitCfgMapper;

    @Override
    public void ipValidate(IplimitCfg queryIp) throws BusiException {
        boolean flag;

        try {
            String ipType;
            String privateGreenIP;

            IplimitCfg cacheIp = SessionUtils.getIpLimitCfg();
            if (cacheIp != null) {
                ipType = cacheIp.getIpType();
                privateGreenIP = cacheIp.getClientIp();
            } else {
                queryIp.setEnableFlag("1");
                Date date = new Date();
                queryIp.setIpEndTime(date);
                IplimitCfg result = ipLimitCfgMapper.selectIp(queryIp);

                if (result != null) {
                    ipType = result.getIpType();
                    privateGreenIP = result.getClientIp();
                    SessionUtils.setIpLimitCfg(result);
                } else {
                    ipType = "01";
                    privateGreenIP = "";

                    IplimitCfg iplimitCfg = new IplimitCfg();
                    iplimitCfg.setIpType("01");
                    iplimitCfg.setClientIp("");
                    SessionUtils.setIpLimitCfg(iplimitCfg);
                }
            }


            switch (ipType) {
                case "01":
                    flag = isInnerIp(queryIp.getClientIp()) || isCompanyOutGateIP(queryIp.getClientIp());
                    break;
                case "02":
                    flag = isMchntIp(queryIp.getClientIp()) || StringUtils.equals(queryIp.getClientIp(), privateGreenIP);
                    break;
                case "03":
                    flag = true;
                    break;
                default:
                    flag = isInnerIp(queryIp.getClientIp()) || isCompanyOutGateIP(queryIp.getClientIp());
            }
        } catch (Exception e) {
            log.error("绿色ip校验异常", e);
            throw new BusiException(ErrorCodeManage.GREEN_IP);
        }

        if (!flag) {
            throw new BusiException(ErrorCodeManage.GREEN_IP);
        }
        log.debug("IP验证完成!");
    }


    //内网ip验证
    public boolean isInnerIp(String ip) {

        String regA = "^10(\\.(2[0-4]\\d|25[0-5]|[1]?\\d?\\d)){3}$";
        String regB = "^172\\.(1[6-9]|2\\d|3[0-1])(\\.(2[0-4]\\d|25[0-5]|[1]?\\d?\\d)){2}$";
        String regC = "^192.168(\\.(2[0-4]\\d|25[0-5]|[1]?\\d?\\d)){2}$";
        Pattern pA = Pattern.compile(regA);
        Pattern pB = Pattern.compile(regB);
        Pattern pC = Pattern.compile(regC);
        return pA.matcher(ip).find() || pB.matcher(ip).find() || pC.matcher(ip).find() || StringUtils.equals(ip, "localhost") ||
                StringUtils.equals(ip, "127.0.0.1") || StringUtils.equals(ip, "0:0:0:0:0:0:0:1");
    }

    private boolean isCompanyOutGateIP(String ip) {
        Map<String, String> map = Constants.getDataDictMap().get(CacheKey.COMPANY_IP.name().toLowerCase());
        return map.containsKey(ip);
    }

    private boolean isMchntIp(String ip) {
        Map<String, String> map = Constants.getDataDictMap().get(CacheKey.MCHNT_IP.name().toLowerCase());
        return map.containsKey(ip);
    }

    public void queryPage(Pagination<IplimitCfg> pagination, IplimitCfg iplimitCfg) {
        List<IplimitCfg> list = ipLimitCfgMapper.selectIplimit(iplimitCfg);
        if (list != null && list.size() > 0) {
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    public void deleteIplimitCfg(IplimitCfg iplimitCfg, ResponseDTO<String> responseDTO) {
        if (ipLimitCfgMapper.deleteByPrimaryKey(iplimitCfg.getId()) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

    public boolean selectByUser(IplimitCfg iplimitCfg) {
        iplimitCfg.setCreateTime(new Date());
        iplimitCfg.setLastUpdTime(new Date());
        List<IplimitCfg> list = ipLimitCfgMapper.selectByUser(iplimitCfg);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public void addNewIplimit(IplimitCfg iplimitCfg, ResponseDTO<String> responseDTO) throws Exception {
        iplimitCfg.setId(UUID.randomUUID().toString());
        //  iplimitCfg.setCreateTime(new Date());
        // iplimitCfg.setLastUpdTime(new Date());

        if (ipLimitCfgMapper.insertSelective(iplimitCfg) > 0) {
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setSuccess(false);
        }
    }

}
