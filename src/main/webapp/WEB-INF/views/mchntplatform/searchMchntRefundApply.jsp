<%@ page import="com.oriental.manage.core.enums.SessionKey" %><%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/7/6
  Time: 15:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/mchntplatform/mchntRefundApply.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="商户退款申请"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/mchntplatform/mchntRefund/search"
          novalidate w5c-form-validate autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    商户号
                </td>
                <td>
                    <input type="text" value="<%=session.getAttribute(SessionKey.MCHNT_CODE.getCode())%>"
                           readonly="readonly"
                           class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    商户订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    平台流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.ourTransNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <%--<td class="text-right">
                    业务流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.busiNo" class="{{vm.constant.inputClass}}">
                </td>--%>
                <td class="text-right">
                    订单开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginOrderDate"
                           required/>
                </td>
                <td class="text-right">
                    订单结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endOrderDate"
                           required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" ng-disabled="queryForm.$invalid" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>商户代码</th>
                <th>订单号</th>
                <th>平台流水号</th>
                <%--<th>业务流水号</th>--%>
                <th>交易类型</th>
                <th>交易日期</th>
                <th>交易金额(元)</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.mchntCode}}({{vm.cached.MERCHANT_CODE[bean.mchntCode]}})</td>
                <td>{{bean.orderNo}}</td>
                <td>{{bean.ourTransNo}}</td>
                <td>{{bean.transCode}}</td>
                <td>{{bean.orderDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.transAmt/100 | currency:'￥'}}</td>

                <td><shiro:hasPermission name="mchntplatform-mchntRefund_refundApply">
                    <button ng-click="vm.doRefund(bean.ourTransNo)">全额退款</button>
                    </shiro:hasPermission>
                </td>
            </tr>

            </tbody>
            <tfoot ng-if="vm.sumObject">
            <tr style="background-color:#f9f9f9">
                <td colspan="15">总条数：{{vm.sumObject.sumCount}} 条, 总金额：{{vm.sumObject.sumTotalAmt}} 元</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
