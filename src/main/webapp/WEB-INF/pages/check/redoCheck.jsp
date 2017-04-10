<%--
  User: 蒯越
  Date: 2016/5/10
  Time: 17:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/redoCheckApp-41c0bfe653.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="重跑对账"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">机构或商户代码</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('ALL_COMPANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.settleDate"/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.rollBackDownloadAndChkData()" class="btn btn-default">回滚下载与对账数据</button>
                    <button type="button" ng-click="vm.rollBackChkData()" class="btn btn-default">仅回滚对账数据</button>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.startDownload()" class="btn btn-default">启动下载</button>
                    <button type="button" ng-click="vm.startCheck()" class="btn btn-default">启动对账</button>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.startInnerCheck()" class="btn btn-default">启动内部勾兑</button>
                    <button type="button" ng-click="vm.startChkFileCreate()" class="btn btn-default">启动对账文件生成</button>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.startForceCheck()" class="btn btn-default">启动强制对账</button>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.countBankChkComplete()" class="btn btn-default">统计支付机构对账情况</button>
                </td>
            </tr>
            <tr align="center">
                <td colspan="4">
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
