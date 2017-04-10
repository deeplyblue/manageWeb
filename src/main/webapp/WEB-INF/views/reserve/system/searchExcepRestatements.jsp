<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/21
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/system/excepRestatements.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="日终异常重报申请"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/dayEnd/excepRestatements/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    关联机构:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.refCode" class="{{vm.constant.inputClass}}" typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getBankCode() | filter:$viewValue | limitTo:6"/>
                </td>
                <td class="text-right">
                    申请日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.applyDate"/>
                </td>
                <td class="text-right">
                    删除类型：
                </td>
                <td>
                    <select   class="form-control" ng-model="vm.queryBean.deleteFlag"
                              ng-options="key as value for (key,value) in vm.cached.DELETE_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <dev>
        <p class="btn-group">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            <!--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>-->
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
               <%-- <th>申请机构</th>--%>
                <th>关联机构</th>
                <th>申请日期</th>
                <th>申请说明</th>
                <th>申请状态</th>
                <th>回执状态</th>
                <th>操作类型</th>
                <th>删除标识</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
                <tr>
                    <td>
                        <input ng-disabled="bean.deleteFlag == '1' " name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)" ng-model="bean._checked">
                    </td>
                   <%-- <td>{{bean.applyCode}}</td>--%>
                    <td>{{bean.refCode}}</td>
                    <td>{{bean.applyDate | date:'yyyy-MM-dd'}}</td>
                    <td>{{bean.applyInfo}}</td>
                    <td>{{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.applyStatus]}}</td>
                    <td>{{vm.cached.DAY_END_REPORT_STATUS[bean.replyStatus]}}</td>
                    <td>{{{'00':'新增','01':'修改'}[bean.operateStatus]}}</td>
                    <td>{{vm.cached.DELETE_STATUS[bean.deleteFlag]}}</td>
                </tr>
            </tbody>
            <tfoot>
            <tr>

            </tr>
            </tfoot>
        </table>
    </dev>
    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
