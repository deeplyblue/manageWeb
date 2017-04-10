<%--
  User: 蒯越
  Date: 2016/5/11
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<div>
    <table table-detail>
        <thead>
        <tr>
            <th>序号</th>
            <th>支付订单号</th>
            <th>支付机构</th>
            <th>银行请求流水号</th>
            <th>银行响应流水号</th>
            <th>支付交易金额(元)</th>
            <th>交易状态</th>
            <th>银行清算日期</th>
            <th>商户</th>
            <th>商户流水号</th>
            <th>商户交易金额(元)</th>
            <th>交易类型</th>
            <th>商户清算日期</th>
        </tr>
        </thead>
        <tbody ng-repeat="bean in vm.item.results.list">
        <tr>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{$index + 1}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.payOrderNo}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.bankReqNo}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.bankRespNo}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.payTransAmt / 100| currency:""}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.transStatus}}</td>
            <td rowspan="{{bean.payBusiErrMchntDTOs.length + 1}}">{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
            <td ng-if="bean.payBusiErrMchntDTOs.length == 0">-</td>
            <td ng-if="bean.payBusiErrMchntDTOs.length == 0">-</td>
            <td ng-if="bean.payBusiErrMchntDTOs.length == 0">-</td>
            <td ng-if="bean.payBusiErrMchntDTOs.length == 0">-</td>
            <td ng-if="bean.payBusiErrMchntDTOs.length == 0">-</td>
        </tr>
        <tr ng-repeat="dto in bean.payBusiErrMchntDTOs">
            <td>{{vm.cached.MERCHANT_CODE[dto.mchntCode]}}({{dto.mchntCode}})</td>
            <td>{{dto.busiReqNo}}</td>
            <td>{{dto.busiTransAmt / 100| currency:""}}</td>
            <td>{{dto.transCodeDesc}}</td>
            <td>{{dto.busiSettleDate | date:"yyyy-MM-dd"}}</td>
        </tr>
        </tbody>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
