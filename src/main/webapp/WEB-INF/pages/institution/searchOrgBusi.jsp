
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/orgBusiApp-07a61ca850.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="业务机构信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/orgbusi/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
            <td class="text-right">
                支付机构
            </td>
            <td>
                <%--<input type="text" ng-model="vm.queryBean.orgCode" class="form-control">--%>
                <select ng-model="vm.queryBean.orgCode" class="form-control"
                        ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">
                    <option value="">请选择</option>
                </select>
            </td>
            <td class="text-right">
                业务类型
            </td>
            <td>
                <input type="text" ng-model="vm.queryBean.transCode" class="form-control">
            </td>
            <td class="text-right">
                业务渠道
            </td>
            <td>
                <input type="text" ng-model="vm.queryBean.busiChannel" class="form-control">
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    公私标识
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.busiFlag" class="form-control">
                </td>
                <td class="text-right">
                    同步标识
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.synchronousFlag" class="form-control">
                </td>
                <td class="text-right">
                    渠道
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.connChannel" class="form-control">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    限额
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.quotaFlag" class="form-control">
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
                <th>业务机构 </th>
                <th>业务类型 </th>
                <th>交易渠道 </th>
                <th>业务渠道</th>
                <th>业务渠道描述</th>
                <th>外部业务渠道</th>
                <th>外部业务渠道描述</th>
                <th>同步异步标识</th>
                <th>公私标识</th>
                <th>限额</th>

            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.transCode}}</td>
                <td>{{bean.connChannel}}</td>
                <td>{{bean.busiChannel}}</td>
                <td>{{bean.busiChannelDesc}}</td>
                <td>{{bean.orgBusiChannel}}</td>
                <td>{{bean.orgBusiChannelDesc}}</td>
                <td>{{bean.synchronousFlag}}</td>
                <td>{{vm.cached.CFG_FLAG[bean.busiFlag]}}</td>
                <td>{{vm.cached.QUOTA_FLAG[bean.quotaFlag]}}</td>
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
