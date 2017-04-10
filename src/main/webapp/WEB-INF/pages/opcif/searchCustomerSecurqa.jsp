
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/8/24
  Time: 20:26
--%>
<%--
  @TODO 参考接口文档，查询展示；预留状态修改按钮【冻结】【锁定】，先不做实现
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/opcif/customerSecurqaApp-b5a8dd6b50.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="客户密保问题列表查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <from id="queryForm" name="queryForm" action="${ctx}/cif/customerSecurqa/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                </td>
            </tr>
        </table>
    </from>

    <div>
<shiro:hasPermission name="customerSecurqa_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="customerSecurqa_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th width="5%">编号</th>
                <th width="40%">问题</th>
                <th width="15%">创建人</th>
                <th width="15%">创建时间</th>
                <th width="15%">修改人</th>
                <th width="15%">修改时间</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.questionNo}}</td>
                <td>{{bean.question}}</td>
                <td>{{bean.createdBy}}</td>
                <td>{{bean.createdAt | date:"yyyyMMdd"}}</td>
                <td>{{bean.updatedBy}}</td>
                <td>{{bean.updatedAt | date:"yyyyMMdd"}}</td>
            </tr>
            </tbody>
        </table>



    </div>
    <core:import url="../common/nav.jsp"/>
</div>
</body>

</html>
