<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/7/14
  Time: 19:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <style>
        .error {
            width: 1000px;
            height: 100%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 0px;
            background-image: url(img/error/404+505.png);
            background-repeat: no-repeat;
            padding-top: 380px;
        }

        .error_404 {
            margin-left: 410px;
            margin-top: 0px;
        }

        .error h1 {
            font-weight: 100;
            font-size: 30px;
            color: #fff;
            margin-left: 345px;
        }
    </style>

    <title>Title</title>
    <script type="text/javascript">
        setTimeout(function () {
            window.top.location = '${ctx}/login.jsp';
        }, 6000)

        var count = 5;
        setInterval(function () {
            document.getElementById('timeout').innerText = count + '';
            count--;
        }, 1000)
    </script>
</head>
<body scroll="no" style="overflow: hidden">

<div class="error">
    <h1>哎呀，登录超时啦！</h1>
    <div class="error_404">5秒后将自动跳转登录页面&nbsp;&nbsp;<span id="timeout" style="color: red">5</span></div>
</div>

</body>
</html>
