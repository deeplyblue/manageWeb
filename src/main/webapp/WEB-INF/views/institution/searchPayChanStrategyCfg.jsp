<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/institution/orgbankForPayApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询机构银行及支付策略信息  "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/orgbank/queryOrgBankInfoForPayChanExtendPage" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                    <%--<select ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"--%>
                    <%--ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">--%>
                    <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                </td>
                <td class="text-right">
                    渠道：
                </td>
                <td>
                    <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>

                </td>
                <td class="text-right">
                    订单类型：
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.TRANS_CODE_02">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    是否已经配置：
                </td>
                <td>
                    <select ng-model="vm.queryBean.transCodeFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'0':'否','1':'是'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
<shiro:hasPermission name="pay-strategy_add">
            <button ng-click="vm.editItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>银行代码</th>
                <th>渠道</th>
                <th>订单类型</th>
                <th>支付机构</th>
                <th>策略优先级</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody ng-repeat="(key,value) in vm.pagination.list | groupBy:'bankCode'">
            <%--<tr><td>{{key | json}}</td><td>{{value | json}}</td></tr>--%>
            <tr ng-repeat="bean in value">
                <td rowspan="{{value.length}}" ng-if="value.indexOf(bean) == 0">
                    {{vm.cached.COMANY_CODE[bean.orgCode]}}{{bean.bankCode}}
                </td>
                <td>
                    {{vm.cached.CONN_CHANNEL[bean.connChannel]}}
                </td>
                <td>{{vm.cached.TRANS_CODE[bean.transCode]}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode]}}({{bean.orgCode}})</td>
                <td>{{bean.stgyPriority}}</td>
                <td>
<shiro:hasPermission name="pay-strategy_update">
                    <button ng-click="vm.editItem(bean)">编辑</button>
    </shiro:hasPermission>
                </td>
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
