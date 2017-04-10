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
    <script src="${ctx}/build/js/views/merchant/itconfig/merchantClearPointApp-d4188cc26d.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询结算点配置信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/itCfg/point/search" class="form-inline" autocomplete="off">
        <table table-form>
            <tr>

                <td align="center"> 结算周期：
                    <select ng-model="vm.queryBean.itemKey" class="{{vm.constant.inputClass}}" ng-options="key as value for (key,value) in {'05':'周结','06':'月结'}"
                    ng-init="vm.item.itemKey='05'">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-itCfg_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-itCfg_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-itCfg_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>结算周期</th>
                <th>结算点</th>
                <th>描述</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{{'05':'周结','06':'月结'}[bean.itemKey]}}</td>
                <td>{{bean.itemValue}}</td>
                <td>{{bean.itemDesc}}</td>
                <%--<td></td>--%>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
