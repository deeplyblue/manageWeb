<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <shiro:hasPermission name="mchntClearDetailAudit_auditSuccese">
        <button ng-click="vm.audit(vm.item.list[0].handleBatchNo,'02')" ng-if="vm.item.list[0].auditStatus == '01'">
            审核成功
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="mchntClearDetailAudit_auditFail">
        <button ng-click="vm.auditFail(vm.item.list[0].handleBatchNo,vm.item.list[0].clrDate,vm.item.list[0].clrType)"
                ng-if="vm.item.list[0].auditStatus == '01'">
            审核失败
        </button>
    </shiro:hasPermission>
    <table table-detail>
        <thead>
        <tr>
            <th>序号</th>
            <th>商户代码</th>
            <th>分账商户</th>
            <th>机构代码</th>
            <td>结算类型</td>
            <th>应付笔数</th>
            <th>应付金额</th>
            <th>应退笔数</th>
            <th>应退金额</th>
            <th>结算净额</th>
            <th>应收佣金</th>
            <th>应退佣金</th>
            <th>应付净额</th>
            <th>审核状态</th>
            <th>申请状态</th>
            <th>报表类型</th>
            <th>经办批次号</th>
            <th>经办人</th>
            <th>结算开始时间</th>
            <th>结算结束时间</th>
        </tr>
        </thead>

        <tbody ng-repeat="bean in vm.item.list track by $index">
        <tr>
            <td>{{$index + 1}}</td>
            <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode]}}({{bean.mchntCode}})</td>
            <td>{{vm.cached.MERCHANT_CODE[bean.mchntCodeSplit]}}({{bean.mchntCodeSplit}})</td>
            <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
            <td>{{vm.cached.CLR_TYPE[bean.clrType]}}</td>
            <td>{{bean.payableCount}}</td>
            <td>{{bean.payableAmt/100 | currency:'￥'}}</td>
            <td>{{bean.receivableCount}}</td>
            <td>{{bean.receivableAmt/100 | currency:'￥'}}</td>
            <td>{{bean.clrNetAmt/100 | currency:'￥'}}</td>
            <td>{{bean.feeAmt/100 | currency:'￥'}}</td>
            <td>{{bean.reFeeAmt/100 | currency:'￥'}}</td>
            <td>{{bean.clrAmt/100 | currency:'￥'}}</td>
            <td>{{{'01':'未审核','02':'审核成功','03':'审核失败'}[bean.auditStatus]}}</td>
            <td>{{vm.cached.PAY_REQ_FLAG[bean.payReqFlag]}}</td>
            <td>{{vm.cached.REPORT_TYPE[bean.reportType]}}</td>
            <td>{{bean.handleBatchNo}}</td>
            <td>{{bean.manager}}</td>
            <td>{{bean.clrStartDate | date:'yyyyMMdd'}}</td>
            <td>{{bean.clrEndDate | date:'yyyyMMdd'}}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
