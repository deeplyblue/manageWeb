<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/6/17
  Time: 15:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/system/systemManageApp-81ee818906.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="后台管理"/>
</core:import>
<ul ng-controller="queryCtrl">
    <li>
        <button ng-click="vm.reFlashCache()">缓存刷新</button>
    </li>
</ul>
<core:import url="../common/nav.jsp"/>
</body>
</html>
