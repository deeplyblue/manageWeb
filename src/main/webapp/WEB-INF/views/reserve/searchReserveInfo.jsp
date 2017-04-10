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
    <script src="${ctx}/js/views/reserve/reserveInfoApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金基本信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/info/search" class="form-inline" autocomplete="off">
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
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="form-control" />
                </td>
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
            <shiro:hasPermission name="reserveInfo_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserveInfo_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserveInfo_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>账号</th>
                <th>户名</th>
                <th>账户性质</th>
                <th>交易机构代码</th>
                <th>交易商户代码</th>
                <th>银行分配机构代码</th>
                <th>银行代码</th>
                <th>类名</th>
                <th>币种</th>
                <th>sheet名称</th>
                <th>开户行</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.accountNature}}</td>
                <td>{{bean.transOrgCode}}</td>
                <td>{{bean.transMerchantCode}}</td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.bankCode}}</td>
                <td>{{bean.className}}</td>
                <td>{{{'001':'人民币','013':'港币','014':'美元'}[bean.currency]}}</td>
                <td>{{bean.sheetName}}</td>
                <td>{{bean.bankOfDeposit}}</td>
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
