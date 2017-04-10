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
    <script src="${ctx}/build/js/views/mchntplatform/mchntRefundAudit-f59bb1de08.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="商户退款审核"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/mchntplatform/mchntRefund/auditSearch"
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
                    原商户订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldOrderNo" class="{{vm.constant.inputClass}}">
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
                    申请日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate" style="width: 90px"
                           required/>
                    --<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                             ng-model="vm.queryBean.endDate" style="width: 90px"
                             required/>
                </td>
                <td class="text-right">
                    申请人
                </td>
                <td>
                    <input type="text" ng-class="vm.constant.inputClass" ng-model="vm.queryBean.refundApplicant">
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" ng-disabled="queryForm.$invalid"
                            class="btn btn-default">查询
                    </button>
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
                <%--<th>平台流水号</th>--%>
                <%--<th>业务流水号</th>--%>
                <%--<th>交易类型</th>--%>
                <th>订单总金额(元)</th>
                <th>退款金额(元)</th>
                <th>退款总金额(元)</th>
                <th>申请人</th>
                <th>申请日期</th>
                <th>审核人</th>
                <th>审核日期</th>
                <th>退款状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.mchntCode}}({{vm.cached.MERCHANT_CODE[bean.mchntCode]}})</td>
                <td>{{bean.orderNo}}</td>
                <%--<td>{{bean.ourTransNo}}</td>--%>
                <%--<td>{{bean.transCode}}</td>--%>
                <td>{{bean.totalAmt/100 | currency:'￥'}}</td>
                <td>{{bean.refundAmt/100 | currency:'￥'}}</td>
                <td>{{bean.totalRefundAmount/100 | currency:'￥'}}</td>
                <td>{{bean.refundApplicant}}</td>
                <td>{{bean.refundDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.auditor}}</td>
                <td>{{bean.auditDate | date:'yyyy-MM-dd'}}</td>
                <td>{{vm.cached.REFUND_STATUS[bean.refundStatus]}}</td>

                <td>
<shiro:hasPermission name="mchntplatform-mchntRefund_audit">
                    <button ng-click="vm.doAudit(bean.id)" ng-if="bean.refundStatus == '1'">审核</button>
    </shiro:hasPermission>
                </td>
            </tr>
            <tfoot ng-if="vm.sumObject">
            <tr style="background-color:#f9f9f9">
                <td colspan="15">总条数：{{vm.sumObject.sumCount}} 条, 总金额：{{vm.sumObject.sumTotalAmt}} 元</td>
            </tr>
            </tfoot>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
