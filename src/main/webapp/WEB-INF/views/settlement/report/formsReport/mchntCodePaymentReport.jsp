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
    <script src="${ctx}/js/views/settlement/report/formsReport/mchntCodePaymentApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="商户委托支付业务统计报表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/formsReport/mchntCodePayment/search"
          novalidate w5c-form-validate="vm.validateOptions"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    汇总日期
                </td>
                <td class="col-md-10" tooltip-placement="right">
                    <input type="text" ng-model="vm.queryBean.settleDate" class="{{vm.constant.inputClass}}"
                           required name="settleDate" placeholder="必填项"
                           uib-datepicker-popup="yyyy-MM-dd"/>
                </td>
            </tr>

            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()"  ng-disabled="queryForm.$invalid" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <%--<shiro:hasPermission name="mchntTransSumDay_down">--%>
                    <button down-file="${ctx}/settlement/report/formsReport/mchntCodePayment/download" params="vm.queryBean"
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
                            <td rowspan="2">商户名称</td>
                            <td rowspan="2">业务种类</td>
                            <td colspan="2">当日（T-1日）发生业务量</td>
                            <td colspan="2">当月发生业务量</td>
                            <td colspan="2">当年发生业务量</td>
                        </tr>
                        <tr>
                            <td>笔数</td>
                            <td>金额</td>
                            <td>笔数</td>
                            <td>金额</td>
                            <td>笔数</td>
                            <td>金额</td>


                        </tr>
                        </thead>
                    </table>
                </st-table-head>

                <st-table-column st-head="商户名称" st-field="mchntCode" st-mapping="MERCHANT_CODE" ng-no-merge="true" />
                <st-table-column st-head="业务种类" st-field="busiType" ng-no-merge="true" st-mapping="MCHNT_BUSI_TYPE" />
                <st-table-column st-head="笔数" st-field="countDay" ng-no-merge="true"/>
                <st-table-column st-head="金额" st-field="amtDay" st-field-type="currency" ng-no-merge="true"/>
                <st-table-column st-head="笔数" st-field="countMonth"/>
                <st-table-column st-head="金额" st-field="amtMonth" st-field-type="currency" ng-no-merge="true"/>

                <st-table-column st-head="笔数" st-field="countYear" ng-no-merge="true"/>
                <st-table-column st-head="金额" st-field="amtYear" st-field-type="currency" ng-no-merge="true"/>


            </st-table-data>

        </div>
    </div>

    <core:import url="../../../common/pageFoot.jsp"/>
</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
