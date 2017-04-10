<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/transRightApp-ee1b1e2302.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询交易权限信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/business/transRight/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    业务机构
            </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.templateName" class="form-control">
                </td>
                <td class="text-right">
                    正反标识
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.connChannel" class="form-control">--%>
                    <select ng-model="vm.queryBean.reverseFlag" class="form-control"
                            ng-options="key as value for (key,value) in vm.cached.SPLIT_FLAG">
                        <option value="">请选择</option>
                    </select>
                </td>

                <td class="text-right">
                    分账标识
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.transCode" class="form-control">--%>
                    <select ng-model="vm.queryBean.splitRightFlag" class="form-control"
                    ng-options="key as value for (key,value) in vm.cached.REVERSE_FLAG">
                    <option value="">请选择</option>
                    </select>
                </td>


            </tr>
            >

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
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>支付机构 </th>
                <th>接入渠道 </th>
                <th>订单类型 </th>
                <th>正反标识</th>
                <th>分账标识 </th>
                <th>退款状态</th>
                <th>修改人 </th>


            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.companyCode]}}({{bean.companyCode}})</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td>{{vm.cached.TRANS_CODE_ALL[bean.transCode]}}</td>
                <td>{{vm.cached.REVERSE_FLAG[bean.reverseFlag]}}</td>
                <td>{{vm.cached.SPLIT_FLAG[bean.splitRightFlag]}}</td>
                <td>{{vm.cached.REFUND_FLAG[bean.refundFlag]}}</td>
                <td>{{bean.modifyUser}}</td>

            </tr>
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
