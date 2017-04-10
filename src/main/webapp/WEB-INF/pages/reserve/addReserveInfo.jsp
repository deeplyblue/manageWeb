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
                    <input type="text" ng-model="vm.item.accountNo" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    户名：
                </td>
                <td class="col-xs-4">
                    <input type="text"  ng-model="vm.item.accountName" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易机构代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" placeholder="多个机构用'|'拼接" ng-model="vm.item.transOrgCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易商户代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" placeholder="多个商户用'|'拼接" ng-model="vm.item.transMerchantCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行分配机构代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" placeholder="工行ICBC" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    类名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.className" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    币种：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.currency" class="{{vm.constant.inputClass}}" />--%>
                    <select   ng-model="vm.item.currency" class="{{vm.constant.inputClass}}" ng-init="vm.item.currency='001'"
                              ng-options="key as value for (key,value) in {'001':'人民币','013':'港币','014':'美元'}">

                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    证件姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.certName" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    证件类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.certType" class="{{vm.constant.inputClass}}" />--%>
                    <select ng-model="vm.item.certType" class="{{vm.constant.inputClass}}" ng-init="vm.item.certType='010'"
                            ng-options="key as value for (key,value) in {'010':'身份证','011':'护照','012':'军官证','013':'士兵证','014':'回乡证','015':'户口本'}">

                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    证件号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.certNo" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    sheet名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.sheetName" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    开户行：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankOfDeposit" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    账户性质：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.accountNature" placeholder="合作行|存款行" class="{{vm.constant.inputClass}}" />--%>
                        <select  ng-model="vm.item.accountNature" ng-init="vm.item.accountNature='合作行'" class="{{vm.constant.inputClass}}"
                                 ng-options="key as value for (key,value) in {'合作行':'合作行','存管行':'存管行'}">

                        </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    描述：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.description" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    备注：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.remark" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
