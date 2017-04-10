package com.oriental.manage.controller.base;

import cfca.mobile.BOCServerDecryption;
import com.oriental.manage.core.authorize.ForceLoginToken;
import com.oriental.manage.core.enums.ErrorCodeManage;
import com.oriental.manage.core.enums.RedisKey;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.exception.FirstLoginException;
import com.oriental.manage.core.exception.LoginRepeatException;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.smsUtils.SmsUtils;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.*;
import com.oriental.manage.pojo.base.IplimitCfg;
import com.oriental.manage.pojo.base.RoleInfo;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IRoleInfoService;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.manage.service.base.IpLimitCfgService;
import com.oriental.manage.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.utils.file.Resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lupf on 2016/4/13.
 */
@Slf4j
@Controller
@RequestMapping("login")
public class UserLoginController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRoleInfoService roleInfoService;
    @Autowired
    private VerifyCodeUtil verifyCodeUtil;
    @Autowired(required = false)
    private RedisService redisService;
    @Autowired
    private IpLimitCfgService ipLimitCfgService;
    @Autowired
    private SmsUtils smsUtils;
    @Value("#{cfgProperties['loginSmsValid']}")
    private boolean loginSmsValid;
    @Value("#{cfgProperties['loginYzmValid']}")
    private boolean loginYzmValid;
    @Value("#{cfgProperties['schedule_pwdExpired']}")
    private boolean pwdExpiredFlag;
    @Value("#{cfgProperties['pwdctr_privatekey']}")
    private String pwdctr_privatekey;
    @Value("#{cfgProperties['ERROR_LOGIN_COUNT']}")
    private String passwordKey;

    @RequestMapping("/login")
    @OperateLogger(content = "用户登录", operationType = OperateLogger.OperationType.R)
    @ResponseBody
    public ResponseDTO login(String userName, String userPwd, String verifyCode, String smsVerifyCode,
                             String mchntCode, String forceFlag, String asPrincipal, HttpServletRequest request, String clientRandom) {
        ResponseDTO responseDTO = new ResponseDTO();

        String userId = null;
        String lastLoginTime = null;
        String lastLoginIpAddress = null;
        UserInfo userInfo = new UserInfo();
        UserInfo info = null;
        try {
            userInfo.setUserName(userName);
            userPwd = decryptPwd(userPwd, clientRandom);

            log.debug("解密结果为：{}" + userPwd);
            userInfo.setUserPwd(userPwd);


            Validate.validateObject(userInfo);

            checkVerifyCode(verifyCode);

            info = userInfoService.getUserByName(userName);

            if (info.getPwdLastUpd() == null) {
                throw new FirstLoginException();
            }
            if (info != null && (!StringUtils.equals(info.getUserStatus(), "03"))) {

                userId = info.getUserId();

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    lastLoginTime = format.format(info.getLastLoginTime());
                } catch (Exception e) {
                    log.error("系统异常", e);
                    lastLoginTime = "";
                }
                lastLoginIpAddress = info.getLastLoginIpAddress();

                if (loginSmsValid) {
                    try {
                        //公司用户
                        if (StringUtils.isBlank(SessionUtils.getSmsVerifyCode()) || !StringUtils.equals(smsVerifyCode, SessionUtils.getSmsVerifyCode())) {
                            SessionUtils.setVerifyCode(RandomStringUtils.randomAlphabetic(5));//登录失败重置验证码，防止恶意攻击
                            throw new BusiException("短信验证码校验失败");
                        }
                    } catch (BusiException e) {
                        /**
                         * 短信验证不通过时，允许使用超级验证码：cbc-acbc
                         * 同时需具有相应角色，name：超级验证码
                         */
                        if (StringUtils.equals(smsVerifyCode, "cbc-acbc")) {
                            boolean flag = false;
                            List<RoleInfo> roleInfoList = roleInfoService.getRolesByUserId(info.getUserId());
                            if (roleInfoList != null && roleInfoList.size() > 0) {
                                for (RoleInfo roleInfo : roleInfoList) {
                                    if (roleInfo.getRoleName().contentEquals("超级验证码")) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (flag) {
                                log.info("当前用户使用超级验证码登录");
                            } else {
                                throw e;
                            }
                        } else {
                            throw e;
                        }
                    }
                }

                if (StringUtils.equals(info.getUserType(), "02")) {
                    //商户用户
                    if (StringUtils.isBlank(info.getCompanyCode()) || !StringUtils.equals(mchntCode, info.getCompanyCode())) {
                        SessionUtils.setVerifyCode(RandomStringUtils.randomAlphabetic(5));//登录失败重置验证码，防止恶意攻击
                        throw new BusiException("用户信息不符!");
                    }
                }


                Subject currentUser = SecurityUtils.getSubject();

                ForceLoginToken token = new ForceLoginToken(userName, userInfoService.enctryptPwd(userPwd),
                        StringUtils.equals(forceFlag, "1") ? true : false);

                currentUser.login(token);
                //验证ip
                String ip = IPUtil.getIpAddrByRequest(request);
                IplimitCfg queryIp = new IplimitCfg();
                queryIp.setClientIp(ip);
                queryIp.setUserId(info.getUserId());
                ipLimitCfgService.ipValidate(queryIp);
                /**记录每次登录成功的时间、IP地址 **/
                UserInfo updateUser = new UserInfo();
                updateUser.setUserId(userId);
                Date now = new Date();
                updateUser.setLastLoginTime(now);
                String ipAddress = IPUtil.getIpAddrByRequest(request);
                updateUser.setLastLoginIpAddress(ipAddress);
                log.debug("当前登录IP:" + ipAddress);

                try {
                    userInfoService.updateLoginTimeAndIP(updateUser, responseDTO);
                } catch (Exception e) {
                    log.error("系统异常", e);
                }
                Map<String, Object> infoMap = new HashMap<String, Object>();
                infoMap.put("userName", userName);
                infoMap.put("lastLoginTime", lastLoginTime);
                infoMap.put("lastLoginIpAddress", lastLoginIpAddress);
                responseDTO.setInfoMap(infoMap);
                /**记录每次登录成功的时间、IP地址 存入Session**/
                SessionUtils.setLastLoginTime(lastLoginTime);
                SessionUtils.setLastLoginIpAddress(lastLoginIpAddress);
                /**记录每次登录成功的时间、IP地址 end**/
                responseDTO.setSuccess(true);
                //登陆成功，判断密码是否将要过期--页面提示
                if (pwdExpiredFlag && userInfo.getPwdLastUpd() != null && DateTime.now().minusMonths(3).plusDays(7).toDate().compareTo(userInfo.getPwdLastUpd()) > 0) {
                    responseDTO.setCode(ErrorCodeManage.LOGIN_PWD_EXPIRE.getCode());
                }
                //登入成功，删除密码错误计数
                redisService.delCachesData(passwordKey + info.getUserId());
            } else {

                if (info == null) {
                    Subject currentUser = SecurityUtils.getSubject();

                    ForceLoginToken token = new ForceLoginToken(userName, userInfoService.enctryptPwd(userPwd),
                            StringUtils.equals(forceFlag, "1") ? true : false);

                    currentUser.login(token);
                }
                responseDTO.setSuccess(false);
                responseDTO.setMsg("该账号被锁定,24小时后再试(或联系管理员)");
            }

        } catch (BusiException e) {
            log.error("系统异常", e);
//            SessionUtils.setVerifyCode(RandomStringUtils.randomAlphabetic(5));//登录失败重置验证码，防止恶意攻击
            log.info("登录失败,{},{}", e.getMessage(), userInfo);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getDesc());

        } catch (LockedAccountException e) {
            log.error("系统异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        } catch (FirstLoginException e) {
            log.error("首次登陆，需重置密码", e);
            responseDTO.setSuccess(false);
            responseDTO.setCode(ErrorCodeManage.FIRST_LOGIN.getCode());
            responseDTO.setMsg(ErrorCodeManage.FIRST_LOGIN.getDesc());
        } catch (AuthenticationException e) {
            log.error("登录认证失败", e);
            if (e.getCause() != null && e.getCause().getClass() == LoginRepeatException.class) {
                LoginRepeatException cause = (LoginRepeatException) e.getCause();

                responseDTO.setSuccess(false);
                responseDTO.setCode(ErrorCodeManage.LOGIN_REPEAT.getCode());
                responseDTO.setMsg(cause.getMessage());
                //此处直接返回  不清除验证码信息
                return responseDTO;
            } else {

                String msg = "用户名或密码错误";
                //记录密码错误次数
                if (info != null) {
                    msg = userInfoService.errorLoginCount(info, passwordKey);
                }
                log.info(msg);
                responseDTO.setSuccess(false);
                responseDTO.setMsg(msg);
            }
        } catch (Exception e) {
//            SessionUtils.setVerifyCode(RandomStringUtils.randomAlphabetic(5));//登录失败重置验证码，防止恶意攻击
            log.error("登录失败", userInfo, e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("登录异常");

        }

        return responseDTO;

    }

    private String decryptPwd(String userPwd, String clientRandom) throws UnsupportedEncodingException {
        String[] arr = new String[6];
        String path = "";
        try {
            path = Resources.getResourceAsFile("/").getParent() + "/pfx";
        } catch (IOException e) {
            log.error("系统异常", e);
        }
        arr[0] = path + "/cfca_pay@2016.pfx";
        arr[1] = pwdctr_privatekey;

        arr[2] = URLDecoder.decode(clientRandom, "UTF-8");
        arr[3] = SessionUtils.getServerRandom();


        arr[4] = URLDecoder.decode(userPwd, "UTF-8");
        arr[5] = "1";
        return BOCServerDecryption.decrypt(arr);

    }

    @RequestMapping("verifyCode")
    @ResponseBody
    public ResponseEntity<byte[]> getVerifyCode() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setCacheControl("no-cache");
            headers.setPragma("no-cache");

            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(verifyCodeUtil.drawImage()),
                    headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("生成验证码失败", e);
        }
        return null;
    }

    /*@RequestMapping("checkVerifyCode")
    @ResponseBody*/
    private void checkVerifyCode(String verifyCode) throws BusiException {

        log.debug(verifyCode + "----" + SessionUtils.getVerifyCode());
        if (!loginYzmValid || StringUtils.equals(verifyCode.toUpperCase(), SessionUtils.getVerifyCode())) {

        } else {

            throw new BusiException("验证码错误!");
        }
    }

    @RequestMapping("smsVerifyCode")
    @ResponseBody
    public ResponseDTO sendSmsVerifyCode(String userName, String userPhone) {
        ResponseDTO responseDTO = new ResponseDTO();
        String template = "您本次登录系统的手机验证码为:{smsVerifyCode}，有效期：10分钟【亿付数字】";
        try {
            UserInfo userInfo = null;
            if (StringUtils.isNotBlank(userName)) {
                userInfo = userInfoService.getUserByName(userName);
            } else {
                throw new BusiException("请先输入用户名、密码");
            }

            //找回密码时使用
            if (StringUtils.isNotBlank(userPhone) && !StringUtils.equals(userPhone, userInfo.getUserMobile())) {
                throw new BusiException("预留手机号不匹配");
            }

            if (SessionUtils.getSmsActionTime() >= 9 * 60) {
                throw new BusiException("短信请求过于频繁,请稍后再试!");
            }

            if (userInfo != null && userInfo.getUserMobile() != null) {
                SessionUtils.setSmsVerifyCode(RandomMath.getNum(6));
                String temp = template.replace("{smsVerifyCode}", SessionUtils.getSmsVerifyCode());

                smsUtils.send(userInfo.getUserMobile(), temp);
            } else {
                throw new BusiException("用户不存在，或者手机号未配置");
            }


            responseDTO.setSuccess(true);
        } catch (BusiException e) {
            log.error("短信发送失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getDesc());
        } catch (Exception e) {
            log.error("短信发送失败", e);
            responseDTO.setSuccess(false);
        }

        return responseDTO;
    }

    @RequestMapping("smsForResetPwd")
    @ResponseBody
    public ResponseDTO smsForResetPwd(String userName, String userPhone) {
        if (StringUtils.isNotBlank(userPhone)) {
            return this.sendSmsVerifyCode(userName, userPhone);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setSuccess(false);
            responseDTO.setMsg("请先输入手机号!");
            return responseDTO;
        }
    }


    private String uncompile(String pwd) {
        String c = String.valueOf((char) (pwd.charAt(0) - pwd.length()));
        for (int i = 1; i < pwd.length(); i++) {
            c += String.valueOf((char) (pwd.charAt(i) - 1));
        }
        log.debug(c);
        return c;
    }

    @OperateLogger(content = "密码修改", operationType = OperateLogger.OperationType.U)
    @RequestMapping("resetPwd")
    @ResponseBody
    public ResponseDTO resetPwd(String userName, String userPwd, String smsVerifyCode, String clientRandom) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            UserInfo info = userInfoService.getUserByName(userName);

            if (info != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(info.getUserId());
                userInfo.setUserName(userName);
                userPwd = decryptPwd(userPwd, clientRandom);
                userInfo.setUserPwd(userPwd);

                if (StringUtils.isNotBlank(smsVerifyCode) && StringUtils.equals(smsVerifyCode, SessionUtils.getSmsVerifyCode())) {

                    Pattern pattern = Pattern.compile("^(?=.{8,16})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*|(?=.{8,})(?=.*[A-Z])" +
                            "(?=.*[a-z])(?=.*\\W).*|(?=.{8,})(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*|(?=.{8,})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$");
                    Matcher matcher = pattern.matcher(userPwd);
                    if (!matcher.matches()) {
                        responseDTO.setSuccess(false);
                        responseDTO.setMsg("密码复杂度过低!");
                    } else {
                        userInfo.setModifier(SessionUtils.getUserName());
                        Date now = new Date();
                        userInfo.setLastUpdTime(now);
                        userInfo.setPwdLastUpd(now);
                        userInfoService.updateUserInfo(userInfo, responseDTO);
                    }

                } else {
                    responseDTO.setSuccess(false);
                    responseDTO.setMsg("短信验证失败,请稍后发送短信重试!");
                    SessionUtils.setSmsVerifyCode(RandomMath.getNum(6));
                }
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg("用户不存在");
            }
        } catch (Exception e) {
            log.error("修改密码错误", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("修改密码异常,请刷新页面再尝试!");
        }

        return responseDTO;
    }
}
