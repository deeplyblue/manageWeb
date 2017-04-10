<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/16
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/settleManage/merchantClearCfgApp-ab92e7acc3.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询结算信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/business/businessClearCfg/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10">
                    <%--<select ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"--%>
                            <%--ng-options="key as value for (key,value) in vm.cached.MERCHANT_CODE">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                </td>
                <td class="text-right">
                    结算点：
                </td>
                <td>
                    <select ng-model="vm.queryBean.clrPoint" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_CYCLE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    订单类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.TRANS_CODE_ALL">
                        <option value="">请选择</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    接入渠道：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    审核状态：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.AUDIT_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>

            </tr>

            <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '03'" ng-show="false" />
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
                <th>名称</th>
                <th>订单类型名称</th>
                <th>接入渠道 </th>
                <th>结算点</th>
                <th>审核状态</th>
                <th>状态</th>
            </tr>
            </thead>

            <tbody>
            <tr ng-repeat="bean in vm.pagination.list">
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}[{{bean.orgCode}}]</td>
                <td>{{bean.transCode}}</td>
                <td>{{bean.connChannel}}</td>
                <td>{{vm.cached.CLR_CYCLE[bean.clrCycle]}}</td>
                <td>{{vm.cached.AUDIT_STATUS[bean.auditStatus]}}</td>
                <td>{{vm.cached.ENABLE_FLAG[bean.enableFlag]}}</td>
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
