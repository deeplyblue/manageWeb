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
    <script src="${ctx}/js/views/settlement/report/sumDay/mchntFeeAccrueApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="佣金收计提表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/sumDay/mchntFeeAccrue/search"
          class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    结算日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.clrDate"

                           required
                           required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="mchntFeeAccrue_down">
                        <button down-file="${ctx}/settlement/report/sumDay/mchntFeeAccrue/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm">
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
                <th rowspan="2">商户代码</th>
                <th rowspan="2">业务种类</th>
                <th rowspan="2">计提周期</th>
                <th colspan="2">业务</th>
                <th colspan="2">退款</th>
                <th colspan="2">小计</th>
                <th rowspan="2">佣金金额（元）</th>
                <th rowspan="2">佣金费率</th>
            </tr>
            <tr>
                <th>业务笔数</th>
                <th>业务金额（元）</th>
                <th>退款笔数</th>
                <th>退款金额（元）</th>
                <th>业务笔数</th>
                <th>业务金额（元）</th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{vm.cached.MERCHANT_CODE[bean.orgCode]}}({{bean.orgCode}})</td>
                <td>{{vm.cached.MCHNT_BUSI_TYPE[bean.busiType]}}</td>
                <td>{{bean.settleDateBegin  | date:'yyyy/MM/dd'}} — {{bean.settleDateEnd  | date:'yyyy/MM/dd'}}</td>
                <td>{{bean.payableCount}}</td>
                <td>{{bean.payableAmt / 100 | currency:'￥'}}</td>
                <td>{{bean.receivableCount}}</td>
                <td>{{bean.receivableAmt / 100 | currency:'￥'}}</td>
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
