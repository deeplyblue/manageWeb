package com.oriental.manage.core.system.log;

import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

/**
 * Created by lupf on 2016/7/12.
 */
@Slf4j
public class LogIdInterceptor implements WebRequestInterceptor{

    public static final String log_id = "logId";

    @Override
    public void preHandle(WebRequest request) throws Exception {
        String desInfo = request.getDescription(true);
        String[] infos = desInfo.split(";");

        String logId = new StringBuilder()
                .append(String.valueOf(System.currentTimeMillis()))
                .append(RandomStringUtils.randomAlphabetic(4))
                .toString();
        //设置logId
        MDC.put(log_id, logId);

        log.info("登录用户名：{}", SessionUtils.getUserName());

        log.info("访问地址：{}",infos[0]);
        log.info("访问来源：{}",infos[1]);

        Map<String,String[]> map = request.getParameterMap();
        if (map !=null && map.size() > 0){
            for (Map.Entry<String,String[]> entry : map.entrySet()){
                log.info("访问参数名：{} ,ֵ值：{}",entry.getKey(),entry.getValue());
            }
        }else {
            log.info("访问参数为空");
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        //移除logId
        MDC.remove(log_id);
    }
}
