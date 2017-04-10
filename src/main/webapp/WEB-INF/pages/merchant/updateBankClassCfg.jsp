<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/23
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>
    <%--<form name="updateFeeCfgBankClassCfg" novalidate w5c-form-validate class="form-inline">--%>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.payOrgCode" class="form-control input-sm">--%>
                        <%--<select ng-model="vm.item.payOrgCode" class="form-control"--%>
                                <%--ng-options="item.key as item.value for item in vm.cached.COMANY_CODE">--%>
                        <%--</select>--%>
                        <input type="text" ng-model="vm.item.payOrgCode" class="{{vm.constant.inputClass}}" required name="companyCode" ng-init="vm.item.payOrgCode"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    订单类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.transCode" class="form-control input-sm">--%>
                    <select ng-model="vm.item.transCode" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.TRANS_CODE_02">

                    </select>
                </td>
            </tr>
                <tr>
                <td class="text-right">
                    类路径：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.classPath" class="{{vm.constant.inputClass}}" required name="classPath" >
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    Action名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.actionName" class="{{vm.constant.inputClass}}" required name="actionName">

                </td>
                </tr>
            <tr>
            <td class="text-right">
                银行类型：
            </td>
            <td>
                <%--<input type="text" ng-model="vm.item.bankType"class="form-control input-sm" />--%>
                    <select ng-model="vm.item.bankType" class="{{vm.constant.inputClass}}" required name="bankType"
                            ng-options="item.key as item.value for item in vm.cached.BANK_TYPE">
                    </select>
            </td>

        </tr>
        </table>
   <%-- </form>--%>
</div>
</body>
</html>
