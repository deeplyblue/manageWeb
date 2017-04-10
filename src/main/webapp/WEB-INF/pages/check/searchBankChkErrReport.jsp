<%--
  User: 蒯越
  Date: 2016/7/7
  Time: 15:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/bankChkErrReportApp-8a16b792aa.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="银行对账差异报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/bankChkErr/searchReport"
          novalidate w5c-form-validate="vm.validateOptions" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期</span>
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
                    <button down-file="${ctx}/check/bankChkErr/downloadReport" params="vm.queryBean"
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
                <th rowspan="2">业务类型</th>
                <th rowspan="2">差异类型</th>
                <th colspan="2">当日差异(未处理)</th>
                <th colspan="2">当日差异(已处理)</th>
                <th colspan="2">本月累计(未处理)</th>
                <th colspan="2">本月累计(已处理)</th>
                <th colspan="2">本年累计(未处理)</th>
                <th colspan="2">本年累计(已处理)</th>
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
            </tr>
            </thead>
            <tbody ng-repeat="(key,value) in vm.result | groupBy:'BUSI_TYPE'">
            <tr ng-repeat="bean in value track by $index">
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0 && bean.BUSI_TYPE != null">
                    {{vm.cached.MCHNT_BUSI_TYPE[bean.BUSI_TYPE]}}
                </td>
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0 && bean.BUSI_TYPE == null">
                    未知业务
                </td>
                <td ng-if="value.indexOf(bean) == (value.length - 1)">
                    小计：
                </td>
                <td ng-if="bean.ERR_TYPE != null">
                    {{bean.ERR_TYPE_DESC}}
                </td>
                <td>{{bean.NO_HANDLE_DAY_COUNT}}</td>
                <td>{{bean.NO_HANDLE_DAY_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.HANDLED_DAY_COUNT}}</td>
                <td>{{bean.HANDLED_DAY_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.NO_HANDLE_MONTH_COUNT}}</td>
                <td>{{bean.NO_HANDLE_MONTH_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.HANDLED_MONTH_COUNT}}</td>
                <td>{{bean.HANDLED_MONTH_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.NO_HANDLE_YEAR_COUNT}}</td>
                <td>{{bean.NO_HANDLE_YEAR_AMT / 100 | currency:"￥"}}</td>
                <td>{{bean.HANDLED_YEAR_COUNT}}</td>
                <td>{{bean.HANDLED_YEAR_AMT / 100 | currency:"￥"}}</td>
            </tr>
            </tbody>
            <tr>
                <td colspan="2">总计：</td>
                <td>{{vm.sumObject.NO_HANDLE_DAY_COUNT}}</td>
                <td>{{vm.sumObject.NO_HANDLE_DAY_AMT / 100 | currency:"￥"}}</td>
                <td>{{vm.sumObject.HANDLED_DAY_COUNT}}</td>
                <td>{{vm.sumObject.HANDLED_DAY_AMT / 100 | currency:"￥"}}</td>
                <td>{{vm.sumObject.NO_HANDLE_MONTH_COUNT}}</td>
                <td>{{vm.sumObject.NO_HANDLE_MONTH_AMT / 100 | currency:"￥"}}</td>
                <td>{{vm.sumObject.HANDLED_MONTH_COUNT}}</td>
                <td>{{vm.sumObject.HANDLED_MONTH_AMT / 100 | currency:"￥"}}</td>
                <td>{{vm.sumObject.NO_HANDLE_YEAR_COUNT}}</td>
                <td>{{vm.sumObject.NO_HANDLE_YEAR_AMT / 100 | currency:"￥"}}</td>
                <td>{{vm.sumObject.HANDLED_YEAR_COUNT}}</td>
                <td>{{vm.sumObject.HANDLED_YEAR_AMT / 100 | currency:"￥"}}</td>
            </tr>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
