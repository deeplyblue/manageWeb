<%--
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
    <script src="${ctx}/build/js/views/system/roleInfoApp-7e7025ba54.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="角色管理"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/system/role/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    角色名称
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.roleName" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.roleName for item in vm.getObj('ALL_ROLE',$viewValue)">
                </td>
                <td class="text-right">
                    角色类型
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.roleType" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.roleType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.ROLE_TYPE">
                        <option value="">请选择</option>
                    </select>
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
        <p class="btn-group">
        <shiro:hasPermission name="sys-role_toAddRole">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys-role_doUpdateRole">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys-role_doDeleteRole">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>角色名称</th>
                <th>角色描述</th>
                <th>角色类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.roleId)">
                </td>
                <td>{{bean.roleName}}</td>
                <td>{{bean.roleDesc}}</td>
                <td>{{vm.cached.ROLE_TYPE[bean.roleType]}}</td>
            <shiro:hasPermission name="sys-role_toRolesResources">
                <td><button ng-click="vm.resourceAllocate(bean.roleId)">权限分配</button></td>
            </shiro:hasPermission>
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
