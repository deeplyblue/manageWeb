<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/26
  Time: 13:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <div class="form-inline" ng-show="vm.pagination.list">
        <%--<div class="col-md-3 form-control-inline">--%>
            <select ng-model="vm.pagination.pageSize" class="{{vm.constant.inputClass}}" title="每页条目数"
                    ng-options="pageSize for pageSize in [10, 25, 100]" ng-disabled="queryForm.$invalid" ng-change="vm.queryDetail()">
            </select>
            <%--<button onclick="download()">下载</button>--%>
        <%--</div>--%>
        <%--<div class="col-md-9">--%>
            <uib-pagination total-items="vm.pagination.rowCount" ng-model="vm.pagination.pageNum" max-size="5"
                            items-per-page="vm.pagination.pageSize" ng-change="vm.queryDetail()"
                            class="pagination-sm pull-right" boundary-links="true"></uib-pagination>
        <%--</div>--%>
    </div>

</body>
</html>
