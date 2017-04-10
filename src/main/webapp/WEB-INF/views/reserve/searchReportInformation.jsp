
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/reserveReportInformation.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询商户上报信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/bank/update/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    支付机构编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgNO" class="form-control" />

                </td>
                <td class="text-right">
                    商户编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantNO" class="form-control" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.operateType"
                            ng-options="key as value for (key,value) in vm.cached.BUSINESS_STATUS_FOR_RESERVE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    交易日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text"  class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.startDate"
                           />
                    ——
                    <input type="text"  class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"
                           />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    删除标识：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.deleteFlag"
                            ng-options="key as value for (key,value) in vm.cached.DELETE_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr class="text-center">
                <td colspan="4" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <%--<button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i></button>
            <shiro:hasPermission name="reserveBankTransDetail_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <%--<th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list"></th>--%>
                <th>支付机构</th>
                <th>商户编号</th>
                <th>商户类型</th>
                <th>商户名称</th>
                <th>商户结算周期</th>
                <th>商户结算账户</th>
                <th>商户结算账户户名</th>
                <th>手续费费率</th>

                <th>查看详情</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list " >
            <tr  ng-if="bean.flag=='1'" style="color: red">
                  <td>{{bean.orgNO}}</td>
                  <td>{{bean.merchantNO}}</td>
                  <td>{{bean.merchantType}}</td>
                  <td>{{bean.merchantName}}</td>
                  <td>{{bean.merchantSettCycle}}</td>
                  <td>{{bean.merchantAcct}}</td>
                  <td>{{bean.merchantAcctName}}</td>
                  <td>{{bean.feeRate}}</td>
                <td><button type="button" ng-click="vm.queryInformtionDetail(bean)">查看</button>
                    </td>
            </tr>
            <tr  ng-if="bean.flag=='0'">
                <td>{{bean.orgNO}}</td>
                <td>{{bean.merchantNO}}</td>
                <td>{{bean.merchantType}}</td>
                <td>{{bean.merchantName}}</td>
                <td>{{bean.merchantSettCycle}}</td>
                <td>{{bean.merchantAcct}}</td>
                <td>{{bean.merchantAcctName}}</td>
                <td>{{bean.feeRate}}</td>
                <td><button type="button" ng-click="vm.queryInformtionDetail(bean)">查看</button>
                </td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
