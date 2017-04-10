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
    <script src="${ctx}/js/views/merchant/settleManage/merchantClearAccountApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户账户信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/settle/account/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantCode"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6" class="{{vm.constant.inputClass}} " />
                </td>
                <td class="text-right">
                    银行账户：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo"  class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr align="center">
                <td colspan="8" class="textCenter">
<%--<shiro:hasPermission name="merchant-account_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
<%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-account_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-account_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
        </p>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <%--<input type="checkbox" name="checkAll">--%>
                </th>
                <th>商户</th>
                <th>账户名</th>
                <th>标准开户行</th>
                <th>联行号</th>
                <th>账户号</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.merchantCode]}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.bankName}}</td>
                <td>{{bean.cnapsCode}}</td>
                <td>{{bean.accountNo}}</td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
