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
    <script src="${ctx}/js/views/merchant/BankClassCfgApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询银行实现类信息   "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/bankClassCfg/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    支付机构 ：
                </td>
                <td>
                 <%--<input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}">--%>
                     <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                            typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    类路径：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.classPath" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    Action名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.actionName" class="{{vm.constant.inputClass}}">

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

        <shiro:hasPermission name="bankClassCfg_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankClassCfg_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankClassCfg_delete">
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
                <th>类路径  </th>
                <th>Action名称  </th>
                <th>银行类型 </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td> {{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td> {{vm.cached.TRANS_CODE[bean.transCode]}}</td>
                <td>{{bean.classPath}}</td>
                <td>{{bean.actionName}}</td>
                <td>{{vm.cached.BANK_TYPE[bean.bankType]}}</td>
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
