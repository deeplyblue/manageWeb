<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/settlement/report/sumDay/payFeeAccrueApp-2c9aade02a.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="支付银行手续费计提表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/sumDay/payFeeAccrue/search"
          class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    结算日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.clrDate" datepicker-options="{datepickerMode:month}"
                           required
                           required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="payFeeAccrue_down">
                        <button down-file="${ctx}/settlement/report/sumDay/payFeeAccrue/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-default" >
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
                <th rowspan="2">支付机构代码</th>
                <th rowspan="2">计提周期</th>
                <th colspan="2">支付</th>
                <th colspan="2">退款</th>
                <th colspan="2">小计</th>
                <th rowspan="2">佣金金额</th>
                <th rowspan="2">佣金费率</th>
            </tr>
            <tr>
                <th>支付笔数</th>
                <th>支付金额（元）</th>
                <th>退款笔数</th>
                <th>退款金额（元）</th>
                <th>支付笔数</th>
                <th>支付金额（元）</th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode]}}({{bean.orgCode}})</td>
                <td>{{bean.settleDateBegin  | date:'yyyy/MM/dd'}} — {{bean.settleDateEnd  | date:'yyyy/MM/dd'}}</td>
                <td>{{bean.receivableCount}}</td>
                <td>{{bean.receivableAmt / 100 | currency:'￥'}}</td>
                <td>{{bean.payableCount}}</td>
                <td>{{bean.payableAmt / 100 | currency:'￥'}}</td>
                <td>{{bean.payableCount + bean.receivableCount}}</td>
                <td>{{bean.payableAmt / 100 + bean.receivableAmt / 100 | currency:'￥'}}</td>
                <td>{{bean.receivableFeeAmt/ 100 - bean.payableFeeAmt / 100 | currency:'￥'}}</td>
                <td>{{bean.fixFee}}</td>
            </tr>
            </tbody>

        </table>
    </div>

</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
