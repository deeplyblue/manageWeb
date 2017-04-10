<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <%--<meta http-equiv="Cache-Control" content="max-age=3600"/>--%>
    <%--替换tomcat标签--%>
    <%--<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>--%>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8">

    <%--引入公共样式--%>
    <link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css">

    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/angular/angular-growl.min.css">
<%--引入外部js--%>
    <script src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/js/jquery/browser.js"></script>
    <script src="${ctx}/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/js/angular/angular.js"></script>


    <script src="${ctx}/js/views/common/app-full.min.js"></script>


    <script src="${ctx}/js/views/common/nav-jquery.js"></script>
    <script src="${ctx}/js/views/common/nav-angular.js"></script>
    <script src="${ctx}/js/views/common/directives.js"></script>

    <%--<link rel="stylesheet" href="${ctx}/css/grid/ui-grid.css"/>--%>
    <%--<script src="${ctx}/js/grid/ui-grid.js"></script>--%>

    <script type="text/javascript">
        var config = {};
        config.ctx = '${ctx}';
        config.jsonHeader = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        config.downHeaderJson = {
            headers: {
                'Content-Type': 'application/json'
            },
            responseType: 'arraybuffer'
        }
        config.downHeaderJqLike = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            responseType: 'arraybuffer'
        }
    </script>

</head>
<body>

</body>
</html>
