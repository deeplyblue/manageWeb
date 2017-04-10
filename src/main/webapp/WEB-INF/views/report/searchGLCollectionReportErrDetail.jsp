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
    <shiro:hasPermission name="bankChkErr_operation">
        <button ng-click="vm.batchHandle('02')" class="btn btn-default">补业务</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="bankChkErr_operationBank">
        <button ng-click="vm.batchHandle('03')" class="btn btn-default">退银行</button>
    </shiro:hasPermission>
    <table table-detail>
        <thead>
        <tr>
            <th>
                <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.item.results.list">
            </th>
            <th>序号</th>
            <th>支付机构</th>
            <th>银行请求流水号</th>
            <th>银行响应流水号</th>
            <th>清算日期</th>
            <th>平台清算日期</th>
            <th>商户</th>
            <th>支付订单号</th>
            <th>交易类型</th>
            <th>系统交易金额</th>
            <th>外部系统交易金额</th>
            <th>外部交易状态</th>
            <th>差异类型</th>
            <th>处理状态</th>
            <th>处理人</th>
            <th>处理时间</th>
            <th>处理方式</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody ng-repeat="bean in vm.item.results.list">
        <tr>
            <td>
                <input name="check" type="checkbox" ng-model="bean._checked">
            </td>
            <td>{{$index + 1}}</td>
            <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
            <td>{{bean.bankReqNo}}</td>
            <td>{{bean.bankRespNo}}</td>
            <td>{{bean.extSettleDate | date:"yyyy-MM-dd"}}</td>
            <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
            <td>
                    <span ng-if="bean.platformCode != null">
                        {{vm.cached.MERCHANT_CODE[bean.platformCode]}}({{bean.platformCode}})
                    </span>
            </td>
            <td>{{bean.innerPayTransNo}}</td>
            <td>{{bean.transCodeDesc}}</td>
            <td>{{bean.transAmt / 100 | currency:""}}</td>
            <td>{{bean.extTransAmt / 100 | currency:""}}</td>
            <td>{{bean.extTransStatus}}</td>
            <td>{{bean.errTypeDesc}}</td>
            <td>{{bean.handleStatusDesc}}</td>
            <td>{{bean.handler}}</td>
            <td>{{bean.handleTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td>{{bean.handleTypeDesc}}</td>
            <td>{{bean.remark}}</td>
            <td>
                <shiro:hasPermission name="bankChkErr_operation">
                    <button type="button" ng-click="vm.handle(bean.id, bean.button.split('|')[$index])"
                            class="btn btn-default" ng-repeat="buttonDesc in bean.buttonDesc.split('|')">
                        {{buttonDesc}}
                    </button>
                </shiro:hasPermission>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
