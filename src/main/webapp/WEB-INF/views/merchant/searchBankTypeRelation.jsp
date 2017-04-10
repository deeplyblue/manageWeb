<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/13
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/merchant/BankTypeRelationApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询银行类型关系信息  "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/bankTypeRelation/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    银行类型：
                </td>
                <td>
                 <%--<input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.bankTypeCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.BANK_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}">
                </td>

            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>
    <div>

        <shiro:hasPermission name="bankTypeRelation_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankTypeRelation_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankTypeRelation_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </shiro:hasPermission>
        <table table-detail >
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>银行类型 </th>
                <th>银行代码  </th>
                <th>银行代码后缀  </th>

            </tr>
            </thead>

            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'bankTypeCode'">
            <tr ng-repeat="bean in value">
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0">
                    <input type="checkbox" ng-model="bean._checked">
                </td>
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0">
                    {{vm.cached.BANK_TYPE[ bean.bankTypeCode]}}({{bean.bankTypeCode}})</td>
                <td> {{bean.bankCode}}</td>
                <td>{{bean.bankCodeSuffix}}</td>

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
