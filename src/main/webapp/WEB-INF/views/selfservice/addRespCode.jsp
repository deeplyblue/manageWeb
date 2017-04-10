<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>

    <table class="table table-bordered">
        <tr>
            <td class="text-right col-xs-3">
                响应编码：
            </td>
            <td class="col-xs-4">
                <input type="text" name="respCode" required
                       ng-model="vm.item.respCode" class="{{vm.constant.inputClass}}">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                中文描述：
            </td>
            <td>
                <input type="text" name="respDesc" required
                       ng-model="vm.item.respDesc" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                机构类型：
            </td>
            <td>
                <%--<input type="text" ng-model="vm.item.userType" class="{{vm.constant.inputClass}} ">--%>
                <select name="orgType" required
                        ng-model="vm.item.orgType" class="{{vm.constant.inputClass}}"
                        ng-options="item.key as item.value for item in vm.cached.RESP_CODE">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="text-right">
                机构代码：
            </td>
            <td>
                <%--<input type="text" name="orgCode" required--%>
                       <%--ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}}">--%>
                <input type="text" ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}}" required name="bankCode"
                       typeahead-min-length="0"   uib-typeahead="item.key as item.key+item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                机构响应码：
            </td>
            <td>
                <input type="text" name="orgRespCode" required
                       ng-model="vm.item.orgRespCode" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                机构响应描述：
            </td>
            <td>
                <input type="text" name="orgRespDesc" required
                       ng-model="vm.item.orgRespDesc" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                旧机构错误码：
            </td>
            <td>
                <input type="text"
                       ng-model="vm.item.oldBankErrCode" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                旧机构错误码描述：
            </td>
            <td>
                <input type="text"
                       ng-model="vm.item.oldBankErrDesc" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
    </table>


</div>
</body>
</html>
