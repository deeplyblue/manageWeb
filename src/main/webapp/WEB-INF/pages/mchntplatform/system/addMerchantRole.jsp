<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/19
  Time: 14:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="role" w5c-form-validate novalidate autocomplete="off">
    <table class="table table-bordered">
        <tr>
            <input type="hidden" ng-model="vm.item.roleId" >
            <td class="text-right col-xs-3">
                角色名称
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.roleName" name="roleName" required  class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                角色描述
            </td>
            <td>
                <input type="text" ng-model="vm.item.roleDesc" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
