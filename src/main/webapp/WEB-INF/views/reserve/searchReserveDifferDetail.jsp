<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div common-modal-form>
    <%--<table table-detail>--%>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>差异数据下发报文标识号：{{vm.item.batchMsgId}}</th>
            <input type="text" ng-hide="true" ng-model="vm.item.batchMsgId">
        </tr>
        </thead>
        <tbody ng-repeat="beans in vm.item.differDetailList track by $index">
        <tr>
            <th colspan="5">差异明细</th>
        </tr>
        <tr>
            <td>差异类型</td>
            <td>{{{'01':'银行多','02':'支付机构多','03':'支付机构结算单有未匹配的交易明细','04':'支付机构计算单明细总金额与对应实时交易总金额不符'}[beans.diffType]}}</td>
            <td>出入金标志</td>
            <td>{{{'01':'出金','02':'入金'}[beans.depositOrWithdraw]}}</td>
        </tr>
        <tr>
            <td colspan="5">支付机构上送数据</td>
        </tr>
        <tr>
            <td>原报文标识号</td>
            <td>支付机构编号</td>
            <td>交易关联账号</td>
            <td>交易金额</td>
            <td>原交易流水号</td>
        </tr>
        <tr>
            <td>{{beans.orgOriMsgId}}</td>
            <td>{{beans.orgAcct}}</td>
            <td>{{beans.orgRefAccount}}</td>
            <td>{{beans.orgAmount}}</td>
            <td>无</td>
        </tr>
        <tr>
            <td colspan="5">银行上送数据</td>
        </tr>
        <tr>
            <td>原报文标识号</td>
            <td>支付机构编号</td>
            <td>交易关联账号</td>
            <td>交易金额</td>
            <td>原交易流水号</td>
        </tr>
        <tr>
            <td>{{beans.bkOriMsgId}}</td>
            <td>{{beans.bkOrgAcct}}</td>
            <td>{{beans.bkRefAccount}}</td>
            <td>{{beans.bkAmount}}</td>
            <td>{{beans.bkOriOrgSeqNo}}</td>
        </tr>
        <tr>
            <th colspan="5">未匹配数据</th>
        </tr>
        <tr>
            <td>交易明细序号</td>
            <td colspan="4">{{beans.diffOrgSeqNos}}</td>
        </tr>
        </tbody>


        <tr>
            <td colspan="5">填写差异说明</td>
        </tr>
        <tr>
            <td colspan="5"><textarea ng-model="vm.item.content">。。。</textarea></td>
        </tr>


    </table>
</div>
<%--<core:import url="../common/nav.jsp"/>--%>
</body>
</html>
