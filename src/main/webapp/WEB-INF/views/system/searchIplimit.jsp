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
    <script src="${ctx}/js/views/system/iplimitApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="用户IP白名单"/>
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
                    启用标志
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.enableFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.ENABLE_LIMITFLAG">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    IP类型
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.ipType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.IP_TYPE">
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
<shiro:hasPermission name="userIp_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="userIp_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>用户编号</th>
                <th>客户端</th>
                <th>IP类型</th>
                <th>启用标志</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>记录生成日期</th>
                <th>记录最后更新日期</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.userId}}</td>
                <td>{{bean.clientIp}}</td>
                <td>{{vm.cached.IP_TYPE[bean.ipType]}}</td>
                <td>{{vm.cached.ENABLE_LIMITFLAG[bean.enableFlag]}}</td>
                <td>{{bean.ipBeginTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.ipEndTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.createTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.lastUpdTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
