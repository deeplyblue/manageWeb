<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    备付金账户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.accountNo" readonly class="{{vm.constant.inputClass}} ">
                    <input type="text" ng-model="vm.item.id" ng-hide="true" readonly class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易日期：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text"  ng-model="vm.item.transDate" readonly class="{{vm.constant.inputClass}} ">--%>
                    {{vm.item.businessDate|date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    备付金余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.balance"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.businessBalance"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    自有资金余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.privateCapital"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    自有资金无利息余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.privateCapitalNoInterest"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    管理费余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.manageFee"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    利息余额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.interest"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
