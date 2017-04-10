<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/8/22
  Time: 14:52
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div common-modal-form>
    <table table-form>
        <tr>
            <td class="col-md-4">用户：</td>
            <td class="col-md-8 input-left">{{vm.item.userName}}</td>
        </tr>
        <tr>
            <td>新密码：</td>
            <td class="input-left">
                <input type="password" name="userPwd" required
                       ng-pattern="/^(?=.{8,16})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*|(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*\W).*|(?=.{8,})(?=.*[A-Z])(?=.*[0-9])(?=.*\W).*|(?=.{8,})(?=.*[a-z])(?=.*[0-9])(?=.*\W).*$/"
                       ng-minlength="8" ng-maxlength="16"
                       ng-model="vm.item.userPwd" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td>确认新密码：</td>
            <td class="input-left">
                <input type="password" name="repeat_userPwd" required w5c-repeat="userPwd"
                       ng-model="vm.item.repeat_userPwd" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
    </table>
</div>
</body>
</html>
