<%--
  User: JinXin
  Date: 2016/1/6
  查看支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body >
<div class="container-fluid">

        <table class="table table-bordered table-condensed" >
            <thead>
                <tr>
                    <th class="text-right">
                        <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.item.jobData">
                    </th>
                    <th>职责名称 </th>
                    <th>优先级 </th>
                    <th>步骤 </th>
                    <th>步骤内容描述 </th>
                    <%--<th>是否被选中 </th>--%>
                </tr>
            </thead>
            <tbody ng-repeat="bean in vm.item.jobData track by $index">
                <tr>
                    <td class="text-right">
                        <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)" ng-model="bean._checked" ng-checked="bean.allocated">
                    </td>
                    <td>{{bean.jobName}}</td>
                    <td>{{bean.priority}}</td>
                    <td>{{bean.step}}</td>
                    <td>{{bean.stepContent}}</td>
                    <%--<td>{{bean.allocated}}</td>--%>
                </tr>
            </tbody>

        </table>

    </div>
</body>
</html>
