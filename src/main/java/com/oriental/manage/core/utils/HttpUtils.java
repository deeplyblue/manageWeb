package com.oriental.manage.core.utils;

import com.oriental.paycenter.commons.utils.ReadCertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lupf on 2016/7/5.
 */
@Slf4j
public class HttpUtils {

    public static final int DEFAULT_CONNECTIN_TIMEOUT = 20000;
    public static final int DEFAULT_SO_TIMEOUT = 50000;

    private static String DEFAULT_CHARSET = "UTF-8";
    public static final RequestConfig config = RequestConfig.custom()
            .setSocketTimeout(DEFAULT_SO_TIMEOUT)
            .setConnectTimeout(DEFAULT_CONNECTIN_TIMEOUT)
            .setConnectionRequestTimeout(DEFAULT_CONNECTIN_TIMEOUT)
            .build();

    public static void init() throws Exception {
       /* log.info("初始化httpclient");
        cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(100);// 最大连接
        cm.setDefaultMaxPerRoute(10);// 每个路由最大连接

        // HttpHost localhost = new HttpHost("locahost", 80);
        // cm.setMaxPerRoute(new HttpRoute(localhost), 50);// 增加指定路由的最大连接

        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTIN_TIMEOUT); // 连接超时
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SO_TIMEOUT); // 请求超时
        httpclient = new DefaultHttpClient(cm, params);
        registerSSl();
//        HttpHost proxy = new HttpHost("192.168.19.9", 80, "http");
//        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        Thread thread = new HttpClientUtil();
        thread.setDaemon(true);
        thread.start();*/
    }

    public static String invoke(String reqUrl) {
        return invoke(reqUrl, StringUtils.EMPTY, DEFAULT_CHARSET, true);

    }

    public static String invoke(String reqUrl, String params) {
        return invoke(reqUrl, params, DEFAULT_CHARSET, true);
    }

    public static String invoke(String reqUrl, String params, boolean decode) {
        return invoke(reqUrl, params, DEFAULT_CHARSET, decode);
    }

    /**
     * http请求
     *
     * @param reqUrl
     * @param params
     * @param charsetName
     * @param decode
     * @return
     */
    public static String invoke(String reqUrl, String params, String charsetName, boolean decode) {
        CloseableHttpClient cHttpclient = HttpClients.createDefault();
        log.info("CloseableHttpClient invoke -- entry -- reqUrl:{},params:{},charsetName:{},decode:{}", reqUrl, params, charsetName, decode);
        HttpPost httpPost = new HttpPost(reqUrl);
        httpPost.setConfig(config);
        try {
            if (!StringUtils.isEmpty(params)) {
                httpPost.setEntity(new StringEntity(params, charsetName));
            }
            HttpResponse response = cHttpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            String value = EntityUtils.toString(entity, charsetName);
            EntityUtils.consume(entity);
            if (decode) {
                value = URLDecoder.decode(value, charsetName);
            }
            return value;
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("CloseableHttpClient invoke 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("CloseableHttpClient invoke 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("CloseableHttpClient invoke Exception：{}", e);
            return StringUtils.EMPTY;
        } finally {
            if (null != cHttpclient) {
                try {
                    cHttpclient.close();
                } catch (IOException e) {
                    log.error("http连接关闭异常");
                }
            }
        }
        return null;
    }
    public static String invokeRequest(String reqUrl, String params, String charsetName, boolean decode) {
        log.info("CloseableHttpClient invokeRequest -- entry -- reqUrl:{},params:{},charsetName:{},decode:{}", reqUrl, params, charsetName, decode);
        HttpPost httpPost = new HttpPost(reqUrl);
        try {
            HttpClient cHttpclient = getHttpClient();
            if (!StringUtils.isEmpty(params)) {
                httpPost.setEntity(new StringEntity(params, charsetName));
            }
            HttpResponse response = cHttpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            String value = EntityUtils.toString(entity, charsetName);
            EntityUtils.consume(entity);
            if (decode) {
                value = URLDecoder.decode(value, charsetName);
            }
            return value;
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("HttpClient 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("HttpClient 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("HttpClient invoke exception {}", e);
            return StringUtils.EMPTY;
        } finally {

        }
        return null;
    }

    public static String invokeRequest(String reqUrl, String params) {
        log.info("HttpClient invokeRequest -- entry -- reqUrl:{},params:{}", reqUrl, params);
        HttpPost httpPost = null;
        try {

            httpPost = new HttpPost(reqUrl);
            httpPost.setEntity(new StringEntity(params, "UTF-8"));
            HttpResponse response = getHttpClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            String value = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
            return value;
        } catch (Exception e) {
            log.error("执行http请求异常: {}", e);
            return StringUtils.EMPTY;
        } finally {
            httpPost = null;
            log.debug("结束http请求..........");
        }
    }

    public static HttpClient getHttpClient() throws Exception {
        HttpParams param = new BasicHttpParams();
        param.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); // 连接超时
        param.setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000); // 请求超时
        HttpClient httpclient = new DefaultHttpClient(param);
        registerSSl(httpclient);
        return httpclient;
    }

    private static void registerSSl(HttpClient httpclient) throws Exception {
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslcontext.init(null, new TrustManager[]{tm}, null);
        org.apache.http.conn.ssl.SSLSocketFactory sf = new org.apache.http.conn.ssl.SSLSocketFactory(sslcontext, org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme https = new Scheme("https", 443, sf);
        httpclient.getConnectionManager().getSchemeRegistry().register(https);
    }

    public static String invoke(String reqUrl, String params, String charsetName, boolean decode, String mimeType) {
        log.info("CloseableHttpClient invoke -- entry -- reqUrl:{},params:{},charsetName:{},decode:{}", reqUrl, params, charsetName, decode);
        CloseableHttpClient cHttpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(reqUrl);
        httpPost.setConfig(config);
        try {
            if (!StringUtils.isEmpty(params)) {
                httpPost.setEntity(new StringEntity(params, ContentType.create(mimeType, charsetName)));
            }
            HttpResponse response = cHttpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            String value = EntityUtils.toString(entity, charsetName);
            EntityUtils.consume(entity);
            if (decode) {
                value = URLDecoder.decode(value, charsetName);
            }
            return value;
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("CloseableHttpClient 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("CloseableHttpClient 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("CloseableHttpClient invoke Exception：{}", e);
            return StringUtils.EMPTY;
        } finally {
            if (null != cHttpclient) {
                try {
                    cHttpclient.close();
                } catch (IOException e) {
                    log.error("http连接关闭异常");

                }
            }
        }
        return null;
    }

    public static String invoke(String reqUrl, Map<String, String> params) {
        return invoke(reqUrl, params, DEFAULT_CHARSET, true);
    }

    public static String invoke(String reqUrl, Map<String, String> params, boolean decode) {
        return invoke(reqUrl, params, DEFAULT_CHARSET, decode);
    }

    /**
     * http 请求Map参数
     *
     * @param reqUrl
     * @param params
     * @param charset
     * @param decode
     * @return
     */
    public static String invoke(String reqUrl, Map<String, String> params, String charset, boolean decode) {
        log.info("CloseableHttpClient invoke -- entry -- reqUrl:{},params:{},decode:{}", reqUrl, params, decode);
        CloseableHttpClient cHttpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(reqUrl);
        httpPost.setConfig(config);
        try {
            if (null != params) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
                Set<Map.Entry<String, String>> set = params.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            }

            HttpResponse response = cHttpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            String value = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            if (decode) {
                value = URLDecoder.decode(value, charset);
            }
            return value;
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("CloseableHttpClient 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("CloseableHttpClient 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("CloseableHttpClient invoke Exception：{}", e);
            return StringUtils.EMPTY;
        } finally {
            if (null != cHttpclient) {
                try {
                    cHttpclient.close();
                } catch (IOException e) {
                    log.error("http连接关闭异常");
                }
            }
        }
        return null;
    }

    /*@Override
    public void run() {
       *//* try {
            while (true) {
                TimeUnit.MINUTES.sleep(3);// 每隔3分钟清理连接
                if (cm != null) {
                    cm.closeExpiredConnections();
                    cm.closeIdleConnections(20, TimeUnit.MINUTES);// 连接空闲20分钟后被关闭
                }
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }*//*
    }
*/
   /* public static void registerSSl() throws Exception {
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslcontext.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme https = new Scheme("https", 443, sf);

        httpclient.getConnectionManager().getSchemeRegistry().register(https);
    }

    public static void shutdown() {
        if (cm != null) {
            cm.shutdown();
        }
    }*/

    /**
     * https  请求，第一组key对应于keystores  第二组对应truststores
     *
     * @param urlContent
     * @param keyPath
     * @param keyPwd
     * @param trustKeyPath
     * @param trustKeyPwd
     * @return
     */
    public static String invokeGetHttps(String urlContent, String keyPath, String keyPwd, String trustKeyPath, String trustKeyPwd) {
        log.info("CloseableHttpClient invokeGetHttps -- entry -- urlContent:{},keyPath:{},keyPwd:{},trustKeyPath:{},trustKeyPwd:{}", urlContent, keyPath, keyPwd, trustKeyPath, trustKeyPwd);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getCloseableHttpClient(keyPath, keyPwd, trustKeyPath, trustKeyPwd);
//            HttpHost proxy = new HttpHost("192.168.19.9", 80, "http");
            HttpGet httpget = new HttpGet(urlContent);
            httpget.setConfig(config);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                return EntityUtils.toString(response.getEntity());
            } finally {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("CloseableHttpClient 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("CloseableHttpClient 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("CloseableHttpClient invoke Exception：{}", e);
            return StringUtils.EMPTY;
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    log.error("http连接关闭异常");
                }
            }

        }
        return null;
    }

    private static CloseableHttpClient getCloseableHttpClient(String keyPath, String keyPwd, String trustKeyPath, String trustKeyPwd) throws Exception {

        KeyStore ks = ReadCertUtil.getKeyInfo(keyPath, keyPwd);
        KeyStore trustStore = ReadCertUtil.getKeyInfo(trustKeyPath, trustKeyPwd);
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(ks, keyPwd.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
    }

    /**
     * 根据SSLContext        创建http请求
     *
     * @param urlContent
     * @param params
     * @param sslcontext
     * @return
     */
    public static String invokePostHttps(String urlContent, Map<String, String> params, SSLContext sslcontext) {
        log.info("CloseableHttpClient invokePostHttps -- entry -- urlContent:{},params:{},sslcontext:{}", urlContent, params, sslcontext);
        CloseableHttpClient httpclient = null;
        try {
            httpclient = getCloseableHttpClient(sslcontext);
            HttpPost httpPost = new HttpPost(urlContent);
            if (null != params) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
                Set<Map.Entry<String, String>> set = params.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            }
            httpPost.setConfig(config);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                return EntityUtils.toString(response.getEntity()).trim();
            } finally {
                EntityUtils.consume(response.getEntity());
                response.close();
            }
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return StringUtils.EMPTY;
        } finally {
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    log.error("http连接关闭异常");
                }
            }

        }
        return null;
    }

    private static CloseableHttpClient getCloseableHttpClient(SSLContext sslcontext) throws Exception {
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        return HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
    }

    public static String invokePostHttps(String reqUrl, String params, String encode) {
        try {
            HttpClient cHttpclient = getHttpClient();
            registerSSl(cHttpclient);
            HttpPost httpPost = new HttpPost(reqUrl);
            httpPost.setConfig(config);
            if (!StringUtils.isEmpty(params)) {
                httpPost.setEntity(new StringEntity(params, encode));
            }
            HttpResponse response = cHttpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String value = EntityUtils.toString(entity, encode);
            EntityUtils.consume(entity);
            return value;
        } catch (ConnectTimeoutException cte) {   //请求超时
            log.error("CloseableHttpClient 请求超时{}", cte);
//            throw new BankException(RCBank.BF_CNCT_REQUESTOUT);
        } catch (SocketTimeoutException ste) {  //读取超时
            log.error("CloseableHttpClient 读取超时{}", ste);
//            throw new BankException(RCBank.BF_CNCT_READOUT);
        } catch (Exception e) {
            log.error("CloseableHttpClient invoke Exception：{}", e);
            return StringUtils.EMPTY;
        }
        return null;
    }

}
