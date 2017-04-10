<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/reserve/reserveBalanceApp-3ca49b50b6.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金余额信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/balance/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.accountNo as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10" />
                </td>
                <td class="text-right">
                    交易日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.begin"
                           required/>
                    ——
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.end"
                           required/>

            </tr>
            <tr>
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <%--<button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <shiro:hasPermission name="reserveBalance_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list"></th>
                <th>账号</th>
                <th>户名</th>
                <th>业务日期</th>
                <th>备付金余额</th>
                <%--<th>业务余额</th>--%>
                <th>自有资金余额</th>
                <th>管理费</th>
                <th>利息</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td><input name="check" type="checkbox" ng-model="bean._checked"></td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.businessDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.balance}}</td>
                <%--<td>{{bean.businessBalance}}</td>--%>
                <td>{{bean.privateCapital}}</td>
                <td>{{bean.manageFee}}</td>
                <td>{{bean.interest}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
