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
            margin-left: auto;
            margin-right: auto;
            margin-top: 0px;
            background-image: url(${ctx}/img/error/404+505.png);
            background-repeat: no-repeat;
            padding-top: 380px;
            width:100%;
            height:100%;
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
</head>
<body scroll="no" style="overflow: hidden">

<div class="error">
    <h1>哎呀，没有访问权限！</h1>
</div>

</body>
</html>
