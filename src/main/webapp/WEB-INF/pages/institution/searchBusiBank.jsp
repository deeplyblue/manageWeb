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
    <script src="${ctx}/build/js/views/institution/busibankApp-e8cb98b0b7.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询业务资金源配置信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/busiBank/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right" width="10%">
                    业务类型：
                <td width="25%">
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.busiType" class="{{vm.constant.inputClass}}"
                            ng-options="key as vm.getBusiDesc(key,value) for (key,value) in vm.cached.BUSINESS_FUNDING_SOURCE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right" width="10%">
                    银行类型：
                <td width="25%">
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.bankType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.BANK_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right" width="10%">
                    银行代码：
                </td>
                <td width="20%">
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}">
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
             <shiro:hasPermission name="busiBank_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
             </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th width="40%">业务类型</th>
                <th width="30%">卡类型</th>
                <th  width="25%">银行代码 </th>

            </tr>
            <tr id="busi_bank_tr">
                <td colspan="3" style="text-align:center;">无相关记录</td>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>{{vm.cached.BUSINESS_FUNDING_SOURCE[bean.busiType]}}({{bean.busiType}})</td>
                <td>{{vm.cached.BANK_TYPE[bean.bankType]}}</td>
                <td>{{bean.bankCode}}</td>

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
