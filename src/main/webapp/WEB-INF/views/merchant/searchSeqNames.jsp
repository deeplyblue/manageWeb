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
    <script src="${ctx}/js/views/merchant/SeqNameApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询支付机构序列关系配置    "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/seqNames/search" autocomplete="off">
        <table table-form>
            <tr align="center">
                <td>
                    支付机构 ：
                 <%--<input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}">--%>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10"/>
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

        <shiro:hasPermission name="seqNames_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="seqNames_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="seqNames_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </shiro:hasPermission>

        <table table-detail >
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>支付机构 </th>
                <th>订单类型   </th>

            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td> {{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td> {{bean.seqName}}</td>
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
