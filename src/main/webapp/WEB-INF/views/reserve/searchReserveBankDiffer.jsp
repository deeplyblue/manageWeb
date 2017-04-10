<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/reserveBankDiffApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="银行账户信息差异查询 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/data/bankDiffer/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    报文工作日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.startDate"
                           required/>
                    ——
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"
                           required/>
                <td class="text-right">
                    银行行号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="form-control" />
                </td>
                <td class="text-right">
                    账号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />
                </td>
            </tr>
            <tr>
                <td colspan="8" class="textCenter">
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
                <th>银行行号</th>
                <th>支付机构编号</th>
                <th>账号</th>
                <th>差异类型</th>
                <th>银行提供值</th>
                <th>支付机构提供值</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.bankCode}}</td>
                <td>{{bean.orgNo}}</td>
                <td>{{bean.accountNo}}</td>
                <td>{{{'01':'户名','02':'账户类型','03':'备份金银行性质'}[bean.diffType]}}</td>
                <td>{{bean.bankValue}}</td>
                <td>{{bean.orgValue}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
