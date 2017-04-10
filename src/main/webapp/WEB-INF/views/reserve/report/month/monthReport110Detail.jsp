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
    <table border="1" style="width: 1400px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead>
        <tr>
            <td width="190px" rowspan="3">账号</td>
            <td width="80px" rowspan="3">交易日期</td>
            <td colspan="6">支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额</td>
            <td colspan="6">支付机构业务系统已减少客户资金账户余额，备付金银行未减少备付金银行账户余额</td>
            <td colspan="6">备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户余额</td>
            <td colspan="6">备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户余额</td>
        </tr>
        <tr>
            <td colspan="2">&lt;=5日</td>
            <td colspan="2">6-10日</td>
            <td colspan="2">&gt;11日</td>
            <td colspan="2">&lt;=5日</td>
            <td colspan="2">6-10日</td>
            <td colspan="2">&gt;11日</td>
            <td colspan="2">&lt;=5日</td>
            <td colspan="2">6-10日</td>
            <td colspan="2">&gt;11日</td>
            <td colspan="2">&lt;=5日</td>
            <td colspan="2">6-10日</td>
            <td colspan="2">&gt;11日</td>
        </tr>
        <tr>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
            <td>笔数</td>
            <td>金额</td>
        </tr>
        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
                <td>{{bean.account == null ? '汇总' : bean.account}}</td>
                <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.k01/1000000}}</td>
                <td>{{bean.k02/1000000}}</td>
                <td>{{bean.k03/1000000}}</td>
                <td>{{bean.k04/1000000}}</td>
                <td>{{bean.k05/1000000}}</td>
                <td>{{bean.k06/1000000}}</td>
                <td>{{bean.k07/1000000}}</td>
                <td>{{bean.k08/1000000}}</td>
                <td>{{bean.k09/1000000}}</td>
                <td>{{bean.k10/1000000}}</td>
                <td>{{bean.k11/1000000}}</td>
                <td>{{bean.k12/1000000}}</td>
                <td>{{bean.k13/1000000}}</td>
                <td>{{bean.k14/1000000}}</td>
                <td>{{bean.k15/1000000}}</td>
                <td>{{bean.k16/1000000}}</td>
                <td>{{bean.k17/1000000}}</td>
                <td>{{bean.k18/1000000}}</td>
                <td>{{bean.k19/1000000}}</td>
                <td>{{bean.k20/1000000}}</td>
                <td>{{bean.k21/1000000}}</td>
                <td>{{bean.k22/1000000}}</td>
                <td>{{bean.k23/1000000}}</td>
                <td>{{bean.k24/1000000}}</td>
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
