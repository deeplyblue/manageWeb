<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/22
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/system/aloneAccount.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="单独对账申请"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">



    <form id="queryForm" name="queryForm" action="${ctx}/reserve/aloneAccount/apply/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    银行行号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="form-control" />
                </td>
                <td class="text-right">
                    结算日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.settleDate"/>
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
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <%--<th>支付机构编号</th>--%>
                <th>银行行号</th>
                <th>结算日期</th>
                <th>申请功能</th>
                <th>申请状态</th>
                <th>操作类型</th>
                <th>删除标识</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td >
                    <input  ng-disabled="bean.deleteFlag == '1' " name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)" ng-model="bean._checked">
                </td>
              <%--  <td>{{bean.orgNO}}</td>--%>
                <td>{{bean.bankCode}}</td>
                <td>{{bean.settleDate | date:'yyyy-MM-dd'}}</td>
                <td>{{{'01':'结算单核对','02':'明细核对'}[bean.type]}}</td>
                <td>{{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.status]}}</td>
                <td>{{{'00':'新增','01':'修改'}[bean.operateType]}}</td>
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
