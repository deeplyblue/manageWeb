<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script type="text/ng-template" id="myPopoverTemplate.html">
            <div class="control-group">
                <label class="control-label" for="verifyCode">登入密码:</label>
                <div class="controls">
                <input id="verifyCode" type="password" ng-model="vm.queryBean.verifyCode" maxlength="20" value=""
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="smsVerifyCode">短信验证码:</label>
                <div class="controls">
                    <input id="smsVerifyCode" type="text" ng-model="vm.queryBean.smsVerifyCode" maxlength="6" value=""
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false" style="width:60px">
                     <span id="smsWrap">
                         <button type="button" ng-click="vm.sendSmsVerifyCode()" class="btn btn-mini">再次发送</button>
                     </span>
                    <span id="smsSecond" style="display: none;font-size: small;color: red"></span>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="button" ng-click="vm.check('04')" class="btn btn-mini">提交</button>
                </div>
            </div>
    </script>
</head>
<body>
<div class="container-fluid">
    <form id="queryForm" name="queryForm" action="#" class="form-inline">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    用户ID
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.userId" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    订单状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.transStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.ORDER_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <div ng-show="vm.pagination.list[0].status == '02'">
            <shiro:hasPermission name="payment_payment">
            <button type="button" uib-popover-template="vm.myPopoverTemplate" popover-title="身份验证"
                    popover-trigger="none" popover-is-open="vm.popover.open" class="btn btn-default" ng-click="vm.popover.open=!vm.popover.open;">验证付款</button>
            <%--<button type="button" ng-click="vm.check('02')" class="btn btn-default">审核通过</button>--%>
            <%--<button type="button" ng-click="vm.check('03')" class="btn btn-default">审核失败</button>--%>
            </shiro:hasPermission>
        </div>
        <div ng-show="vm.pagination.list[0].status == '04'">
            <shiro:hasPermission name="payment_downloadResultFile">
                <button type="button" ng-click="vm.downloadResultFile()" class="btn btn-default">下载结果文件</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="payment_paymentAgain">
                <button type="button" ng-click="vm.paymentAgain()" class="btn btn-default">中断补发</button>
            </shiro:hasPermission>
        </div>
        <table table-detail>
            <input type="hidden" ng-model="vm.item.batchNo"/>
            <thead>
            <tr>
                <th>订单号</th>
                <th>用户ID</th>
                <th>姓名</th>
                <th>手机号</th>
                <th>身份证号</th>
                <th>中奖等级</th>
                <th>金额(元)</th>
                <th>卡号</th>
                <th>交易状态</th>
                <th>响应信息</th>
                <th>是否退款</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.orderNo}}</td>
                <td>{{bean.userId}}</td>
                <td>{{bean.userName}}</td>
                <td>{{bean.phoneNo}}</td>
                <td>{{bean.identityCardNo}}</td>
                <td>{{bean.bonusLevel}}</td>
                <td>{{bean.amount| currency:''}}</td>
                <td>{{bean.bankCardNo}}</td>
                <td>{{vm.cached.ORDER_STATUS[bean.transStatus]}}</td>
                <td>{{bean.respDesc}}</td>
                <td>
                    <div ng-show="bean.refundFlag == '0'">未退款</div>
                    <div ng-show="bean.refundFlag == '1'">已退款</div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
