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

            <td colspan="7" >  {{vm.item.list[0].reportYear}} 年  第{{vm.item.list[0].reportQuarter}}  季度支付机构客户备付金银行统计表</td>
        </tr>

        <tr>
            <td colspan="3" ></td>
            <td colspan="4">单位：个、万元</td>

        </tr>
        <tr>
            <td >机构名称</td>
            <td >银行名称</td>
            <td >账户类型</td>
            <td >存管账户数量</td>
            <td >收付账户数量</td>
            <td >汇缴账户数量</td>
            <td >客户备付金余额</td>
        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <td ng-if="bean.bankName != '合计'">{{bean.orgName}}</td>
            <td ng-if="bean.bankName != '合计'">{{bean.bankName}}</td>
            <td ng-if="bean.bankName != '合计'">{{bean.bankType}}</td>
            <td ng-if="bean.bankName == '合计'">{{bean.bankName}}</td>
            <td ng-if="bean.bankName == '合计'">合计备付金存管银行数量: {{bean.storageBankNum}}</td>
            <td ng-if="bean.bankName == '合计'">合计备付金合作银行数量: {{bean.cooperBankNum}}</td>
            <td>{{bean.a01}}</td>
            <td>{{bean.a02}}</td>
            <td>{{bean.a03}}</td>
            <td>{{bean.a04}}</td>
        </tr>
        <tr>
            <td>填表人:</td>
            <td >{{vm.item.list[0].fillPerson}}</td>
            <td>复核人:</td>
            <td >{{vm.item.list[0].checkPerson}}</td>
            <td>填表日期:</td>
            <td >{{vm.item.list[0].fillDate}}</td>
            <td colspan="2" ></td>
        </tr>
    </table>
        <%--</st-table-data>--%>
</div>
</body>
</html>
