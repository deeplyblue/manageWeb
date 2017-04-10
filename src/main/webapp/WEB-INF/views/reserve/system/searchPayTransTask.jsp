<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: tanglu
  Date: 2017/1/10 0010
  Time: 22:59   查看日志
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../.././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/system/payTransTaskApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查看日志 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/inif/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <input type="text" ng-model="vm.queryBean.type" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.type as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10" />
                </td>
                <td>
                    状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.TASK_STATUS">
                        <option value="" selected="true" disabled="true">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    工作日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.startDate"
                           required/>
                    ——
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"
                           required/>
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
            <%--<button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>类型</th>
                <th>工作日期</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>状态</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.type}}</td>
                <td>{{bean.workDate |date:'yyyy-MM-dd'}}</td>
                <td>{{bean.startDate|date:'yyyy-MM-dd HH:mm:ss'}}</td>
                <td>{{bean.endDate|date:'yyyy-MM-dd HH:mm:ss'}}</td>
                <td>{{vm.cached.TASK_STATUS[bean.status]}}</td>
                <td>{{bean.remarkDesc}}</td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
