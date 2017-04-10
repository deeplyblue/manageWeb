<%@ page import="com.oriental.reserve.enums.MessageStatus" %>
<%@ page import="com.oriental.reserve.enums.ExtTransType" %>
<%@ page import="com.oriental.reserve.enums.CashStatus" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/trans/periodCheckApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询应收应付明细"/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/period/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    机构号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                        <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                               typeahead-min-length="0" />

                </td>
                <td class="text-right">
                    结算日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                        <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                               ng-model="vm.queryBean.queryStartDate"
                               required/>
                    ——
                        <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                               ng-model="vm.queryBean.queryEndDate"
                               required/>

            </td>
            </tr>
            <tr class="text-center">
                <td colspan="4" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list"></th>
                <th>机构</th>
                <th>清算开始时间</th>
                <th>清算结束时间</th>
                <th>结算时间</th>
                <th>应收金额</th>
                <th>应收笔数</th>
                <th>应收手续费</th>
                <th>应付金额</th>
                <th>应付笔数</th>
                <th>应付手续费</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td><input name="check" type="checkbox" ng-model="bean._checked"></td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.startDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.endDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.clearDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.receivableAmt}}</td>
                <td>{{bean.receivableCount}}</td>
                <td>{{bean.receivableFeeAmt}}</td>
                <td>{{bean.payableAmt}}</td>
                <td>{{bean.payableCount}}</td>
                <td>{{bean.payableFeeAmt}}</td>
                <td><button type="button" ng-click="vm.checkBusiness(bean)">查看资金核对</button></td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
