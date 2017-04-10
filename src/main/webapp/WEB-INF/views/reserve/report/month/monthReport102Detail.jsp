<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<style>
    .table {
        width: 100%;
        padding: 0;
        margin: 0;
    }

    th {
        font: bold 12px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;
        color: #4f6b72;
        border-right: 1px solid #C1DAD7;
        border-bottom: 1px solid #C1DAD7;
        border-top: 1px solid #C1DAD7;
        letter-spacing: 2px;
        text-transform: uppercase;
        text-align: left;
        padding: 6px 6px 6px 12px;
        background: #CAE8EA no-repeat;
    }

    td {
        border-right: 1px solid #C1DAD7;
        border-bottom: 1px solid #C1DAD7;
        background: #fff;
        font-size: 14px;
        padding: 6px 6px 6px 12px;
        color: #4f6b72;
    }

    td.alt {
        background: #F5FAFA;
        color: #797268;
    }

    th.spec, td.spec {
        border-left: 1px solid #C1DAD7;
    }

    /*---------for IE 5.x bug*/
    html > body td {
        font-size: 14px;
    }

    tr.select th, tr.select td {
        background-color: #CAE8EA;
        color: #797268;
    }
</style>
</head>
<body>
<div style="border: dashed grey 1px;padding:2px 0px 5px 0px;margin-left: inherit;margin-right: 5px;
     width: 100%;overflow:auto">
    <%--<st-table-data st-data="vm.item.list" st-caches="vm.cached">--%>
    <table border="1" style="width: 1300px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead style="width: 1300px;">
        <tr>
            <td rowspan="4">账户</td>
            <td rowspan="4">日期</td>
            <td colspan="5">支付机构业务系统中已反映的出金业务</td>
            <td colspan="4">支付机构业务系统中未反映，但银行已扣款</td>
        </tr>
        <tr>
            <td rowspan="3">本期业务系统中借记客户资金账户金额</td>
            <td rowspan="3">本期业务应出金金额</td>
            <td rowspan="3">本期手续费等收入（支出）</td>
            <td rowspan="3">本期扣款的金额</td>
            <td colspan="3">当期处理</td>
        </tr>
        <tr>
            <td rowspan="2">本期系统反映，本期出金</td>
            <td rowspan="2">前期系统反映，本期出金</td>
            <td colspan="2">补入账</td>
            <td rowspan="2">银行返回款项</td>
        </tr>
        <tr>
            <td>借记客户资金账户金额</td>
            <td>手续费收入等（支出）</td>
        </tr>
        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
            <td>{{bean.bankCode == null ? '汇总' : bean.bankCode}}</td>
            <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
            <td>{{bean.b01/1000000}}</td>
            <td>{{bean.b02/1000000}}</td>
            <td>{{bean.b03/1000000}}</td>
            <td>{{bean.b04/1000000}}</td>
            <td>{{bean.b05/1000000}}</td>
            <td>{{bean.b06/1000000}}</td>
            <td>{{bean.b07/1000000}}</td>
            <td>{{bean.b08/1000000}}</td>
            <td>{{bean.b09/1000000}}</td>
        </tr>
    </table>


    <%--<st-table-column st-head="商户名称" st-field="mchntCode" st-mapping="MERCHANT_CODE" />--%>
    <%--<st-table-column st-head="业务种类" st-field="busiType" st-mapping="MCHNT_BUSI_TYPE"/>--%>

    <%--<st-table-column st-head="业务笔数" st-field="countMonth"/>--%>
    <%--<st-table-column st-head="结算金额" st-field="amtMonth" st-field-type="currency"/>--%>
    <%--<st-table-column st-head="计费费率" st-field="payOrgCode" />--%>
    <%--<st-table-column st-head="计费金额" st-field="amtDay" st-field-type="currency" />--%>
    <%--<st-table-column st-head="实际计费金额" st-field="feeMonth" st-field-type="currency"/>--%>

    <%--<st-table-column st-head="业务笔数" st-field="countYear"/>--%>
    <%--<st-table-column st-head="结算金额" st-field="amtYear" st-field-type="currency"/>--%>
    <%--<st-table-column st-head="计费费率" st-field="payOrgCode" />--%>
    <%--<st-table-column st-head="计费金额" st-field="feeDay" st-field-type="currency" />--%>
    <%--<st-table-column st-head="实际计费金额" st-field="feeYear" st-field-type="currency"/>--%>


    <%--</st-table-data>--%>
</div>
</body>
</html>
