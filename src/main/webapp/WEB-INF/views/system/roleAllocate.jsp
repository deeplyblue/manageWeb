<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/19
  Time: 17:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="row">
    <div class="col-md-offset-1" ng-repeat="bean in vm.cached.ROLE_INFO">
        <ul><input name="check" type="checkbox" ng-click="vm.toggleCheck($event, bean.key)"
                   ng-checked="vm.item.roles.indexOf(bean.key) != -1">{{bean.value}}
        </ul>
    </div>
</div>
</body>
</html>
