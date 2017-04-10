<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2016/12/21
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>

    <table class="table table-bordered">

        <tr>
            <td class="text-right col-xs-3">
                联系人姓名：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.connName" name="connName" class="{{vm.constant.inputClass}}"
                       maxlength="50"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                归属机构编号：
            </td>
            <td class="col-xs-4">
                <input type="text"
                       style="ime-mode:disabled"
                       ng-model="vm.item.ownOrgNO" name="ownOrgNO" class="{{vm.constant.inputClass}} "
                       maxlength="14"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                职务：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.rule" name="rule" class="{{vm.constant.inputClass}} "
                       maxlength="90"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                办公电话：
            </td>
            <td class="col-xs-4">
                <input type="text" onpaste="return false"
                       onpaste="return false"
                       ondragenter="return false"
                       oncontextmenu="return false;"
                       style="ime-mode:disabled"
                       ng-model="vm.item.tel" name="tel" class="{{vm.constant.inputClass}} "
                       maxlength="30"
                       required>
            </td>
        </tr>

        <tr>
            <td class="text-right col-xs-3">
                手机：
            </td>
            <td class="col-xs-4">
                <input type="text"
                       onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"
                       ng-model="vm.item.mobile" name="mobile" class="{{vm.constant.inputClass}} "
                       maxlength="20"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                邮件：
            </td>
            <td class="col-xs-4">
                <input type="email" ng-model="vm.item.email" name="email" class="{{vm.constant.inputClass}} "
                       style="ime-mode:disabled"
                       maxlength="50"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                备注：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.remarks" name="remarks" class="{{vm.constant.inputClass}} "
                       maxlength="300"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                主要联系人：
            </td>
            <td class="col-xs-4">
                <select ng-model="vm.item.mainConn" class="{{vm.constant.inputClass}} " name="mainConn"
                        ng-options="key as value for (key,value) in vm.cached.MAIN_CONN_FOR_RESERVE" required>
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                离职标示：
            </td>
            <td class="col-xs-4">
                <select ng-model="vm.item.offStatus" class="{{vm.constant.inputClass}} " name="offStatus"
                        ng-options="key as value for (key,value) in vm.cached.OFF_STATUS_FOR_RESERVE" required>
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>

    </table>
</div>
</body>
</html>