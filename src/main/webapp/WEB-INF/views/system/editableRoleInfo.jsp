<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/19
  Time: 14:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div common-modal-form>

    <table class="table table-bordered">
        <tr>
            <input type="hidden" ng-model="vm.item.roleId">
            <td class="text-right col-xs-3">
                角色名称
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.roleName" class="{{vm.constant.inputClass}} "
                       w5c-unique-check="{url:'${ctx}/system/role/checkRoleName?roleName='+vm.item.roleName+'&roleId='+vm.item.roleId}"
                       name="roleName" required>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                角色类型
            </td>
            <td>
                <select ng-model="vm.item.roleType" class="{{vm.constant.inputClass}} "
                        ng-options="item.key as item.value for item in vm.cached.ROLE_TYPE" name="roleType" required>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                角色描述
            </td>
            <td>
                <input type="text" ng-model="vm.item.roleDesc" class="{{vm.constant.inputClass}} " name="roleDesc" required>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
