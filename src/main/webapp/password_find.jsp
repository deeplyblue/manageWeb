<%@ page import="com.oriental.manage.core.utils.RandomMath" %>
<%@ page import="com.oriental.manage.core.utils.SessionUtils" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./WEB-INF/views/common/taglibs.jsp" %>

<!doctype html>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>

<%
    String originKey = RandomMath.getMath(16);
    String destKey = Base64.encodeBase64String(originKey.getBytes());
    SessionUtils.setServerRandom(destKey);
%>

<head>
    <meta charset="UTF-8">
    <title>亿付数字 | 账户中心</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="icon" href="${ctx}/img/login/favicon_yf.png" type="image/x-icon"/>
    <link rel="shortcut icon" href="${ctx}/img/login/favicon.ico" type="image/x-icon"/>
    <script src="${ctx}/js/jquery/jquery.form.js"></script>
    <script src="${ctx}/js/pwdCtrl/CFCASIPInput.min.js"></script>
</head>

<body>
<div class=".container" style="margin-top:100px;width:250%;">
    <form class="form-horizontal formPwdReset" role="form">
        <div class="form-group">
            <label for="userName" class="col-sm-2 control-label">账户</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" style="width:250px;" id="userName" name="userName"
                       placeholder="用户名"><span
                    id="usernameTip" style="display:none;color:red;"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="userPhone" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" style="width:250px;" id="userPhone" name="userPhone"
                       placeholder="手机号">
            </div>
        </div>
        <div class="form-group">
            <label for="smsVerifyCode" class="col-sm-2 control-label">短信验证码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" style="width:250px;" id="smsVerifyCode" name="smsVerifyCode"
                       placeholder="短信验证码"><span id="oldpassTip" style="display:none;color:red;"></span>
                <span id="smsWrap"><a href="#" onclick="sendSmsVerifyCode()">发送短信</a></span>
                <span id="smsSecond" style="display: none;font-size: small;color: red"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="userPwd" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" style="width:250px;" id="userPwd" name="userPwd"
                       placeholder="新密码"/><span id="newpassTip" style="display:none;color:red;"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"> </label>
            <button type="button" class="btn btn-primary" id="submit" style="text-align:center;" onclick="resetPwd()">
                确认修改
            </button>
        </div>
    </form>
</div>


<script type="text/javascript">

    var sip1;
    (function initCFCA() {

        sip1 = new CFCASIPInput("userPwd", SIP_TYPE_SYSTEM_KEYBOARD);

        var serverRandom = "<%=SessionUtils.getServerRandom()%>";
//        TMD测试版本的控件，这个必须打死
//        serverRandom = 'MTIzNDU2Nzg5MDk4NzY1NA==';
        console.log(serverRandom);

        sip1.setOutputType("userPwd", OUTPUT_TYPE_ORIGINAL);
        sip1.setServerRandom("userPwd", serverRandom);
        sip1.setCipherType("userPwd", CIPHER_TYPE_RSA);
        sip1.setMinLength("userPwd", 1);
        sip1.setMaxLength("userPwd", 20);
//        sip1.setMatchRegex("userPwd", "^(?=.{8,16})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*|(?=.{8,})(?=.*[A-Z])(?=.*[a-z])" + "(?=.*\W).*|(?=.{8,})(?=.*[A-Z])(?=.*[0-9])(?=.*\W).*|(?=.{8,})(?=.*[a-z])(?=.*[0-9])(?=.*\W).*$");

        console.info('------init  end--------');

    })();

    function getEncryptValue() {
        var sip = sip1;

        var inputId = "userPwd";

        var encryptedInputValue = sip.getEncryptedInputValue(inputId);
        var errorCode = sip.getErrorCode(inputId).toString(16);
        if (errorCode != CFCA_OK) {
            alert("密码复杂度不够,请刷新页面重试");
            console.log(errorCode);
            return;
        } else {
            console.log(encryptedInputValue);
            return encodeURIComponent(encodeURIComponent(encryptedInputValue));
        }
    }

    function getEncryptClientRandom() {
        var sip = sip1;

        var inputId = "userPwd";

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

    function compile(code) {
        code = unescape(code);
        var c = String.fromCharCode(code.charCodeAt(0) + code.length);
        for (var i = 1; i < code.length; i++) {
            c += String.fromCharCode(code.charCodeAt(i) + 1);
        }
        return c;
    }

    function resetPwd() {
        var queryString = $('.formPwdReset').formSerialize();
        var fields = queryString.split("&");
        for (var index in fields) {
            var f = fields[index].split("=");
            if (!f[1]) {
                alert("请先填写所有信息!");
                return;
            }
        }


        //神奇的代码
        /*---------start------*/
        /*var fieldPwd = fields[2].split("=");
         patt = /^(?=.{8,16})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*|(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*\W).*|(?=.{8,})(?=.*[A-Z])(?=.*[0-9])(?=.*\W).*|(?=.{8,})(?=.*[a-z])(?=.*[0-9])(?=.*\W).*$/;
         if (patt.test(fieldPwd[1])) {
         fieldPwd[1] = compile(fieldPwd[1]);
         fields[2] = fieldPwd.join("=");
         queryString = fields.join("&");
         } else {
         alert("密码复杂度不够！")
         return;
         }*/
        var fieldPwd = fields[3].split("=");
        fieldPwd[1] = getEncryptValue();
        if (!fieldPwd[1]) {
            return;
        }
        fields[3] = fieldPwd.join("=");
        fields.push("clientRandom=" + getEncryptClientRandom());
        queryString = fields.join("&");
        /*--------end---------*/

        $.getJSON('${ctx}/login/resetPwd?' + queryString + '&t=' + Date.now(),
                function (data) {
                    if (data.success) {
                        alert("修改成功");
                    } else {
                        alert(data.msg);
                    }
                })
    }

    function sendSmsVerifyCode() {
        var queryString = $('.formPwdReset #userName').fieldSerialize() + '&' + $('.formPwdReset #userPhone').fieldSerialize();

        $.getJSON('${ctx}/login/smsVerifyCode?' + 't=' + Date.now() + '&' + queryString,
                function (data) {
                    if (data.success) {
                        alert("短信发送成功!");
                        var seconds = 60;
                        $('#smsWrap').hide();
                        $('#smsSecond').show();
                        var id = window.setInterval(function () {
                            $('#smsSecond').text("重置短信 " + seconds);
                            seconds--;
                            if (seconds == 0) {
                                $('#smsWrap').show();
                                $('#smsSecond').hide();
                                window.clearInterval(id);
                            }
                        }, 1000);
                    } else {
                        if (data.msg) {
                            alert(data.msg);
                        } else {
                            alert("短信发送失败!");
                        }
                    }
                })
    }
</script>
</body>
</html>