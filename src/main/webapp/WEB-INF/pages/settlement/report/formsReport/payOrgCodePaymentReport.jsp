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
    <script src="${ctx}/build/js/views/settlement/report/formsReport/payOrgCodePaymentApp-8232d03088.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="商业银行支付接口支付业务统计明细表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/formsReport/payOrgCodePayment/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    汇总日期
                </td>
                <td class="col-md-10" tooltip-placement="right">
                    <input type="text" ng-model="vm.queryBean.settleDate" class="{{vm.constant.inputClass}}"
                           uib-datepicker-popup="yyyy-MM-dd"/>
                </td>
            </tr>

            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <%--<shiro:hasPermission name="mchntTransSumDay_down">--%>
                    <button down-file="${ctx}/settlement/report/formsReport/payOrgCodePayment/download" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default" >
                        下载
                    </button>
                    <%--</shiro:hasPermission>--%>

                </td>
            </tr>
        </table>
    </form>

    <div>

        <div>
            <st-table-data st-data="vm.pagination.list" st-caches="vm.cached">

                <st-table-head>
                    <table>
                        <thead>
                        <tr>
                            <td rowspan="2">支付机构名称</td>
                            <td rowspan="2">支付接口种类</td>
                            <td colspan="4">当日（T-1日）借贷记交易业务量</td>
                            <td colspan="4">当月借贷记交易业务量</td>
                            <td colspan="4">当年借贷记交易业务量</td>
                        </tr>
                        <tr>
                            <td>借方笔数</td>
                            <td>借方金额</td>
                            <td>贷方笔数</td>
                            <td>贷方金额</td>
                            <td>借方笔数</td>
                            <td>借方金额</td>
                            <td>贷方笔数</td>
                            <td>贷方金额</td>
                            <td>借方笔数</td>
                            <td>借方金额</td>
                            <td>贷方笔数</td>
                            <td>贷方金额</td>
                        </tr>
                        </thead>
                    </table>
                </st-table-head>

                <st-table-column st-head="支付机构名称" st-field="payOrgCode" st-mapping="COMANY_CODE" />
                <st-table-column st-head="支付接口种类" st-field="busiType"/>
                <st-table-column st-head="借方笔数" st-field="countDayD"/>
                <st-table-column st-head="借方金额" st-field="amtDayD" st-field-type="currency"/>
                <st-table-column st-head="贷方笔数" st-field="countDayC"/>
                <st-table-column st-head="贷方金额" st-field="amtDayC" st-field-type="currency"/>

                <st-table-column st-head="借方笔数" st-field="countMonthD"/>
                <st-table-column st-head="借方金额" st-field="amtMonthD" st-field-type="currency"/>
                <st-table-column st-head="贷方笔数" st-field="countMonthC"/>
                <st-table-column st-head="贷方金额" st-field="amtMonthC" st-field-type="currency"/>

                <st-table-column st-head="借方笔数" st-field="countYearD"/>
                <st-table-column st-head="借方金额" st-field="amtYearD" st-field-type="currency"/>
                <st-table-column st-head="贷方笔数" st-field="countYearC"/>
                <st-table-column st-head="贷方金额" st-field="amtYearC" st-field-type="currency"/>

            </st-table-data>

        </div>
    </div>

    <core:import url="../../../common/pageFoot.jsp"/>
</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
