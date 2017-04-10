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
    <script src="${ctx}/build/js/views/merchant/BankInfoApp-edcaf78aee.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询银行信息  "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/bankInfo/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    银行代码 ：
                </td>
                <td>
                 <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}"
                        typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('BANK_INFO') | filter:$viewValue | limitTo:10">

                </td>
                <td class="text-right">
                    银行名称 ：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankName" class="{{vm.constant.inputClass}}">

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
        <shiro:hasPermission name="bankInfo_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankInfo_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankInfo_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </shiro:hasPermission>
        <table table-detail >
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>银行代码 </th>
                <th>银行名称   </th>
                <th>银行简称  </th>
                <th>银行卡类型  </th>
                <th>老银行代码 </th>
                <th>logo </th>
                <th>状态 </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td> {{bean.bankCode}}</td>
                <td> {{bean.bankName}}</td>
                <td>{{bean.bankAbbrName}}</td>
                <td>
                    {{vm.cached.BANK_CARD_TYPE[bean.bankCardType]}}
                </td>
                <td>{{bean.oldBankCode}}</td>
                <td>{{bean.bankLogo}}</td>
                <td>{{vm.cached.ENABLE_FLAG[bean.enableFlag]}}</td>

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
