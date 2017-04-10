package com.oriental.manage.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Desc: 获取请求IP
 * User: ma wei
 * Date: 2016/6/18 14:34
 */
public class IPUtil {
    public IPUtil() {
    }

    /**
     * 获取请求ip地址
     *
     *            请求对象
     * @return 返回发送请求的ip地址
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return doMultiLevelAgencyIp(ip);
    }

    /***
     * 取首个IP地址(多个ip情况下)
     * @param oldClientIp
     * @return
     */
    private static String doMultiLevelAgencyIp(String oldClientIp){
        String newClientIp = oldClientIp;
        //如果ip地址大于15位，则为多IP
        if(oldClientIp.length()>15&&oldClientIp.contains(",")){
            newClientIp = oldClientIp.split(",")[0].trim();
        }
        return newClientIp;
    }
}
