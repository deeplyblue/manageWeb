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
    <script src="${ctx}/build/js/views/settlement/unityFunding/orgClearDetailAuditApp-f1b59b1852.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="机构周期结算审核"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/unityFunding/orgClearDetailAudit/search"

          role="form" autocomplete="off">
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
                <td class="text-right">审核状态</td>
                <td>
                    <select ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'未审核','02':'审核成功','03':'审核失败'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">经办日期</td>
                <td colspan="5">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.bateBegin"

                            required
                           required/>
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-model="vm.queryBean.endDate"

                                   required
                                  required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
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
                <th>经办批次号</th>
                <th>结算批次号</th>
                <th>应收笔数</th>
                <th>应收金额</th>
                <th>应退笔数</th>
                <th>应退金额</th>
                <th>结算净额</th>
                <th>应付佣金</th>
                <th>应退佣金</th>
                <th>应付净额</th>
                <th>审核状态</th>
                <th>报表类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.handleBatchNo}}</td>
                <td>{{bean.clrBatchNo}}</td>
                <td>{{bean.receivableCount}}</td>
                <td>{{bean.receivableAmt/100 | currency:'￥'}}</td>
                <td>{{bean.payableCount}}</td>
                <td>{{bean.payableAmt/100 | currency:'￥'}}</td>
                <td>{{bean.clrNetAmt/100 | currency:'￥'}}</td>
                <td>{{bean.reFeeAmt/100 | currency:'￥'}}</td>
                <td>{{bean.feeAmt/100 | currency:'￥'}}</td>
                <td>{{bean.clrAmt/100 | currency:'￥'}}</td>
                <td>{{{'01':'未审核','02':'审核成功','03':'审核失败'}[bean.auditStatus]}}</td>
                <td>{{vm.cached.REPORT_TYPE[bean.reportType]}}</td>
                <td>
<shiro:hasPermission name="orgClearDetailAudit_toAuditDetail">
    <button ng-click="vm.toAuditDetail(bean.handleBatchNo)">结算明细</button>
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
