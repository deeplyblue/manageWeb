package com.oriental.manage.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理帮助类
 */
@Slf4j
public class FieldUtils {

    public static String hideName(String name) {
        try {
            if (StringUtils.isNotBlank(name)) {
                return "*" + name.substring(name.length() - 1);
            }
        } catch (Exception e) {
            log.error("姓名脱敏异常", e);
            return "******";
        }
        return "******";
    }

    public static String hideCertNo(String certNo) {
        try {
            if (StringUtils.isNotBlank(certNo)) {
                return certNo.substring(0, 6) + "********" + certNo.substring(certNo.length() - 4);
            }
        } catch (Exception e) {
            log.error("证件号脱敏异常", e);
            return "******";
        }
        return "******";
    }

    public static String hidePhone(String phone) {
        try {
            if (StringUtils.isNotBlank(phone)) {
                return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
            }
        } catch (Exception e) {
            log.error("手机号脱敏异常", e);
            return "******";
        }
        return "******";
    }

    /**
     * 银行卡号去前六后四   中间隐藏字符用*
     */
    public static String hideBankCardNo(String cardNo) {
        try {
            if (StringUtils.isNotBlank(cardNo)) {
                String beforeSix = cardNo.substring(0, 6);
                String lastFour = cardNo.substring(cardNo.length() - 4);
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < cardNo.length() - beforeSix.length() - lastFour.length(); i++) {
                    s.append("*");
                }
                return beforeSix + s + lastFour;
            }
        } catch (Exception e) {
            log.error("银行卡号脱敏异常", e);
            return "******";
        }
        return "******";
    }

}
