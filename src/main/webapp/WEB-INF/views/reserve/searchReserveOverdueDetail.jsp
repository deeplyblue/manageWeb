<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div common-modal-form>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th colspan="3">逾期数据下发报文标识号：{{vm.item.batchNo}}</th>
            <input type="text" ng-hide="true" ng-model="vm.item.batchNo">
        </tr>
        </thead>
        <tbody ng-repeat="beans in vm.item.overdueDetailList track by $index">
        <tr>
            <th colspan="3">逾期数据</th>
        </tr>
        <tr>
            <td>交易发生日期</td>
            <td>交易流水号</td>
            <td>交易金额</td>
        </tr>
        <tr>
            <td>{{beans.transDate}}</td>
            <td>{{beans.orgSeqNo}}</td>
            <td>{{beans.amount}}</td>
        </tr>
        </tbody>


        <tr>
            <td colspan="3">填写说明</td>
        </tr>
        <tr>
            <td colspan="3"><textarea ng-model="vm.item.content">。。。</textarea></td>
        </tr>


    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
