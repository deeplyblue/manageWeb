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
    <table border="1" style="width: 2600px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead>
        <tr>
            <td rowspan="3" width="80px">交易日期</td>
            <td rowspan="3" width="100px">账号</td>
            <td colspan="9">一、增加备付金银行账户余额的特殊业务：</td>
            <td colspan="14">二、减少备付金银行账户余额的特殊业务：</td>
        </tr>
        <tr>
            <td rowspan="2">1.向备付金银行缴存现金形式备付金</td>
            <td rowspan="2">2.备付金银行间头寸调拨（调入行）</td>
            <td rowspan="2">3.收到利息收入</td>
            <td rowspan="2">4.备付金非活期存款转活期存款（活期账户）</td>
            <td rowspan="2">5.备付金活期存款转非活期存款（非活期账户）</td>
            <td rowspan="2">6.当日申请存放的自有资金（存管银行）</td>
            <td rowspan="2">7.利息收入划拨(存管银行)</td>
            <td>其他</td>
            <td rowspan="2">合计</td>
            <td rowspan="2">1.备付金银行间头寸调拨（调出行）	</td>
            <td rowspan="2">2.利息收入划拨存管银行</td>
            <td rowspan="2">3.银行扣取的手续费、管理费等费用	</td>
            <td rowspan="2">4.备付金非活期存款转活期存款（非活期账户）	</td>
            <td rowspan="2">5.备付金活期存款转非活期存款（活期账户）	</td>
            <td rowspan="2">6.结转风险准备金（存管银行）	</td>
            <td rowspan="2">7.结转利息收入（存管银行）	</td>
            <td rowspan="2">8.结转手续费收入（存管银行）	</td>
            <td rowspan="2">9.办理预付卡先行现金赎回业务（存管银行）	</td>
            <td rowspan="2">10.当日提出原申请存放的自有资金（存管银行）</td>
            <td colspan="2">11.其它(3)</td>
            <td rowspan="2">合计</td>
        </tr>
        <tr>
            <td>(1)向备付金银行缴存现金形式预付卡押金</td>
            <td>(1)以转账方式退回购卡押金</td>
            <td>(2)办理预付卡押金先行现金赎回业务（存管银行）</td>
        </tr>
        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
                <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.account}}</td>
                <td>{{bean.f01/1000000}}</td>
                <td>{{bean.f02/1000000}}</td>
                <td>{{bean.f03/1000000}}</td>
                <td>{{bean.f04/1000000}}</td>
                <td>{{bean.f05/1000000}}</td>
                <td>{{bean.f06/1000000}}</td>
                <td>{{bean.f07/1000000}}</td>
                <td>{{bean.other1OtherInfo1/1000000}}</td>
                <td>{{bean.f10/1000000}}</td>
                <td>{{bean.g01/1000000}}</td>
                <td>{{bean.g02/1000000}}</td>
                <td>{{bean.g03/1000000}}</td>
                <td>{{bean.g04/1000000}}</td>
                <td>{{bean.g05/1000000}}</td>
                <td>{{bean.g06/1000000}}</td>
                <td>{{bean.g07/1000000}}</td>
                <td>{{bean.g08/1000000}}</td>
                <td>{{bean.g09/1000000}}</td>
                <td>{{bean.g10/1000000}}</td>
                <td>{{bean.other2OtherInfo1/1000000}}</td>
                <td>{{bean.other2OtherInfo2/1000000}}</td>
                <td>{{bean.g14/1000000}}</td>
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
