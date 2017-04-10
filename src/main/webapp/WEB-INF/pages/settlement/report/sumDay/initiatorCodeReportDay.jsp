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
    <script src="${ctx}/build/js/views/settlement/report/sumDay/initiatorCodeReportDayApp-b7c59b0b8a.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="渠道报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/sumDay/initiatorCodeReportDay/search"
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
                    <shiro:hasPermission name="initiatorCodeReportDay_down">
                        <button down-file="${ctx}/settlement/report/sumDay/initiatorCodeReportDay/download" params="vm.queryBean"
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
                <th>渠道</th>
                <th>业务种类</th>
                <th>当日业务笔数</th>
                <th>当日业务金额（元）</th>
                <th>当月业务笔数</th>
                <th>当月业务金额（元）</th>
                <th>当年业务笔数</th>
                <th>当年业务金额（元）</th>
            </tr>
            </thead>
            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'connChannel'">
            <tr ng-repeat="bean in value track by $index">
                <td rowspan="{{value.length - 1}}" ng-if="value.indexOf(bean) == 0 && bean.connChannel != null">
                    {{vm.cached.CONN_CHANNEL[bean.connChannel]}}
                </td>
                <td colspan="2" ng-if="value.indexOf(bean) == (value.length - 1) && bean.connChannel != null">
                    小计：
                </td>
                <td colspan="2" ng-if="value.length == 1 && bean.connChannel == null">
                    总计：
                </td>
                <td ng-if="bean.busiType != null">
                    {{vm.cached.MCHNT_BUSI_TYPE[bean.busiType]}}
                </td>
                <td>{{bean.countDay}}</td>
                <td>{{bean.amtDay / 100 | currency:'￥'}}</td>
                <td>{{bean.countMonth}}</td>
                <td>{{bean.amtMonth / 100 | currency:'￥'}}</td>
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
