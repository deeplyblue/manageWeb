
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/system/basicDataApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询数据字典 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/system/basicData/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    字典项名:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.itemName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    列英文名:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.colName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    列中文名:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.colNameCn" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    列值项:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.itemVal" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    描叙:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.itemDesc" class="{{vm.constant.inputClass}}">
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
         <shiro:hasPermission name="basicData_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
         </shiro:hasPermission>
         <shiro:hasPermission name="basicData_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
         </shiro:hasPermission>
         <shiro:hasPermission name="basicData_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
         </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>字典项名 </th>
                <th>列中文名 </th>
                <th>列英文名 </th>
                <th>列值项 </th>
                <th>列类型  </th>
                <th>描述   </th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.itemName}}</td>
                <td>{{bean.colNameCn}}</td>
                <td>{{bean.colName}}</td>
                <td>{{bean.itemVal}}</td>
                <td>{{bean.itemType}}</td>
                <td>{{bean.itemDesc}}</td>
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
