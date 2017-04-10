<%--
  总账付款核对报表
  User: 蒯越
  Date: 2016/7/7
  Time: 15:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>总账付款核对报表</title>
    <script src="${ctx}/js/views/report/gLPaymentReport.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="总账付款核对报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/report/gLPaymentReport/search"
          novalidate w5c-form-validate="vm.validateOptions">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateStart" required/>
                    -
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateEnd" required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default"
                            ng-disabled="queryForm.$invalid">
                        查询
                    </button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <button down-file="${ctx}/report/downloadGLPaymentReport" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default" down-cfg="vm.downCfg">
                        下载
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <table table-detail>
            <thead>
            <tr>
                <th rowspan="2">序号</th>
                <th rowspan="2">商户</th>
                <th rowspan="2">清算日期</th>
                <th colspan="2">平台成功-支付成功</th>
                <th colspan="2">平台成功-退款成功</th>
                <th colspan="2">平台成功</th>
                <th colspan="2">对账文件-支付成功</th>
                <th colspan="2">对账文件-退款成功</th>
                <th colspan="2">对账文件</th>
                <th colspan="2">应付</th>
                <th colspan="2">应退</th>
                <th rowspan="2">应付净额</th>
                <th colspan="7">银行实付</th>
            </tr>
            <tr>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>笔数</th>
                <th>金额</th>
                <th>支付机构</th>
                <th>出账日期</th>
                <th>出账金额</th>
                <th>入账日期</th>
                <th>入账金额</th>
                <th>实付净额</th>
                <th>交易所在日期</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr my-color="bean.red" my-color-true="true" my-color-code="f08080" my-color-type="font">
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{$index + 1}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">
                    {{vm.cached.MERCHANT_CODE[bean.mchntCode]}}({{bean.mchntCode}})
                </td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.busiSettleDate |
                    date:"yyyy-MM-dd"}}
                </td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.payCount}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.payAmt / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.refundCount}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.refundAmt / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.succCount}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.succAmt / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transCountD}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transAmtD / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transCountC}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transAmtC / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transCountD + bean.transCountC}}
                </td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{(bean.transAmtD + bean.transAmtC) / 100|
                    currency:""}}
                </td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transCountD}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transAmtD / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transCountC}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{bean.transAmtC / 100| currency:""}}</td>
                <td rowspan="{{bean.glPaymentReportPaidDataList.length == 0 ? 1 : bean.glPaymentReportPaidDataList.length}}">{{(bean.transAmtD - bean.transAmtC) / 100|
                    currency:""}}
                </td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length == 0">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1 && bean.glPaymentReportPaidDataList[0].payOrgCode != null">
                    {{vm.cached.COMANY_CODE[bean.glPaymentReportPaidDataList[0].payOrgCode]}}({{bean.glPaymentReportPaidDataList[0].payOrgCode}})
                </td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1 && bean.glPaymentReportPaidDataList[0].payOrgCode == null">-</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{bean.glPaymentReportPaidDataList[0].paidOutDate | date:"yyyy-MM-dd"}}</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{bean.glPaymentReportPaidDataList[0].paidOutAmt / 100| currency:""}}</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{bean.glPaymentReportPaidDataList[0].paidInDate | date:"yyyy-MM-dd"}}</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{bean.glPaymentReportPaidDataList[0].paidInAmt / 100| currency:""}}</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{(bean.glPaymentReportPaidDataList[0].paidOutAmt - bean.glPaymentReportPaidDataList[0].paidInAmt) / 100| currency:""}}</td>
                <td ng-if="bean.glPaymentReportPaidDataList.length >= 1">{{bean.glPaymentReportPaidDataList[0].transDates}}</td>
            </tr>
            <tr ng-repeat="data in bean.glPaymentReportPaidDataList" ng-if="$index != 0"
                my-color="bean.red" my-color-true="true" my-color-code="f08080" my-color-type="font">
                <td ng-if="data.payOrgCode != null">{{vm.cached.COMANY_CODE[data.payOrgCode]}}({{data.payOrgCode}})</td>
                <td ng-if="data.payOrgCode == null">-</td>
                <td>{{data.paidOutDate | date:"yyyy-MM-dd"}}</td>
                <td>{{data.paidOutAmt / 100| currency:""}}</td>
                <td>{{data.paidInDate | date:"yyyy-MM-dd"}}</td>
                <td>{{data.paidInAmt / 100| currency:""}}</td>
                <td>{{(data.paidOutAmt - data.paidInAmt) / 100| currency:""}}</td>
                <td>{{data.transDates}}</td>
            </tr>
            </tbody>
            <tr>
            </tr>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
