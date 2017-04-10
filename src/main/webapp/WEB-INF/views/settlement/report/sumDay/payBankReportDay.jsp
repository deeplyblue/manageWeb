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
    <script src="${ctx}/js/views/settlement/report/sumDay/payBankReportDayApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="商业银行支付报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/sumDay/payBankReportDay/search"
          class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    清算日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.sumDate"

                           required
                           required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="payBankReportDay_down">
                        <button down-file="${ctx}/settlement/report/sumDay/payBankReportDay/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm" >
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
                <th rowspan="2">银行名称</th>
                <th rowspan="2">机构代码</th>
                <th colspan="6">当日借（贷）记卡交易统计</th>
                <th colspan="6">当月借（贷）记卡交易统计</th>
                <th colspan="6">当年借（贷）记卡交易统计</th>
            </tr>
            <tr>
                <th>借/笔</th>
                <th>借（元）</th>
                <th>贷/笔</th>
                <th>贷（元）</th>
                <th>总计/笔</th>
                <th>总计（元）</th>
                <th>借/笔</th>
                <th>借（元）</th>
                <th>贷/笔</th>
                <th>贷（元）</th>
                <th>总计/笔</th>
                <th>总计（元）</th>
                <th>借/笔</th>
                <th>借（元）</th>
                <th>贷/笔</th>
                <th>贷（元）</th>
                <th>总计/笔</th>
                <th>总计（元）</th>
            </tr>
            </thead>
            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'payBankCode'">
            <tr ng-repeat="bean in value track by $index">
                <td rowspan="{{value.length - 1}}" ng-if="value.indexOf(bean) == 0 && bean.payBankCode != null">
                    {{vm.cached.PAY_BANK_CODE[bean.payBankCode]}}
                </td>
                <td colspan="2" ng-if="value.indexOf(bean) == (value.length - 1) && bean.payBankCode != null">
                    小计：
                </td>
                <td colspan="2" ng-if="value.length == 1 && bean.payBankCode == null">
                    总计：
                </td>
                <td ng-if="bean.orgCode != null">
                    {{vm.cached.COMANY_CODE[bean.orgCode]}}({{bean.orgCode}})
                </td>
                <td>{{bean.countDayD}}</td>
                <td>{{bean.amtDayD / 100 | currency:'￥'}}</td>
                <td>{{bean.countDayC}}</td>
                <td>{{bean.amtDayC / 100 | currency:'￥'}}</td>
                <td>{{bean.countDay}}</td>
                <td>{{bean.amtDay / 100 | currency:'￥'}}</td>
                <td>{{bean.countMonthD}}</td>
                <td>{{bean.amtMonthD / 100 | currency:'￥'}}</td>
                <td>{{bean.countMonthC}}</td>
                <td>{{bean.amtMonthC / 100 | currency:'￥'}}</td>
                <td>{{bean.countMonth}}</td>
                <td>{{bean.amtMonth / 100 | currency:'￥'}}</td>
                <td>{{bean.countYearD}}</td>
                <td>{{bean.amtYearD / 100 | currency:'￥'}}</td>
                <td>{{bean.countYearC}}</td>
                <td>{{bean.amtYearC / 100 | currency:'￥'}}</td>
                <td>{{bean.countYear}}</td>
                <td>{{bean.amtYear / 100 | currency:'￥'}}</td>
            </tr>
            </tbody>

        </table>
    </div>

</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
