<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2016/12/21
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/reserveMsgPaymentContactInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="支付机构联系人信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/paymentContactInfo/search" class="form-inline"
          autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    联系人姓名：
                </td>
                <td>
                    <input ng-model="vm.queryBean.connName" class="{{vm.constant.inputClass}}">
                    </input>
                </td>
                <td class="text-right">
                    是否删除：
                </td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'0':'否','1':'是'}">
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

    <div>
        <p class="btn-group">
            <%--<shiro:hasPermission name="reserve-orgCode_add">--%>
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="reserve-orgCode_update">--%>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:hasPermission name="reserve-orgCode_delete">--%>
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            <%--</shiro:hasPermission>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>姓名</th>
                <th>归属机构编号</th>
                <th>职务</th>
                <th>办公电话</th>
                <th>手机</th>
                <th>邮件</th>
                <th>备注</th>
                <th>主要联系人</th>
                <th>离职标识</th>
                <th>操作人</th>
                <th>操作时间</th>
                <th>是否删除</th>
                <th>操作类型</th>
                <th>是否人行下发</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <%--<input name="check" type="checkbox" ng-disabled="bean.centerStatus =='01' "--%>
                    <%--ng-click="vm.updateChecked($event, bean.id)">--%>
                    <input name="check" type="checkbox" ng-disabled="bean.deleteFlag =='1' || bean.centerStatus =='01' || bean.operateType == '02'"
                           ng-model="bean._checked">
                </td>
                <td>{{bean.connName}}</td>
                <td>{{bean.ownOrgNO}}</td>
                <td>{{bean.rule}}</td>
                <td>{{bean.tel}}</td>
                <td>{{bean.mobile}}</td>
                <td>{{bean.email}}</td>
                <td>{{bean.remarks}}</td>
                <td>{{vm.cached.MAIN_CONN_FOR_RESERVE[bean.mainConn]}}</td>
                <td>{{vm.cached.OFF_STATUS_FOR_RESERVE[bean.offStatus]}}</td>
                <td>{{bean.operator}}</td>
                <td>{{bean.operateTime | date:'yyyy-MM-dd'}}</td>
                <td>{{{'0':'否','1':'是'}[bean.deleteFlag]}}</td>
                <td>{{{'00':'新增','01':'修改','02':'删除'}[bean.operateType]}}</td>
                <td>{{vm.cached.CENTER_STATUS_FOR_RESERVE[bean.centerStatus]}}</td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>