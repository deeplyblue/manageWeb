<%@ page import="com.oriental.manage.core.enums.ErrorCodeManage" %>
<%@ page import="com.oriental.manage.core.enums.SessionKey" %>
<%@ page import="com.oriental.manage.core.utils.RandomMath" %>
<%@ page import="com.oriental.manage.core.utils.SessionUtils" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/13
  Time: 10:18
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<%
    String originKey = RandomMath.getMath(16);
    String destKey = Base64.encodeBase64String(originKey.getBytes());
    SessionUtils.setServerRandom(destKey);
%>
<head>
    <title>后台管理系统-登录</title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="icon" href="${ctx}/img/login/favicon_yf.png" type="image/x-icon"/>
    <link rel="shortcut icon" href="${ctx}/img/login/favicon.ico" type="image/x-icon"/>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <%--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">--%>
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <%--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">--%>
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/js/jquery/jquery.form.js"></script>
    <script src="${ctx}/js/pwdCtrl/CFCASIPInput.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <%--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>--%>

    <link rel="stylesheet" href="${ctx}/css/login.css">
</head>
<body>
<!--top-->
<div class="top_box">
    <div class="top">
        <div class="logo"><img src="${ctx}/img/login/logo_yf.png" style="padding-top: 20px">
            <small style="color: #999;font-weight: normal;line-height: 5;font-size: medium;font-style: italic">后台管理
            </small>
        </div>

    </div>
</div>
<!--banner-->
<div class="banner_box">
    <div class="banner" style="background-image: url(${ctx}/img/login/bg01.png)">
        <div class="dl_top">
            <a href="#" name="mchnt" onclick="toggleTab(this.name)">商户登录</a>
            <img src="${ctx}/img/login/DLFGX.png">&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="#" name="system" onclick="toggleTab(this.name)">管理员登录</a>
        </div>

        <%--商户登录框--%>
        <div class="dl_box" id="mchnt">
            <form class="mchnt" action="login/login" method="post" autocomplete="off">
                <input placeholder="用户名" name="userName" id="mchntUserName" type="text"
                       style="background-image:url(${ctx}/img/login/zhanghu.png); background-position:10px 7px;background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;">
                <input placeholder="密码" name="userPwd" type="password" id="mchntPwd"
                       style="background-image:url(${ctx}/img/login/mima.png);background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;background-position:10px 7px;">

                <div class="yzm">
                    <input type="text" name="verifyCode" title="不区分大小写"
                           style=" background-image:url(${ctx}/img/login/YZM.png); padding-left:70PX;background-position:10px 7px;background-repeat:no-repeat; width:60px; background-color:#fff9f1; border-color:#d8d8d8;">
                    <%--<input style=" width:45px; margin-left:20px; background-color:#fff9f1; border-color:#d8d8d8;">--%>
                    <img id="mchntValidateCodeImg" src="${ctx}/login/verifyCode" onclick="changeImg('mchnt')"/>
                </div>

                <div>
                    <input type="text" name="smsVerifyCode"
                           style="width:90px; background-image:url(${ctx}/img/login/DXYZM.png); background-position:10px 7px;padding-left:95PX;background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;">
                    <span id="mchntSmsWrap"><a href="#" onclick="sendSmsVerifyCode('mchnt')">发送短信</a></span>
                    <span id="mchntSmsSecond" style="display: none;font-size: small;color: red"></span>
                </div>

                <input type="text" name="mchntCode"
                       style="width:145px; background-image:url(${ctx}/img/login/SHH.png); background-position:10px 7px;padding-left:95PX;background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;">
                <div>
                    <a href="${ctx}/password_find.jsp" style="margin-left: 240px;font-size: x-small;" target="_blank">忘记密码?</a></br>
                </div>
                <button type="button" onclick="loginAjax('mchnt')">登陆</button>
            </form>
        </div>

        <%--系统登录框--%>
        <div class="dl_box" id="system" style="display: none">
            <form class="system" action="login/login" method="post" autocomplete="off">
                <input name="userName" id="sysUserName" placeholder="用户名" type="text"
                       style="background-image:url(${ctx}/img/login/zhanghu.png); background-position:10px 7px;background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;">
                <input name="userPwd" placeholder="密码" type="password" id="sysPwd"
                       style="background-image:url(${ctx}/img/login/mima.png);background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;background-position:10px 7px;">

                <div class="yzm">
                    <input type="text" name="verifyCode" title="不区分大小写"
                           style=" background-image:url(${ctx}/img/login/YZM.png); padding-left:70PX;background-position:10px 7px;background-repeat:no-repeat; width:60px; background-color:#fff9f1; border-color:#d8d8d8;">
                    <%--<input style=" width:45px; margin-left:20px; background-color:#fff9f1; border-color:#d8d8d8;">--%>
                    <img id="systemValidateCodeImg" src="${ctx}/login/verifyCode" onclick="changeImg('system')"/>
                    <%--</br>--%>
                    <%--<a href="#" onclick="changeImg()">看不清?换一个</a>--%>
                </div>
                <div>
                    <input type="text" name="smsVerifyCode"
                           style="width:90px; background-image:url(${ctx}/img/login/DXYZM.png); background-position:10px 7px;padding-left:95PX;background-repeat:no-repeat; background-color:#fff9f1; border-color:#d8d8d8;">
                    <span id="smsWrap"><a href="#" onclick="sendSmsVerifyCode()">发送短信</a></span>
                    <span id="smsSecond" style="display: none;font-size: small;color: red"></span>
                </div>
                <div>
                    <a href="${ctx}/password_find.jsp" style="margin-left: 180px;font-size: x-small;" target="_blank">忘记密码?</a></br>
                </div>
                <button type="button" onclick="loginAjax('system')">登陆</button>
            </form>
        </div>

    </div>
</div>

<!--foot-->
<div class="foot_box">
    <span id="serverRandom" style="display: none"><%=session.getAttribute(SessionKey.SERVER_RANDOM.getCode())%></span>
    <div class="foot">亿付数字后台管理系统</div>

</div>

<script type="text/javascript">

    var sip1, sip2;
    (function initCFCA() {

        sip1 = new CFCASIPInput("mchntPwd", SIP_TYPE_SYSTEM_KEYBOARD);
        sip2 = new CFCASIPInput("sysPwd", SIP_TYPE_SYSTEM_KEYBOARD);

        var serverRandom = document.getElementById('serverRandom').innerText;
//        TMD测试版本的控件，这个必须打死
//        serverRandom = 'MTIzNDU2Nzg5MDk4NzY1NA==';
        console.log(serverRandom);

        sip1.setOutputType("mchntPwd", OUTPUT_TYPE_ORIGINAL);
        sip1.setServerRandom("mchntPwd", serverRandom);
        sip1.setCipherType("mchntPwd", CIPHER_TYPE_RSA);
        sip1.setMinLength("mchntPwd", 1);
        sip1.setMaxLength("mchntPwd", 20);

        sip2.setOutputType("sysPwd", OUTPUT_TYPE_ORIGINAL);
        sip2.setServerRandom("sysPwd", serverRandom);
        sip2.setCipherType("sysPwd", CIPHER_TYPE_RSA);
        sip2.setMinLength("sysPwd", 1);
        sip2.setMaxLength("sysPwd", 20);

        console.info('------init  end--------');

    })();

    function getSIPInput(name) {

        if (name == "mchnt") {
            return sip1;
        } else {
            return sip2;
        }
    }

    function getPwdId(name) {
        if (name == "mchnt") {
            return "mchntPwd";
        } else {
            return "sysPwd";
        }
    }

    function getEncryptValue(name) {
        var sip = getSIPInput(name);

        var inputId = getPwdId(name);

        console.log(inputId);

        var encryptedInputValue = sip.getEncryptedInputValue(inputId);
        var errorCode = sip.getErrorCode(inputId).toString(16);
        if (errorCode != CFCA_OK) {
            alert("密码格式不符");
            console.log(errorCode);
            return;
        } else {
            console.log(encryptedInputValue);
            return encodeURIComponent(encodeURIComponent(encryptedInputValue));
        }
    }

    function getEncryptClientRandom(name) {
        var sip = getSIPInput(name);

        var inputId = getPwdId(name);

        var encryptedClientRandom = sip.getEncryptedClientRandom(inputId);
        var errorCode = sip.getErrorCode(inputId).toString(16);
        if (errorCode != CFCA_OK) {
            alert("密码控件错误");
            return;
        } else {
            console.log(encryptedClientRandom);
            return encodeURIComponent(encodeURIComponent(encryptedClientRandom));
        }
    }

    function toggleTab(name) {
        $('.dl_box').attr('style', 'display: none');
        $('#' + name).attr('style', 'display: block');
        changeImg(name);
    }

    function changeImg(name) {
        document.getElementById(name + "ValidateCodeImg").src = "${ctx}/login/verifyCode?" + Math.random();
    }

    function compile(code) {
        code = unescape(code);
        var c = String.fromCharCode(code.charCodeAt(0) + code.length);
        for (var i = 1; i < code.length; i++) {
            c += String.fromCharCode(code.charCodeAt(i) + 1);
        }
        return c;
    }

    function loginAjax(name, flag) {
        var queryString = $('.' + name).formSerialize();
        //神奇的代码
        /*---------start------*/
        var fields = queryString.split("&");
        var fieldPwd = fields[1].split("=");
//        fieldPwd[1] = compile(fieldPwd[1]);
        fieldPwd[1] = getEncryptValue(name);
        if (!fieldPwd[1]) {
            location
            return;
        }
        fields[1] = fieldPwd.join("=");
        queryString = fields.join("&");
        /*--------end---------*/

        queryString += '&forceFlag=' + flag;
        queryString += '&clientRandom=' + getEncryptClientRandom(name);


        $.getJSON('${ctx}/login/login?' + queryString + '&t=' + Date.now(),
                function (data) {
                    if (data.success) {
                        if (data.code == '<%=ErrorCodeManage.LOGIN_PWD_EXPIRE.getCode()%>') {
                            alert('<%=ErrorCodeManage.LOGIN_PWD_EXPIRE.getDesc()%>');
                        }

                        console.log(data);
                        location.href = "${ctx}/home.jsp";
                    } else {
                        if (data.code == '<%=ErrorCodeManage.LOGIN_REPEAT.getCode()%>') {
                            if (confirm("该用户已登录,强制登录？")) {
                                loginAjax(name, '1');
                            }
                        } else if (data.msg.indexOf('密码过期') != -1 || data.code == '<%=ErrorCodeManage.LOGIN_REPEAT.getCode()%>' || data.code == '<%=ErrorCodeManage.FIRST_LOGIN.getCode()%>') {
                            alert(data.msg);
                            window.open("${ctx}/password_find.jsp");
                        } else {
                            alert(data.msg);
                            changeImg(name);
                        }
                    }
                })
    }

    function sendSmsVerifyCode(platform) {
        var queryString;
        if (platform){
            queryString = $('.mchnt #mchntUserName').fieldSerialize();
        }else {
            queryString = $('.system #sysUserName').fieldSerialize();
        }

        $.getJSON('${ctx}/login/smsVerifyCode?' + 't=' + Date.now() + '&' + queryString,
                function (data) {
                    if (data.success) {
                        var seconds = 60;
                        $('#smsWrap').hide();
                        $('#smsSecond').show();
                        $('#mchntSmsWrap').hide();
                        $('#mchntSmsSecond').show();
                        var id = window.setInterval(function () {
                            $('#smsSecond').text("重置短信 " + seconds);
                            $('#mchntSmsSecond').text("重置短信 " + seconds);
                            seconds = seconds - 1;
                            if (seconds < 0) {
                                $('#smsWrap').show();
                                $('#smsSecond').text("");
                                $('#smsSecond').hide();

                                $('#mchntSmsWrap').show();
                                $('#mchntSmsSecond').text("");
                                $('#mchntSmsSecond').hide();
                                window.clearInterval(id);
                            }
                        }, 1000);
                        alert("短信发送成功!");
                    } else {
                        alert("短信发送失败!");
                    }
                })
    }
</script>
</body>
</html>
