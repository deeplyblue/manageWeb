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
                    银行类型：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.bankTypeCode" class="{{vm.constant.inputClass}}" required name="bankTypeCode">--%>
                    <select ng-model="vm.item.bankTypeCode" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.BANK_TYPE" required name="bankTypeCode">
                        <option value="">请选择</option>
                    </select>
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

</div>
</body>
</html>
