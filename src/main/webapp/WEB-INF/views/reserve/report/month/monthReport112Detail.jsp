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
    <table border="1" style="width: 1900px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead>
        <tr>
            <td width="80px" rowspan="2">交易日期</td>
            <td colspan="6">一、备付金银行账户余额</td>
            <td colspan="4">二、未达账项调整（期末余额）</td>
            <td colspan="2">三、其他调整项目(3)</td>
            <td rowspan="2">调整项合计</td>
            <td rowspan="2">四、支付机构客户资金账户余额试算值（备付金银行账户余额加减调整项及未达账后得出的数值）</td>
            <td rowspan="2">五、支付机构客户资金账户余额实际值</td>
            <td rowspan="2">六、实际值-试算值</td>
        </tr>
        <tr>
            <td>备付金银行账户余额</td>
            <td>减：备付金银行账户中未结转的备付金银行存款利息余额（累计实现的利息收入总额-累计计提的风险准备金-累计结转的利息收入）</td>
            <td>减：备付金银行账户中累计申请存放的自有资金余额（累计申请存放的-累计申请提回的）</td>
            <td>减：未结转的支付业务净收入余额（累计实现的收入-累计扣取的手续费支出-累计结转的手续费收入）</td>
            <td>加：期末以现金形式持有的客户备付金余额（累计接受的现金形式客户备付金-累计缴存备付金银行金额）</td>
            <td>减：本期期末仍存在的以自有资金先行偿付的预付卡赎回金额（累计以自有资金先行偿付金额-累计向存管银行申请结转金额）</td>
            <td>加：支付机构已增加客户资金余额，备付金银行未增加备付金银行账户余额</td>
            <td>减：支付机构已减少客户资金余额，备付金银行未减少备付金银行账户余额</td>
            <td>减：备付金银行已增加备付金银行账户余额，支付机构未增加客户资金余额</td>
            <td>加：备付金银行已减少备付金银行账户余额，支付机构未减少客户资金余额</td>
            <td>（1）在备付金银行存放的预付卡押金金额</td>
            <td>（2）未达账项中应收押金金额的合计数</td>
        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
                <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.m1/1000000}}</td>
                <td>{{bean.m2/1000000}}</td>
                <td>{{bean.m3/1000000}}</td>
                <td>{{bean.m4/1000000}}</td>
                <td>{{bean.m5/1000000}}</td>
                <td>{{bean.m6/1000000}}</td>
                <td>{{bean.m7/1000000}}</td>
                <td>{{bean.m8/1000000}}</td>
                <td>{{bean.m9/1000000}}</td>
                <td>{{bean.m10/1000000}}</td>
                <td>{{bean.other1OtherInfo1/1000000}}</td>
                <td>{{bean.other1OtherInfo2/1000000}}</td>
                <td>{{bean.m11/1000000}}</td>
                <td>{{bean.m12/1000000}}</td>
                <td>{{bean.m13/1000000}}</td>
                <td>{{bean.m14/1000000}}</td>
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
