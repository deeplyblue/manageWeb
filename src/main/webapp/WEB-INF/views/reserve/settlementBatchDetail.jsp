<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed">
        <thead>
        <tr>
            <th>批次明细数据</th>
        </tr>
        </thead>
        <tbody >
            <tr>
                <td>交易流水号</td>
                <td>交易日期</td>
                <td>金额</td>
            </tr>
            <tr ng-repeat="bean in vm.item.list[0].settlementDetailList track by $index">
                <td>{{bean.orgSeqNo}}</td>
                <td>{{bean.tradeDate}}</td>
                <td>{{bean.amount}}</td>
            </tr>
        </tbody>


    </table>
</div>
</body>
</html>
