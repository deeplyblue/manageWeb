<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhangxinhai
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>支付机构代码</th>
                <th>清算日期</th>
                <th>应退金额（元）</th>
                <th>应退笔数</th>
                <th>应收金额（元）</th>
                <th>应收笔数</th>
                <th>应退手续费（元）</th>
                <th>应付手续费（元）</th>
                <th>结算净额（元）</th>
                <%--<th>操作</th>--%>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.item.results track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.settleDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.transAmtP / 100 | currency:''}}</td>
                <td>{{bean.transCountP}}</td>
                <td>{{bean.transAmtR / 100 | currency:''}}</td>
                <td>{{bean.transCountR}}</td>
                <td>{{bean.feeAmtR / 100 | currency:''}}</td>
                <td>{{bean.feeAmtP / 100 | currency:''}}</td>
                <td>{{(bean.transAmtP - bean.transAmtR) / 100 | currency:''}}</td>
                <%--<td>--%>
                    <%--<button ng-click="vm.redirectPaySettleDetail(bean,true)">清分明细</button>--%>
                <%--</td>--%>
            </tr>
            </tbody>
        </table>
    </div>
    <div ng-show="vm.detailFlag">
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>支付机构代码</th>
                <th>清算日期</th>
                <th>支付流水号</th>
                <th>交易代码</th>
                <th>接入渠道</th>
                <th>银行代码</th>
                <th>银行类型</th>
                <th>交易金额（元）</th>
                <th>手续费（元）</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{bean.payOrgCode}}</td>
                <td>{{bean.settleDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.payTransNo}}</td>
                <td>{{vm.cached.TRANS_CODE_ALL[bean.transCode + '']}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel + '']}}</td>
                <td>{{vm.cached.BANK_INFO[bean.bankCode + '']}}</td>
                <td>{{vm.cached.BANK_CARD_TYPE[bean.bankType + '']}}</td>
                <td>{{bean.transAmt /100 | currency:''}}</td>
                <td>{{bean.feeAmt /100 | currency:''}}</td>
            </tr>
            </tbody>
        </table>
        <core:import url="../../../common/pageFoot.jsp"/>
    </div>
</div>
</body>
</html>
</html>
