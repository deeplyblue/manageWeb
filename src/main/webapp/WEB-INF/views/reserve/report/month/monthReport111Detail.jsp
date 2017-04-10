<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<style>

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
<div style="border: dashed grey 1px;padding:2px 0 5px 0;margin-left: inherit;margin-right: 5px;
     width: 100%;overflow:auto">
    <%--<st-table-data st-data="vm.item.list" st-caches="vm.cached">--%>
    <table border="1" style="width: 1900px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead>
        <tr>
            <td width="50px" rowspan="2">账号</td>
            <td width="80px" rowspan="2">交易日期</td>
            <td rowspan="2">期初客户资金账户余额</td>
            <td rowspan="2">期末客户资金账户余额</td>
            <td rowspan="2">客户资金账户余额净增加（减少）</td>
            <td rowspan="2">减：本期接受现金形式的客户备付金金额</td>
            <td rowspan="2">加：本期向备付金银行缴存现金备付金</td>
            <td rowspan="2">加：本期以自有资金先行赎回预付卡的金额</td>
            <td rowspan="2">减：本期向备付金存管银行办理预付卡先行赎回资金结转业务金额</td>
            <td rowspan="2">加：本期实现的手续费收入</td>
            <td rowspan="2">减：本期支付机构已增加客户资金余额，备付金银行未增加备付金银行账户余额</td>
            <td rowspan="2">加：本期支付机构已减少客户资金余额，备付金银行未减少备付金银行账户余额</td>
            <td rowspan="2">加：本期备付金银行已增加备付金银行账户余额，支付机构未增加客户资金余额</td>
            <td rowspan="2">减：本期备付金银行已减少备付金银行账户余额，支付机构未减少客户资金余额</td>
            <td rowspan="2">加：本期收到利息收入</td>
            <td rowspan="2">加：本期申请存放的自有资金</td>
            <td rowspan="2">减：本期银行扣取的手续费、管理费等费用</td>
            <td rowspan="2">减：本期结转的风险准备金</td>
            <td rowspan="2">减：本期结转利息收入</td>
            <td rowspan="2">减：本期结转手续费收入</td>
            <td rowspan="2">减：本期提出原申请存放的自有资金</td>
            <td colspan="2">其它调整项(3)</td>
            <td rowspan="2">调整项合计</td>
            <td rowspan="2">试算值：（客户资金账户余额变动加减调整项后的数值）</td>
            <td rowspan="2">备付金银行账户余额期初值</td>
            <td rowspan="2">备付金银行账户余额期末值</td>
            <td rowspan="2">备付金银行账户余额净增加（减少）</td>
        </tr>
        <tr>
            <td>（1）在备付金银行存放的预付卡押金本期净增加数</td>
            <td>（2）本期新增应收但未收到的预付卡押金金额</td>
        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <%--<td><input name="check" type="checkbox" ng-model="bean._checked"></td>--%>
                <td>{{bean.account == null ? '汇总' : bean.account}}</td>
                <td>{{bean.tradeDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.l1/1000000}}</td>
                <td>{{bean.l2/1000000}}</td>
                <td>{{bean.l3/1000000}}</td>
                <td>{{bean.l4/1000000}}</td>
                <td>{{bean.l5/1000000}}</td>
                <td>{{bean.l6/1000000}}</td>
                <td>{{bean.l7/1000000}}</td>
                <td>{{bean.l8/1000000}}</td>
                <td>{{bean.l9/1000000}}</td>
                <td>{{bean.l10/1000000}}</td>
                <td>{{bean.l11/1000000}}</td>
                <td>{{bean.l12/1000000}}</td>
                <td>{{bean.l13/1000000}}</td>
                <td>{{bean.l14/1000000}}</td>
                <td>{{bean.l15/1000000}}</td>
                <td>{{bean.l16/1000000}}</td>
                <td>{{bean.l17/1000000}}</td>
                <td>{{bean.l18/1000000}}</td>
                <td>{{bean.l19/1000000}}</td>
                <td>{{bean.other1OtherInfo1/1000000}}</td>
                <td>{{bean.other1OtherInfo2/1000000}}</td>
                <td>{{bean.l20/1000000}}</td>
                <td>{{bean.l21/1000000}}</td>
                <td>{{bean.l22/1000000}}</td>
                <td>{{bean.l23/1000000}}</td>
                <td>{{bean.l24/1000000}}</td>
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
