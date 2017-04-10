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

            <td colspan="25" > {{vm.item.list[0].reportYear}} 年  第{{vm.item.list[0].reportQuarter}}  季度支付机构数字电视支付业务指标统计表</td>
        </tr>

        <tr>
            <td colspan="19" ></td>
            <td colspan="6">单位：万张、万笔、亿元</td>

        </tr>
        <tr>
            <td rowspan="3" > 项目\统计</td>
            <td rowspan="3" > 许可证编号</td>
            <td  colspan="7"> 支付方式</td>
            <td  colspan="12"> 业务统计</td>
            <td  colspan="4"> 支付账户</td>

        </tr>
        <tr>

            <td colspan="2">网银支付</td>
            <td colspan="3">无卡/快捷支付</td>
            <td colspan="2">支付账户支付</td>
            <td colspan="2">消费业务</td>
            <td colspan="2">金融理财业务</td>
            <td colspan="2">自助支付业务</td>

            <td colspan="2">B2B业务</td>
            <td colspan="2">虚拟产品</td>
            <td colspan="2">其他</td>
            <td colspan="2">实名账户（个）</td>
            <td colspan="2">非实名账户（个</td>
        </tr>




        <tr>
            <td >笔数</td>
            <td >金额</td>
            <td >关联银行卡张数</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >笔数</td>
            <td >金额</td>
            <td >单位账户</td>
            <td >个人账户</td>
            <td >单位账户</td>
            <td >个人账户</td>

        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <td>{{bean.orgName}}</td>
            <td>{{bean.licenseNo}}</td>
            <td>{{bean.a01}}</td>
            <td>{{bean.a02}}</td>
            <td>{{bean.a03}}</td>
            <td>{{bean.a04}}</td>
            <td>{{bean.a05}}</td>
            <td>{{bean.a06}}</td>
            <td>{{bean.a07}}</td>
            <td>{{bean.a08}}</td>
            <td>{{bean.a09}}</td>
            <td>{{bean.a10}}</td>
            <td>{{bean.a11}}</td>
            <td>{{bean.a12}}</td>
            <td>{{bean.a13}}</td>
            <td>{{bean.a14}}</td>
            <td>{{bean.a15}}</td>
            <td>{{bean.a16}}</td>
            <td>{{bean.a17}}</td>
            <td>{{bean.a18}}</td>
            <td>{{bean.a19}}</td>
            <td>{{bean.a20}}</td>
            <td>{{bean.a21}}</td>
            <td>{{bean.a22}}</td>
            <td>{{bean.a23}}</td>

        </tr>
        <tr>
            <td>填表人:</td>
            <td colspan="2">{{vm.item.list[0].fillPerson}}</td>
            <td>复核人:</td>
            <td colspan="2">{{vm.item.list[0].checkPerson}}</td>
            <td>填表日期:</td>
            <td colspan="2">{{vm.item.list[0].fillDate}}</td>
            <td colspan="6" ></td>
        </tr>
    </table>
    <%--</st-table-data>--%>
</div>
</body>
</html>
