package com.oriental.manage.core.system.log;

import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.OperateRecordMapper;
import com.oriental.manage.pojo.base.OperateRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by lupf on 2016/7/13.
 */
@Slf4j
public class OperateRecordInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private OperateRecordMapper operateRecordMapper;

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            super.preHandle(request, response, handler);
            //�鿴ע��
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            OperateLogger operateLogger = method.getAnnotation(OperateLogger.class);

            if (operateLogger != null) {
                OperateRecord operateRecord = new OperateRecord();
                Date now = new Date();

                operateRecord.setId(MDC.get(LogIdInterceptor.log_id));

                String userId = SessionUtils.getUserId();
                if (StringUtils.isBlank(userId)) {
                    //若为登录，则记录登录用户名

                    userId = request.getParameterMap().get("userName")[0];

                    if (StringUtils.isBlank(userId)) {
                        userId = "unknown";
                    }
                }

                operateRecord.setUserId(userId);
                operateRecord.setRsrcCode(request.getRequestURI());
                operateRecord.setOpType(operateLogger.operationType().name());
                operateRecord.setOpDesc(operateLogger.content());
                operateRecord.setOpTime(now);
                operateRecord.setPrevContent(operateLogger.oldObject());
                operateRecord.setContent(operateLogger.targetObject());
                operateRecord.setCreateTime(now);
                operateRecord.setLastUpdTime(now);
                operateRecord.setClientIp(request.getRemoteHost());
                //Ĭ�ϳɹ�
                operateRecord.setOpResult("true");

                operateRecordMapper.insertSelective(operateRecord);
            }
        } catch (Exception e) {
            log.error("操作日志记录", e);
        }

        return true;
    }

}
