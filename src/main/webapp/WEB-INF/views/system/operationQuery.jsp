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
    <script src="${ctx}/js/views/system/operationQueryApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="操作查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/system/operationQuery/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    用户编号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.userId" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    操作类型
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.opType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.TYPE_OPERATION">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    操作结果
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.opResult" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPERATION_RESULT">
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
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>用户编号</th>
                <%-- <th>请求路径</th>
                <th>请求参数</th>--%>
                <th>操作描述</th>
                <th>操作类型</th>
                <th>操作结果</th>
                <th>客户端IP</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.userId}}</td>
                <td>{{bean.opDesc}}</td>
                <td>{{vm.cached.TYPE_OPERATION[bean.opType]}}</td>
                <td>{{vm.cached.OPERATION_RESULT[bean.opResult]}}</td>
                <td>{{bean.clientIp}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
</body>
</html>
