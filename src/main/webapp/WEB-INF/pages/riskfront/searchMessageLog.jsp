<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed">

    </table>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th width="5%">序号</th>
                <th  width="15%">订单号</th>
                <th width="15%">日志日期</th>
                <th width="65%">处理结果</th>

            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.item track by $index">

                <td >{{$index}}</td>
                <td> {{bean.orderId}}</td>
                <td>{{bean.createTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.resultMsg}}</td>

            </tr>
            </tbody>
        </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
