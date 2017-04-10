<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form name="addBankType" novalidate w5c-form-validate class="form-inline" autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    银行类型：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.bankTypeCode" class="{{vm.constant.inputClass}}" required name="bankTypeCode">--%>
                    <%--<select ng-model="vm.item.bankTypeCode" class="{{vm.constant.inputClass}}"--%>
                            <%--ng-options="item.key as item.value for item in vm.cached.BANK_TYPE">--%>
                    <%--</select>--%>
                        <div>{{vm.item.BANK_TYPE[vm.item.bankTypeCode]}}[{{vm.item.bankTypeCode}}]</div>
                        <input type="text" ng-model="vm.item.bankTypeCode" class="form-control" style="display: none;" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行代码
                </td>
                <td>
                <span ng-repeat="bean in vm.item.bankCodes track by $index" colspan="{{vm.item.bankCodes.length}}">
                    <%--<input type="checkbox" ng-model="vm.item.bankCode">--%>
                    <input name="checked" type="checkbox"  ng-model="bean.checked">
                     {{bean.bankCode}}
                </span>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
