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
    <script src="${ctx}/js/views/institution/busitypeOrgRelationshipApp.js"></script>

</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="业务资金源支付机构银行强绑关系"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/busitypeOrgRelationship/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    业务类型
                </td>
                <td>
                    <select ng-model="vm.queryBean.busiType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.BUSINESS_FUNDING_SOURCE">
                        <option value="">请选择</option>
                    </select>

                </td>
                <td class="text-right">
                    支付机构
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    银行：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('BANK_INFO') | filter:$viewValue | limitTo:10"/>
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
          <shiro:hasPermission name="busitypeOrgRelationship_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
          </shiro:hasPermission>
<shiro:hasPermission name="busitypeOrgRelationship_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="busitypeOrgRelationship_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>业务类型</th>
                <th>支付机构</th>
                <th>银行类型</th>
                <th>银行代码</th>
                <th>状态</th>


            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list " >
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.BUSINESS_FUNDING_SOURCE[bean.busiType] }}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}{{bean.payOrgCode}}</td>
                <td>{{vm.cached.BANK_TYPE[bean.bankType]}}</td>
                <td>{{vm.cached.BANK_INFO[bean.bankCode]}}({{bean.bankCode}})</td>
                <td>{{vm.cached.ENABLE_FLAG[bean.enableFlag]}} </td>
            </tr>
            </tbody>

        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
