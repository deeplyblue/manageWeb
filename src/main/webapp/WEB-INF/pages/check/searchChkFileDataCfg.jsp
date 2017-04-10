<%--
  User: 蒯越
  Date: 2016/5/17
  Time: 14:40
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/chkFileDataCfgApp-c5f37991ff.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="对账文件模板配置"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/chkFileDataCfg/search" autocomplete="off"  class="form-inline">
        <table  table-form>
            <tr>
                <td class="text-right">对账文件模板</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.templateNo" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('FILE_TEMPLATE_NO') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">

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
        <shiro:hasPermission name="merchant-chkFileDataCfg_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="merchant-chkFileDataCfg_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>对账文件模板</th>
                <th>字段名称</th>
                <th>字段含义</th>
                <th>排列序号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td rowspan="{{bean.fieldDTOList.length + 1}}">
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td rowspan="{{bean.fieldDTOList.length + 1}}">
                    {{bean.templateName}}({{bean.templateNo}})
                </td>
            </tr>
            <tr ng-repeat="field in bean.fieldDTOList">
                <td>{{field.field}}</td>
                <td>{{field.fieldDesc}}</td>
                <td>{{field.seqNo}}</td>
                <td>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>

