<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/09
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/institution/datadictApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询支付机构交易代码信息"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/datadict/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构交易代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.itemVal" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    支付机构交易名称
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.itemDesc" class="{{vm.constant.inputClass}}">
                </td>

            </tr>
            <input type="text" ng-model="vm.queryBean.itemType" ng-init="vm.queryBean.itemType = '02'" ng-show="false" />
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
<shiro:hasPermission name="institution-datadict_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="institution-datadict_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="institution-datadict_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>支付机构交易代码</th>
                <th>支付机构交易名称</th>
                <th>字段类型 </th>
                <th>分账交易代码 </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.itemVal}}</td>
                <td>{{bean.itemDesc}}</td>
                <td>{{bean.itemType}}</td>
                <td>{{bean.spare1}}</td>

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
