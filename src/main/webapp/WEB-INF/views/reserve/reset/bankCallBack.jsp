<%@ page import="com.oriental.reserve.enums.ExtTransType" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/reset/resetBusiness.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询备付金交易类型信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" class="form-inline" autocomplete="off">
        <textarea rows="20" cols="100" ng-model="vm.item.call.message"></textarea><br/>
        <button ng-click="vm.callbackMessage()">银行信息回调补录</button>
    </form>
    <%--<core:import url="../../common/pageFoot.jsp"/>--%>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
