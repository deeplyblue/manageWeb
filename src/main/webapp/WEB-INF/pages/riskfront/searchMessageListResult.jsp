<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed">
      <td><span ng-repeat="bean in vm.item.reMessage track by $index" colspan="{{vm.item.reMessage.length}}">
               {{bean}} <br/>
                </span>
      </td>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
