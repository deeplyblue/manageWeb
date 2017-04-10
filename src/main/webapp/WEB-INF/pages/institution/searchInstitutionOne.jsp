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
    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    机构代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    机构种类：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCategory" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgType" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构全称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构简称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgAbbrName" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构介绍：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgIntro" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构归属地：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgArea"class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构接入时间：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgConnDate" class="{{vm.constant.inputClass}} "
                           data-format="dd/MM/yyyy hh:mm:ss"  id="dp2">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgOrganizationCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    法定代表人：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgLegalProxy" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照注册号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bizLicenseNo" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构注册地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgRegAddr" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    退款交易是否需要对账：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.refundHasCheck" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构告警级别：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.isAlarm" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付机构商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.payOrgMchnt" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付机构级别：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.payOrgLevel" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    是否支持部分退款：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.partRefundFlag" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
            <td class="text-right">
                手续费收取方式：
            </td>
            <td>
                <input type="text" ng-model="vm.item.feeCollectWay" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    内外部类型：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgInOrOutFlag" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    退款方式：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.refundMode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
