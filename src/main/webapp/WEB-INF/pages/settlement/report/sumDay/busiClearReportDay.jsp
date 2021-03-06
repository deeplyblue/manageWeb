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
    <script src="${ctx}/build/js/views/settlement/report/sumDay/busiClearReportDayApp-39b6bd1578.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="业务资金结算报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/sumDay/busiClearReportDay/search"
          class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    结算日期
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
                    <shiro:hasPermission name="busiClearReportDay_down">
                        <button down-file="${ctx}/settlement/report/sumDay/busiClearReportDay/download" params="vm.queryBean"
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
                <th>业务种类</th>
                <th>商户代码</th>
                <th>待结算日期</th>
                <th>每天的待结算笔数</th>
                <th>每天的待结算金额（元）</th>
            </tr>
            </thead>
            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'busiType'">
            <tr ng-repeat="bean in value track by $index">
                <td rowspan="{{value.length - 1}}" ng-if="value.indexOf(bean) == 0 && bean.busiType != null">
                    {{vm.cached.MCHNT_BUSI_TYPE[bean.busiType]}}
                </td>
                <td colspan="2" ng-if="value.indexOf(bean) == (value.length - 1) && bean.busiType != null">
                    小计：
                </td>
                <td colspan="2" ng-if="value.length == 1 && bean.busiType == null">
                    总计：
                </td>
                <td ng-if="bean.orgCode != null">
                    {{vm.cached.MERCHANT_CODE[bean.orgCode]}}({{bean.orgCode}})
                </td>
                <td>{{bean.sumDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.countDay}}</td>
                <td>{{bean.amtDay / 100 | currency:'￥'}}</td>
            </tr>
            </tbody>

        </table>
    </div>

</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
