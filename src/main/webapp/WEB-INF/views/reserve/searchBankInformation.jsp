<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: tanglu
  Date: 2016/12/20
  Time: 16:30
  Desc：银行信息录入
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/bankInformationApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询银行信息录入 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/bank/info/search"
          class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    银行行号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode"
                           placeholder="根据简称获取银行行号"
                           class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.code as item.name for item in vm.cached.BANK_LIST | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    账号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />
                </td>
                <td class="text-right">
                    账户状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.BANK_STATUS">
                        <option value="" selected="true" disabled="true">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.operateType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'新增','02':'修改','03':'删除'} ">
                        <option value="" selected="true" disabled="true">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    删除标识：
                </td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.DELETE_STATUS ">
                        <option value="" selected="true" disabled="true">请选择</option>
                    </select>
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
            <shiro:hasPermission name="reserveInfo_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserveInfo_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserveInfo_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>银行名称</th>
                <th>账号</th>
                <th>户名</th>
                <th>开户行行号</th>
                <th>开户行名称</th>
                <th>账户类型</th>
                <th>操作类型</th>
                <th>删除标识</th>
                <th>开户地</th>
                <th>是否跨境人民币账户</th>
                <th>备付金银行性质</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index" >
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.submitMsgId)" >
                </td>
                <td>{{bean.bankCode}}</td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.openBankCode}}</td>
                <td>{{bean.openBankName}}</td>
                <td>{{vm.cached.ACCT_TYPE[bean.accountType]}}</td>
                <td>{{{'01':'新增','02':'修改','03':'删除'}[bean.operateType]}}</td>
                <td>{{vm.cached.DELETE_STATUS[bean.deleteFlag]}}</td>
                <td>{{bean.openAccountAddr}}</td>
                <td>{{vm.cached.IS_CROSS_TRANS[bean.isCrossType]}}</td>
                <td>{{vm.cached.BANK_DETAIL_TYPE[bean.bankType]}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
