<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/20
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../.././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/system/sysParticipantsMsg.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询系统参与者信息"/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/sysParticipantsMsg/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
            </tr>
            <td class="text-right">
                银行行号/支付机构编号：
            </td>
            <td>
                <input type="text" class="form-control" ng-model="vm.queryBean.code" typeahead-min-length="0" uib-typeahead="item.key as item.value for item in vm.getBankCode() | filter:$viewValue | limitTo:6"/>
            </td><td class="text-right">
               名称：
            </td>
            <td>
                <input type="text" class="form-control" ng-model="vm.queryBean.name"/>
            </td>
            <td class="text-right">
                参与者类型：
            </td>
            <td>
                <select   class="form-control" ng-model="vm.queryBean.type"
                          ng-options="key as value for (key,value) in {'01':'支付机构','02':'银行'} ">
                    <option value="">请选择</option>
                </select>
            </td>
            <tr>
                <td colspan="8" class="textCenter">
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
                <th>参与者类型</th>
                <th>银行行号/支付机构编号</th>
                <th>名称</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <td>{{{'01':'支付机构','02':'银行'}[bean.type]}}</td>
            <td>{{bean.code}}</td>
            <td>{{bean.name}}</td>
            <td>{{{'0':'正常','1':'弃用'}[bean.deleteFlag]}}</td>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
