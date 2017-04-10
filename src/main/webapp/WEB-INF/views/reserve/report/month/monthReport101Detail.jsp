<%@ page import="com.oriental.reserve.enums.OrgType" %>
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
            <td rowspan="6">账户</td>
            <td rowspan="6">日期</td>
            <td colspan="9">支付机构业务系统中已反映的入金业务</td>
            <td colspan="5" rowspan="2">支付机构业务系统中未反映但银行已收到的款项</td>
        </tr>
        <tr>
            <td colspan="6">银行已入金金额</td>
            <td colspan="3">应收入金业务金额</td>
        </tr>
        <tr>
            <td colspan="3">本期系统反映，本期入金</td>
            <td colspan="3">前期系统反映，本期入金</td>
            <td rowspan="4">本期业务系统中已贷记客户资金账户金额</td>
            <td rowspan="4">本期手续费收入(支出)</td>
            <td rowspan="3">其它(3)</td>
            <td rowspan="4">本期收到的金额</td>
            <td colspan="4">当期处理</td>
        </tr>
        <tr>
            <td rowspan="3">业务系统中贷记客户资金账户金额</td>
            <td rowspan="3">手续费收入（支出）</td>
            <td rowspan="2">其它(1）</td>
            <td rowspan="3">业务系统中贷记客户资金账户金额</td>
            <td rowspan="3">手续费收入（支出）</td>
            <td rowspan="2">其它(2）</td>
            <td colspan="3">补入账</td>
            <td rowspan="3">退回</td>
        </tr>
        <tr>
            <td rowspan="2">业务系统中贷记客户资金账户金额</td>
            <td rowspan="2">手续费收入（支出）</td>
            <td>其他(4)</td>
        </tr>
        <tr>
            <td>押金</td>
            <td>押金</td>
            <td>押金</td>
            <td>押金</td>
        </tr>
        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
            <td>{{bean.account == null ? '汇总' : bean.account}}</td>
            <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
            <td>{{bean.a01/1000000}}</td>
            <td>{{bean.a02/1000000}}</td>
            <td>{{bean.other1OtherInfo1/1000000}}</td>
            <td>{{bean.a04/1000000}}</td>
            <td>{{bean.a05/1000000}}</td>
            <td>{{bean.other2OtherInfo1/1000000}}</td>
            <td>{{bean.a07/1000000}}</td>
            <td>{{bean.a08/1000000}}</td>
            <td>{{bean.other3OtherInfo1/1000000}}</td>
            <td>{{bean.a10/1000000}}</td>
            <td>{{bean.a11/1000000}}</td>
            <td>{{bean.a12/1000000}}</td>
            <td>{{bean.other4OtherInfo1/1000000}}</td>
            <td>{{bean.a14/1000000}}</td>
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
