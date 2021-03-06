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
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/bankTransDetailApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金银行交易信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/bank/trans/detail/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账户：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                        <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                               typeahead-min-length="0"   uib-typeahead="item.accountNo as item.bankCode for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    交易日期：
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
            <%--<button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <shiro:hasPermission name="reserveBankTransDetail_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list"></th>
                <th>账号</th>
                <th>交易时间</th>
                <th>交易类型</th>
                <th>资金状态</th>
                <th>交易金额</th>
                <th>本次余额</th>
                <th>借贷标识</th>
                <th>对方账号</th>
                <th>对方户名</th>
                <th>摘要</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td><input name="check" type="checkbox" ng-model="bean._checked"></td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.transDate|date:'yyyy-MM-dd'}}</td>
                <td>{{{
                    <%for (ExtTransType e : ExtTransType.values()) {%>
                    '<%=e.getCode()%>':'<%=e.getDesc()%>',
                    <%}%>
                    }[bean.transType]}}</td>
                <td>{{{
                    <%for (CashStatus e : CashStatus.values()) {%>
                    '<%=e.getCode()%>':'<%=e.getDesc()%>',
                    <%}%>
                    }[bean.cashStatus]}}</td>
                <td>{{bean.amt}}</td>
                <td>{{bean.balance}}</td>
                <td>{{{'1':'借','2':'贷'}[bean.cdFlag]}}</td>
                <td>{{bean.otherSideAccountNo}}</td>
                <td>{{bean.otherSideName}}</td>
                <td>{{bean.summary}}</td>
                <td>{{bean.bankRemark}}</td>
                <td><button type="button" ng-click="vm.checkBusiness(bean)">查看业务核对</button></td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
