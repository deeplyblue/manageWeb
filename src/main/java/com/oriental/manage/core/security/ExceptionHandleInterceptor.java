package com.oriental.manage.core.security;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.GsonUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/**
 * Created by lupf on 2016/11/23.
 */
@Slf4j
public class ExceptionHandleInterceptor extends HandlerInterceptorAdapter {

    /**
     * 访问无权限，跳转页面
     */
    private final String noPermission = "/noPermission.jsp";
    /**
     * 服务端发生未知异常,跳转页面
     */
    private final String serverError = "/errors/serverError";

    @Value("#{cfgProperties['httpEnable']}")
    @Setter
    private String httpEnable;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        if (ex == null) {
            return;
        } else {
            HandlerMethod method = (HandlerMethod) handler;
            if (ex.getClass() == UnauthorizedException.class) {

                if (method.getReturnType().getParameterType() == ResponseDTO.class) {
                    ResponseDTO responseDTO = new ResponseDTO();
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("has no permission!");
                    responseOutWithJson(response, responseDTO);
                    return;
                } else {
                    WebUtils.issueRedirect(request, response, noPermission, Collections.EMPTY_MAP, true, Boolean.parseBoolean(httpEnable));
                    return;
                }
            } else if (ex.getClass() == BusiException.class) {
                BusiException e = (BusiException) ex;
                if (e.getErrorCodeManage() != null) {
                    switch (e.getErrorCodeManage()) {
                        case GREEN_IP:
                            if (method.getReturnType().getParameterType() == ResponseDTO.class) {
                                ResponseDTO responseDTO = new ResponseDTO();
                                responseDTO.setSuccess(false);
                                responseDTO.setMsg(e.getDesc());
                                responseOutWithJson(response, responseDTO);
                                return;
                            } else {
                                WebUtils.issueRedirect(request, response, noPermission, Collections.EMPTY_MAP, true, Boolean.parseBoolean(httpEnable));
                                return;
                            }
                    }
                }
            }
        }


        //其他异常的默认处理方式
        WebUtils.issueRedirect(request, response, noPermission, Collections.EMPTY_MAP, true, Boolean.parseBoolean(httpEnable));


    }


    /**
     * 以JSON格式输出
     *
     * @param response
     */
    private void responseOutWithJson(HttpServletResponse response,
                                     Object responseObject) {
        //将实体对象转换为JSON Object转换
        String responseJSONObject = GsonUtil.getInstance().toJson(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
            out.flush();
        } catch (IOException e) {
            log.error("结果返回异常", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
