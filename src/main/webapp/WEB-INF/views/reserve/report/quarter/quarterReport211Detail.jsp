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

            <td colspan="15" > {{vm.item.list[0].reportYear}} 年  第{{vm.item.list[0].reportQuarter}}  季度业务系统备付金余额与备付金银行存款余额差异说明</td>
        </tr>


        <tr>
            <td  rowspan="2" > 机构名称</td>
            <td  rowspan="2" > 许可证编号</td>
            <td  rowspan="2"> 业务系统备付金余额（万元）</td>
            <td  rowspan="2"> 备付金银行存款余额（万元）</td>
            <td  rowspan="2"> 业务系统备付金余额与备付金银行存款余额差异的构成及原因说明</td>

        </tr>

        </thead>
        <tr ng-repeat="bean in vm.item.list track by $index">
            <td>{{bean.orgName}}</td>
            <td>{{bean.licenseNo}}</td>
            <td>{{bean.a01}}</td>
            <td>{{bean.a02}}</td>
            <td>{{bean.a03}}</td>

        </tr>

        <tr>
            <td>填表人:</td>
            <td >{{vm.item.list[0].fillPerson}}</td>
            <td>复核人:</td>
            <td >{{vm.item.list[0].checkPerson}}</td>
            <td>填表日期:{{vm.item.list[0].fillDate}}</td>
        </tr>
    </table>
    <%--</st-table-data>--%>
</div>
</body>
</html>
