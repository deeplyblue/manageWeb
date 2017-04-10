package com.oriental.manage.core.smsUtils;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.paycenter.commons.constans.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lupf on 2016/7/5.
 */
@Slf4j
@Repository("smsUtils")
@Scope
public class SmsUtils {
    @Value("#{cfgProperties['sms_account']}")
    private String account;
    @Value("#{cfgProperties['sms_password']}")
    private String password;
    @Value("#{cfgProperties['sms_smsAuthCode']}")
    private String smsAuthCode;
    @Value("#{cfgProperties['sms_channel']}")
    private String channel;
    @Value("#{cfgProperties['sms_sendType']}")
    private String sendType;
    @Value("#{cfgProperties['sms_LOGinUrl']}")
    private String LOGinUrl;

    public void send(String phoneNumber, String content) {

        try {
            // 设置超时时间(单位毫秒)
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(20000)
                    .setConnectionRequestTimeout(50000)
                    .setSocketTimeout(50000)
                    .build();

            CloseableHttpClient httpClient =
                    HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpPost httpPost = new HttpPost(LOGinUrl);
            //组装参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("account", account));
            params.add(new BasicNameValuePair("password", md5Encrypt(password + smsAuthCode)));
            params.add(new BasicNameValuePair("mobile", phoneNumber));
            params.add(new BasicNameValuePair("content", content));
            params.add(new BasicNameValuePair("channel", channel));
            params.add(new BasicNameValuePair("smsid", "" + System.currentTimeMillis()));
            params.add(new BasicNameValuePair("sendType", sendType));

            httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));

            log.debug("httpClient.execute start");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            log.debug("httpClient.execute end");

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                Header locationHeader = response.getFirstHeader("location");
                String location;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    log.debug("The page was redirected to:" + location);
                } else {
                    log.debug("Location field value is null.");
                }
            } else {
                String body = EntityUtils.toString(response.getEntity());
                log.debug("结果为" + body);
                if (body.indexOf("0") != 0){
                    throw new BusiException("短信发送异常");
                }
            }
            httpPost.releaseConnection();
            log.debug("postSMS end");

        } catch (Exception e) {
            log.error("获取发送状态信息异常:" + e.getMessage(), e);
            throw new BusiException("短信发送异常");

    }
    }

    private String md5Encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte buffer[] = input.getBytes();
            md.update(buffer);
            byte bDigest[] = md.digest();
            md.reset();
            BigInteger bi = new BigInteger(1, bDigest);
            return bi.toString(16);
        } catch (Exception e) {
            log.error("smsMD5加密失败", e);
        }
        return null;
    }
}
