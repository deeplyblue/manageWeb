<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/24
  Time: 17:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../.././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/settlement/unityFunding/orgClearDetailHandleApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="机构周期结算经办"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/unityFunding/orgClearDetail/search"

          role="form">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">结算批次号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.clrBatchNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">经办批次号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.handleBatchNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">结算类型</td>
                <td>
                    <select ng-model="vm.queryBean.clrType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算日期
                </td>
                <td colspan="3">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.clrDateBegin"

                            required
                           required/>
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-model="vm.queryBean.clrDateEnd"

                                   required
                                  required/>
                </td>
                <td class="text-right">结算状态</td>
                <td>
                    <select ng-model="vm.queryBean.clrFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_FLAG">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="8">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>

        <table table-detail>
            <thead>
            <tr>
                <td>序号</td>
                <td>结算批次号</td>
                <td>应收笔数</td>
                <td>应收金额</td>
                <td>应退笔数</td>
                <td>应退金额</td>
                <td>结算净额</td>
                <td>应付佣金</td>
                <td>应退佣金</td>
                <td>应付净额</td>
                <td>报表类型</td>
                <td>结算类型</td>
                <td>结算状态</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{$index + 1}}</td>
                <td>{{bean.clrBatchNo}}</td>
                <td>{{bean.transCountR}}</td>
                <td>{{bean.transAmtR/100 | currency:''}}</td>
                <td>{{bean.transCountP}}</td>
                <td>{{bean.transAmtP/100 | currency:''}}</td>
                <td>{{(bean.transAmtP - bean.transAmtR)/100 | currency:''}}</td>
                <td>{{bean.feeAmtP/100 | currency:''}}</td>
                <td>{{bean.feeAmtR/100 | currency:''}}</td>
                <td>{{(bean.feeAmtR - bean.feeAmtP)/100 | currency:''}}</td>
                <td>{{vm.cached.REPORT_TYPE[bean.reportType + '']}}</td>
                <td>{{vm.cached.CLR_TYPE[bean.clrType + '']}}</td>
                <td>{{vm.cached.CLR_FLAG[bean.clrFlag + '']}}</td>
                <td>
<shiro:hasPermission name="orgClearDetail_toClearDetail">
                    <button ng-click="vm.toClearDetail(bean)">结算明细</button>
    </shiro:hasPermission>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>

</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
</html>
