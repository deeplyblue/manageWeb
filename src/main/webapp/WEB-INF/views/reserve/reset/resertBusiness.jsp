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
        重置备付金基础日报:
        <hr/>
        日期：<input type="text" ng-model="vm.query.base.baseBusinessDate">格式:yyyy-MM-dd<br/>
        <button type="button" ng-click="vm.resetReportBase()">重置备付金基础日报</button>
        <hr/>
        重置备付金月报:
        <hr/>
        日期：<input type="text" ng-model="vm.query.base.reportBusinessDate">格式:yyyy-MM-dd<br/>
        <button type="button" ng-click="vm.resetReport()">重置备付金月报</button>
        <hr/>
        重新获取交易明细:
        <hr/>
        开始时间：<input type="text" ng-model="vm.query.base.startDate">格式:yyyyMMddHHmm<br/>
        结束时间：<input type="text" ng-model="vm.query.base.endDate">格式:yyyyMMddHHmm<br/>
        <button type="button" ng-click="vm.resetPayTrans()">重新获取交易明细</button>
        <hr/>
        重新生成非结算:
        <hr/>
        日期：<input type="text" ng-model="vm.query.base.NonBusinessDate">格式:yyyy-MM-dd,
        银行账号：<input type="text" ng-model="vm.query.base.accountNo"><br/>
        <button type="button" ng-click="vm.resetNonSettlement()">重新生成非结算</button>
        <hr/>
        重新生成结算流水（入金）:
        <hr/>
        日期：<input type="text" ng-model="vm.query.base.depositBusinessDate">格式:yyyy-MM-dd<br/>
        <button type="button" ng-click="vm.resetDepositSettlement()">重新生成入金结算流水</button>
        <hr/>
        重新生成结算流水（出金）:
        <hr/>
        日期：<input type="text" ng-model="vm.query.base.remitBusinessDate">格式:yyyy-MM-dd<br/>
        <button type="button" ng-click="vm.resetRemitSettlement()">重新生成出金结算流水</button>
        <hr/>
        获取交易系统的交易明细:
        <hr/>
        开始时间：<input type="text" ng-model="vm.query.base.startTime">格式:yyyyMMddHHmm<br/>
        结束时间：<input type="text" ng-model="vm.query.base.endTime">格式:yyyyMMddHHmm<br/>
        <button type="button" ng-click="vm.resetBusiPayTrans()">重新获取业务明细</button>
        备注：同步交易系统请求或成功的交易到备付金系统.
        <hr/>
        业务明细批次生成:
        <hr/>
        <button type="button" ng-click="vm.createBusiBacthNo()">业务明细批次生成</button>
        备注：将备付金系统所有未生成批次的有效的交易生成批次并上报。
        <hr/>
        处理业务数据状态为请求的订单:
        <hr/>
        <button type="button" ng-click="vm.dealReqPayTransDetail()">处理业务数据状态为请求的订单</button>
        备注：根据备付金系统已上报成功的且交易状态为请求的交易,到交易系统查询交易的状态并更新到备付金系统,若交易失败则上报人行删除。
        <hr/>
        处理超过期限的业务数据状态为请求的订单:
        <hr/>
        <button type="button" ng-click="vm.dealPayTransDetail()">处理业务数据状态为请求的订单</button>
        备注：查询备付金系统已上报成功的且交易状态为请求,超过期限的交易,上报人行删除。
        <hr/>
        初始化银行余额:
        <hr/>
        初始化账号：<input type="text" ng-model="vm.query.base.accountNo"><br/>
        初始化时间：<input type="text" ng-model="vm.query.base.businessDate">格式:yyyyMMdd<br/>
        <button type="button" ng-click="vm.initBankBalance()">初始化银行余额</button>
        <hr/>


    </form>
    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
