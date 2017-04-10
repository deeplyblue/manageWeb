
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/system/jobResponsibilityApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询岗位职责 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/system/jobResponsibility/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    职责名称:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.jobName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    优先级:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.priority" class="{{vm.constant.inputClass}}">
                </td>

            </tr>


            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
         <%--<shiro:hasPermission name="basicData_add">--%>
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
         <%--</shiro:hasPermission>--%>
         <%--<shiro:hasPermission name="basicData_update">--%>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
         <%--</shiro:hasPermission>--%>

        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>职责名称 </th>
                <th>优先级 </th>
                <th>步骤 </th>
                <th>步骤内容描述 </th>
                <th>创建人  </th>
                <th>创建时间  </th>
                <th>修改人</th>
                <th>修改时间</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.jobName}}</td>
                <td>{{bean.priority}}</td>
                <td>{{bean.step}}</td>
                <td>{{bean.stepContent}}</td>
                <td>{{bean.creator}}</td>
                <td>{{bean.createTime | date:"yyyy-MM-dd "}}</td>
                <td>{{bean.modifier}}</td>
                <td>{{bean.lastUpdTime | date:"yyyy-MM-dd "}}</td>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
