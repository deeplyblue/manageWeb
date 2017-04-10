<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/16
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/settleManage/instiutionClearCfgApp-752c8e776b.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询结算信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/clearCfg/search" >
        <table table-form>
            <tr>
                <td class="text-right">
                    机构代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                </td>
                <td class="text-right">
                    启用状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.enableFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.ENABLE_FLAG">
                        <option value="">请选择</option>
                    </select>
                    <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '<%=CompanyType.PAY.getCode()%>'" ng-show="false" />
                </td>
                <td> 结算类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.clrType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_TYPE">
                        <option value="">请选择</option>
                    </select>
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
<shiro:hasPermission name="clearCfg_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="clearCfg_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
    </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>名称</th>
                <th>结算类型</th>
                <th>结算周期</th>
                <th>报表类型</th>
                <th>手续费收取方式</th>
                <th>创建人</th>
                <th>操作人</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr ng-repeat="bean in vm.pagination.list">
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.companyCode]}}[{{bean.companyCode}}]</td>
                <td>{{vm.cached.CLR_TYPE[bean.clrType]}}</td>
                <td>{{vm.cached.CLR_CYCLE[bean.clrCycle]}}</td>
                <td>{{vm.cached.REPORT_TYPE[bean.reportType]}}</td>
                <td>{{vm.cached.FEE_CLEAR_TYPE[bean.feeClearType]}}</td>
                <td>{{bean.creator}}</td>
                <td>{{bean.auditor}}</td>
                <td>{{vm.cached.ENABLE_FLAG[bean.enableFlag]}}</td>
                <td>
                    <shiro:hasPermission name="pay-clearCfg_open">
                        <button type="button" ng-if="bean.auditStatus=='02' && bean.enableFlag=='0'"
                                ng-click="vm.updateItemEnableFlag(bean,'','1')">
                            启用</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="pay-clearCfg_close">
                        <button type="button" ng-if="bean.auditStatus=='02' && bean.enableFlag=='1'"
                                ng-click="vm.updateItemEnableFlag(bean,'','0')">
                            关闭</button>
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
    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
