
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/8/24
  Time: 20:26
--%>
<%--
  @TODO 参考接口文档，查询展示；预留状态修改按钮【冻结】【锁定】，先不做实现
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/opcif/agreementRateReportApp-646cb8bb98.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="绑卡报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <from id="queryForm" name="queryForm" action="${ctx}/cif/agreementRateReport/search" autocomplete="off" role="form">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                  银行名称:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankName" class="{{vm.constant.inputClass}}">
                </td>

                <td class="text-right">
                    申请时间:
                </td>
                <td colspan="3">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-blur="vm.checkStartDay(vm.queryBean.startDate,vm.queryBean.endDate)"
                           ng-model="vm.queryBean.startDate" />
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-model="vm.queryBean.endDate"
                                  ng-blur="vm.checkEndDay(vm.queryBean.startDate,vm.queryBean.endDate)"  class="form-control input-sm"/>
                </td>
            </tr>
                <tr>

                </tr>

            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="agreementRateReport_download">
                        <button down-file="${ctx}/cif/agreementRateReport/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm">
                            下载
                        </button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </from>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>序号</th>
                <th>银行名称</th>
                <th>总申请数</th>
                <th>签约总数</th>
                <th>成功笔数</th>
                <th>失败笔数</th>
                <th>成功占比</th>
                <th>失败占比</th>

            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{$index+1}}</td>
                <td>{{bean.bankName}}</td>
                <td>{{bean.applyCount}}</td>
                <td>{{bean.signCount}}</td>
                <td>{{bean.signSuccessCount}}</td>
                <td>{{bean.signFailCount}}</td>
                <td>{{bean.signSuccessRate}}</td>
                <td>{{bean.signFailRate}}</td>
            </tr>
            </tbody>
        </table>


        <core:import url="../common/pageFoot.jsp"/>
    </div>
    <core:import url="../common/nav.jsp"/>
</div>
</body>

</html>
