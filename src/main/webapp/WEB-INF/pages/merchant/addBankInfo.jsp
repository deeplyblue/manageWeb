<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    银行代码：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}" required name="bankCode">--%>
                    <input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}" required name="bankCode"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.key+item.value for item in vm.cached.T_BANK_CODE | filter:$viewValue | limitTo:10">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankName" class="{{vm.constant.inputClass}}" required name="bankName">
                </td>
            </tr>
                <tr>
                <td class="text-right">
                    银行简称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankAbbrName" class="{{vm.constant.inputClass}}" required name="bankAbbrName">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.bankType" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.item.bankCardType" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.BANK_INFO_TYPE">
                        <option value="">请选择</option>
                        </select>
                </td>
                </tr>
            <tr>
            <td class="text-right">
                银行Logo：
            </td>
            <td>
                <input type="text" ng-model="vm.item.bankLogo"class="{{vm.constant.inputClass}}" required name="bankLogo"/>
            </td>
        </tr>
            <tr>
            <td class="text-right">
                银行说明文件：
            </td>
            <td>
                <input type="text" ng-model="vm.item.bankMemo"class="{{vm.constant.inputClass}}" required name="bankMemo"/>
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    老银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.oldBankCode"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    状态：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.enableFlag"class="{{vm.constant.inputClass}}" />--%>
                    <select ng-model="vm.item.enableFlag" class="{{vm.constant.inputClass}}"
                            required name="enableFlag"
                            ng-options="item.key as item.value for item in vm.cached.ENABLE_FLAG">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    备注：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.spare1"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
        </table>

</div>
</body>
</html>
