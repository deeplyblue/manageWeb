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
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    用户账号
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.userName" class="{{vm.constant.inputClass}} " readonly="readonly">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    用户姓名
                </td>
                <td>
                    <input type="text" ng-model="vm.item.userFullName" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    用户类型
                </td>
                <td>{{vm.queryBean.userType}}
                    <select ng-model="vm.item.userType" class="{{vm.constant.inputClass}} "
                            ng-options="item.key as item.value for item in vm.cached.USER_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr ng-if="vm.item.userType == '01'">
                <td class="text-right">
                    部门
                </td>
                <td>
                    <select type="text" name="deptCode" required
                            ng-model="vm.item.deptCode" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.DPET_NAME">
                    </select>
                </td>
            </tr>
            <tr ng-if="vm.item.userType == '02'">
                <td class="text-right">
                    商户号
                </td>
                <td>
                    <select type="text" name="companyCode" required
                            ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.MERCHANT_CODE">
                    </select>
                </td>
            </tr>
            <%--<tr>
                <td class="text-right">
                    登录密码
                </td>
                <td>
                    <input type="password" ng-model="vm.item.userPwd" class="{{vm.constant.inputClass}} "
                           ng-init="vm.item.userPwd = '******'">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    密码确认
                </td>
                <td>
                    <input type="password" ng-model="vm.item.repeat_userPwd" class="{{vm.constant.inputClass}} "
                           ng-init="vm.item.repeat_userPwd = '******'">
                </td>
            </tr>--%>
            <tr>
                <td class="text-right">
                    mobil
                </td>
                <td>
                    <input type="text" ng-model="vm.item.userMobile" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    email
                </td>
                <td>
                    <input type="text" ng-model="vm.item.userEmail" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
