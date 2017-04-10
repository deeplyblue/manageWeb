<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2017/1/12
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>
    <table class="table table-bordered">
        <tr>
            <td class="text-right col-xs-3">
                关联机构：
            </td>
            <td class="col-xs-4">
                <input type="text" name="refCode" maxlength="40" style="ime-mode:disabled" ng-model="vm.item.refCode" class="{{vm.constant.inputClass}} " typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getBankCode() | filter:$viewValue | limitTo:6" required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                申请日期：
            </td>
            <td class="col-xs-4">
                <input type="text" name="applyDate" ng-model="vm.item.applyDate" class="{{vm.constant.inputClass}}"
                       uib-datepicker-popup="yyyy-MM-dd" required>

            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                申请说明：
            </td>
            <td class="col-xs-4">
                <textarea name="applyInfo" rows="10" ng-model="vm.item.applyInfo" class="form-control" ng-change="checkText()" required/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
