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
    <script src="${ctx}/js/views/settlement/report/formsReport/mchntCodeFeeCfgApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="商户支付业务佣金收入结算明细表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/formsReport/mchntCodeFeeCfg/search"
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
                    <button down-file="${ctx}/settlement/report/formsReport/mchntCodeFeeCfg/download" params="vm.queryBean"
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

                            <td colspan="5">当月待结算手续费支出</td>
                            <td colspan="5">当年累计已结算手续费支付</td>
                        </tr>
                        <tr>
                            <td>业务笔数</td>
                            <td>结算金额</td>
                            <td>计费费率</td>
                            <td>计费金额</td>
                            <td>实际计费金额</td>

                            <td>业务笔数</td>
                            <td>结算金额</td>
                            <td>计费费率</td>
                            <td>计费金额</td>
                            <td>实际计费金额</td>

                        </tr>
                        </thead>
                    </table>
                </st-table-head>

                <st-table-column st-head="商户名称" st-field="mchntCode" st-mapping="MERCHANT_CODE" />
                <st-table-column st-head="业务种类" st-field="busiType" st-mapping="MCHNT_BUSI_TYPE"/>

                <st-table-column st-head="业务笔数" st-field="countMonth"/>
                <st-table-column st-head="结算金额" st-field="amtMonth" st-field-type="currency"/>
                <st-table-column st-head="计费费率" st-field="payOrgCode" />
                <st-table-column st-head="计费金额" st-field="amtDay" st-field-type="currency" />
                <st-table-column st-head="实际计费金额" st-field="feeMonth" st-field-type="currency"/>

                <st-table-column st-head="业务笔数" st-field="countYear"/>
                <st-table-column st-head="结算金额" st-field="amtYear" st-field-type="currency"/>
                <st-table-column st-head="计费费率" st-field="payOrgCode" />
                <st-table-column st-head="计费金额" st-field="feeDay" st-field-type="currency" />
                <st-table-column st-head="实际计费金额" st-field="feeYear" st-field-type="currency"/>


            </st-table-data>

        </div>
    </div>

    <core:import url="../../../common/pageFoot.jsp"/>
</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
