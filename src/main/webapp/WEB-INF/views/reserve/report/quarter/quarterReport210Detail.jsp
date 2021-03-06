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

            <td colspan="9" > {{vm.item.list[0].reportYear}} 年  第{{vm.item.list[0].reportQuarter}}  季度支付机构其他支付业务统计表</td>
        </tr>

        <tr>
            <td colspan="5" ></td>
            <td colspan="4">单位：万笔、万元、个</td>

        </tr>
        <tr>
            <td rowspan="2" > 机构名称</td>
            <td rowspan="2" > 其他支付业务类型</td>
            <td  colspan="2"> 银行卡支付</td>
            <td  colspan="2"> 支付账户支付</td>
            <td  colspan="2"> 预付卡支付</td>
            <td  rowspan="2" > 商户数</td>
        </tr>

        <tr>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <td>{{bean.orgName}}</td>
            <td>{{bean.otherPayment}}</td>
            <td>{{bean.a01}}</td>
            <td>{{bean.a02}}</td>
            <td>{{bean.a03}}</td>
            <td>{{bean.a04}}</td>
            <td>{{bean.a05}}</td>
            <td>{{bean.a06}}</td>
            <td>{{bean.a07}}</td>
        </tr>
        <tr>
            <td>填表人:</td>
            <td >{{vm.item.list[0].fillPerson}}</td>
            <td>复核人:</td>
            <td >{{vm.item.list[0].checkPerson}}</td>
            <td>填表日期:</td>
            <td >{{vm.item.list[0].fillDate}}</td>
            <td colspan="3" ></td>
        </tr>
    </table>
    <%--</st-table-data>--%>
</div>
</body>
</html>
