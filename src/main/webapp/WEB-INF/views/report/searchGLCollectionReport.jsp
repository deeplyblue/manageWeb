<%--
  总账收款核对报表
  User: 蒯越
  Date: 2016/7/7
  Time: 15:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>总账收款核对报表</title>
    <script src="${ctx}/js/views/report/gLCollectionReport.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="总账收款核对报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/report/gLCollectionReport/search"
          novalidate w5c-form-validate="vm.validateOptions">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">支付机构</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
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
                    <button down-file="${ctx}/report/downloadGLCollectionReport" params="vm.queryBean"
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
                <th rowspan="2">支付机构</th>
                <th rowspan="2">清算日期</th>
                <th colspan="2">平台成功-支付成功</th>
                <th colspan="2">平台成功-退款成功</th>
                <th colspan="2">平台成功</th>
                <th colspan="2">对账文件-支付成功</th>
                <th colspan="2">对账文件-退款成功</th>
                <th colspan="2">对账文件</th>
                <th colspan="2">应收</th>
                <th colspan="2">应退</th>
                <th rowspan="2">应收净额</th>
                <th colspan="6">银行实收</th>
                <th rowspan="2">操作</th>
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
                <th>入账日期</th>
                <th>入账金额</th>
                <th>出账日期</th>
                <th>出账金额</th>
                <th>实收净额</th>
                <th>交易所在日期</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr my-color="bean.red" my-color-true="true" my-color-code="f08080" my-color-type="font">
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td>{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.payCount}}</td>
                <td>{{bean.payAmt / 100| currency:""}}</td>
                <td>{{bean.refundCount}}</td>
                <td>{{bean.refundAmt / 100| currency:""}}</td>
                <td>{{bean.succCount}}</td>
                <td>{{bean.succAmt / 100| currency:""}}</td>
                <td>{{bean.transCountD}}</td>
                <td>{{bean.transAmtD / 100| currency:""}}</td>
                <td>{{bean.transCountC}}</td>
                <td>{{bean.transAmtC / 100| currency:""}}</td>
                <td>{{bean.fileCount}}</td>
                <td>{{bean.fileAmt / 100| currency:""}}</td>
                <td>{{bean.transCountD}}</td>
                <td>{{bean.transAmtD / 100| currency:""}}</td>
                <td>{{bean.transCountC}}</td>
                <td>{{bean.transAmtC / 100| currency:""}}</td>
                <td>{{(bean.transAmtD - bean.transAmtC) / 100| currency:""}}</td>
                <td>{{bean.paidInDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.paidInAmt / 100| currency:""}}</td>
                <td>{{bean.paidOutDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.paidOutAmt / 100| currency:""}}</td>
                <td>{{(bean.paidInAmt - bean.paidOutAmt) / 100| currency:""}}</td>
                <td>{{bean.transDates}}</td>
                <td>
                    <button ng-if="bean.red == true"
                            ng-click="vm.queryErrDetail(bean.payOrgCode,bean.bankSettleDate)"
                            class="btn btn-default">
                        查看差异明细
                    </button>
                </td>
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
