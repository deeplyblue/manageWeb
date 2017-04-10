<%--
  User: 蒯越
  Date: 2016/7/19
  Time: 18:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/refund/refundReportApp-d0600392e1.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="退款报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/refund/searchReport"
          novalidate w5c-form-validate="vm.validateOptions" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">退款日期</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDate" required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default"
                            ng-disabled="queryForm.$invalid">
                        查询
                    </button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="refund-report_download">
                        <button down-file="${ctx}/refund/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-default" down-cfg="vm.downCfg" >
                            下载
                        </button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>业务类型</th>
                <th>原订单号</th>
                <th>商户</th>
                <th>支付机构</th>
                <th>付款银行(发卡行)</th>
                <th>退款流水号</th>
                <th>申请人</th>
                <th>审核人</th>
                <th>处理人</th>
                <th>付款金额</th>
                <th>退款金额</th>
            </tr>
            </thead>
            <tbody ng-repeat="(key,value) in vm.result | groupBy:'BUSI_TYPE'">
            <tr ng-repeat="bean in value track by $index">
                <td>{{bean.BUSI_TYPE_DESC}}</td>
                <td>{{bean.OLD_ORDER_NO}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.MCHNT_CODE]}}({{bean.MCHNT_CODE}})</td>
                <td>{{vm.cached.COMANY_CODE[bean.PAY_ORG_CODE]}}({{bean.PAY_ORG_CODE}})</td>
                <td>{{vm.cached.BANK_INFO[bean.PAY_BANK]}}({{bean.PAY_BANK}})</td>
                <td>{{bean.ORDER_NO}}</td>
                <td>{{bean.REFUND_APPLICANT}}</td>
                <td>{{bean.AUDITOR}}</td>
                <td>{{bean.PROC_EMP_ID}}</td>
                <td>{{bean.PAY_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.REFUND_AMT / 100 | currency:"￥"}}</td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="7" class="text-right">总笔数:</td>
                <td>{{vm.result.length}}</td>
                <td class="text-right">退款总金额:</td>
                <td>{{vm.sumObject / 100 | currency:"￥"}}</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
