<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/institution/transRightApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询交易权限信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/transRight/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    支付机构
                </td>
                <td>

                    <input ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                    </input>
                </td>
                <td class="text-right">
                    分账标识
                </td>
                <td>
                    <select ng-options="key as value for (key,value) in {'0':'分账交易','1':'非分账交易'}"
                            ng-model="vm.queryBean.splitRightFlag" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    正反标识
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-options="key as value for (key,value) in {'1':'正交易','0':'反交易'}"
                            ng-model="vm.queryBean.reverseFlag" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                    </select>
                </td>
                <input type="text" ng-hide="true" ng-model="vm.queryBean.companyType"
                       ng-init="vm.queryBean.companyType='<%=CompanyType.PAY.getCode()%>'">

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
        <p class="btn-group">
<shiro:hasPermission name="transRight_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="transRight_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="transRight_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>支付机构</th>
                <th>接入渠道</th>
                <th>订单类型</th>
                <th>正反标识</th>
                <th>分账标识</th>
                <th>退款状态</th>
                <th>修改人</th>


            </tr>
            </thead>

            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'companyCode'">
            <tr ng-repeat="bean in value">
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0">
                    <input type="checkbox" ng-model="bean._checked">
                </td>
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0">
                    {{vm.cached.COMANY_CODE[bean.companyCode]}}({{bean.companyCode}})
                </td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td>{{vm.cached.TRANS_CODE_ALL[bean.transCode]}}</td>
                <td>{{{'1':'正交易','0':'反交易'}[bean.reverseFlag]}}</td>
                <td>{{{'0':'分账交易','1':'非分账交易'}[bean.splitRightFlag]}}</td>
                <td>{{vm.cached.REFUND_FLAG[bean.refundFlag]}}</td>
                <td>{{bean.modifyUser}}</td>

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
