<%--
  User: 蒯越
  Date: 2016/5/17
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/mchntChkResultApp-06cde9e669.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="商户对账结果查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/mchntChkResult/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateStart"/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateEnd"/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
<shiro:hasPermission name="mchntChkResult_download">
                    <button down-file="${ctx}/check/mchntChkResult/download"
                            params="vm.queryBean" down-cfg="vm.downCfg"
                            down-file-type="xls" class="btn btn-default">
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
                <th rowspan="2">序号</th>
                <th rowspan="2">商户</th>
                <th rowspan="2">清算日期</th>
                <th colspan="2">对账文件</th>
                <th colspan="2">平台成功</th>
                <th colspan="2">勾兑成功</th>
                <th colspan="2">勾兑失败</th>
                <th colspan="2">应付</th>
                <th colspan="2">应退</th>
                <th rowspan="2">净额</th>
                <th rowspan="2">对账状态</th>
                <th rowspan="2">备注</th>
            </tr>
            <tr>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode]}}({{bean.mchntCode}})</td>
                <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.extTransCount}}</td>
                <td>{{bean.extTransAmt/100 | currency:""}}</td>
                <td>{{bean.transCount}}</td>
                <td>{{bean.transAmt/100 | currency:""}}</td>
                <td>{{bean.chkSuccCount}}</td>
                <td>{{bean.chkSuccAmt/100 | currency:""}}</td>
                <td>{{bean.chkErrCount}}</td>
                <td>{{bean.chkErrAmt/100 | currency:""}}</td>
                <td>{{bean.transCountC}}</td>
                <td>{{bean.transAmtC/100 | currency:""}}</td>
                <td>{{bean.transCountD}}</td>
                <td>{{bean.transAmtD/100 | currency:""}}</td>
                <td>{{(bean.transAmtC/100)-(bean.transAmtD/100) |currency:""}}</td>
                <td>{{bean.statusDesc}}</td>
                <td>{{bean.remark}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td colspan="3" class="text-right">总计：</td>
                <td>{{vm.sumObject.extTransCount}}</td>
                <td>{{vm.sumObject.extTransAmt / 100 | currency:''}}</td>
                <td>{{vm.sumObject.transCount}}</td>
                <td>{{vm.sumObject.transAmt / 100 | currency:''}}</td>
                <td>{{vm.sumObject.chkSuccCount}}</td>
                <td>{{vm.sumObject.chkSuccAmt / 100 | currency:''}}</td>
                <td>{{vm.sumObject.chkErrCount}}</td>
                <td>{{vm.sumObject.chkErrAmt / 100 | currency:''}}</td>
                <td>{{vm.sumObject.transCountC}}</td>
                <td>{{vm.sumObject.transAmtC / 100 | currency:''}}</td>
                <td>{{vm.sumObject.transCountD}}</td>
                <td>{{vm.sumObject.transAmtD / 100 | currency:''}}</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
