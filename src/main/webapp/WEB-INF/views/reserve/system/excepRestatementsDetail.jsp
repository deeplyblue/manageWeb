<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2017/1/6
  Time: 9:53
  To change this template use File | Settings | File Templates.
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
    <table border="1" style="width: 1300px;text-align: center;word-wrap:break-word;word-break:break-all;">
        <thead style="width: 1300px;">
                <tr>
                    <td>关联机构</td>
                    <td>申请日期</td>
                    <td>申请说明</td>
                    <td>msg_id</td>
                </tr>
            </thead>
            <tbody ng-repeat="bean in vm.item.list track by $index">
                <tr>
                    <td>{{bean.refCode}}</td>
                    <td>{{bean.applyDate| date:'yyyy-MM-dd'}}</td>
                    <td>{{bean.applyInfo}}</td>
                    <td>{{bean.msgId}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
