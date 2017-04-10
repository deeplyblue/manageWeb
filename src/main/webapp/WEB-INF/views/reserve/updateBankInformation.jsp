<%--
  Created by IntelliJ IDEA.
  User: tanglu
  Date: 2016/12/20 0020
  Time: 19:50   银行信息录入
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>
    <table class="table table-bordered">
        <tr>
            <td class="text-right col-xs-3">
                银行行号：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.bankCode"
                       placeholder="根据简称获取银行行号"
                       class="{{vm.constant.inputClass}}"
                       typeahead-min-length="0"
                       uib-typeahead="item.code as item.name for item in vm.cached.BANK_LIST | filter:$viewValue | limitTo:10"/>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                银行简称代码：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.bankShortCode" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                账号：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.accountNo" class="{{vm.constant.inputClass}} " required name="accountNo">
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                户名：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.accountName" class="{{vm.constant.inputClass}} " required name="accountName">
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                开户行行号：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.openBankCode" class="{{vm.constant.inputClass}} " >
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                开户行名称：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.openBankName" class="{{vm.constant.inputClass}} " required name="openBankName">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                账户类型：
            </td>
            <td>
                <select   ng-model="vm.item.accountType" class="{{vm.constant.inputClass}}" ng-init="vm.item.currency='001'"
                          ng-options="key as value for (key,value) in vm.cached.ACCT_TYPE" required name="accountType">
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                开户地：
            </td>
            <td class="col-xs-4">
                <input type="text"  ng-model="vm.item.openAccountAddr" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                是否跨境人民币账户：
            </td>
            <td>
                <select   ng-model="vm.item.isCrossType" class="{{vm.constant.inputClass}}" ng-init="vm.item.currency='001'"
                          ng-options="key as value for (key,value) in vm.cached.IS_CROSS_TRANS" required name="isCrossType">
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                备付金银行性质：
            </td>
            <td>
                <select   ng-model="vm.item.bankType" class="{{vm.constant.inputClass}}" ng-init="vm.item.currency='001'"
                          ng-options="key as value for (key,value) in vm.cached.BANK_DETAIL_TYPE" required name="bankType">
                </select>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
