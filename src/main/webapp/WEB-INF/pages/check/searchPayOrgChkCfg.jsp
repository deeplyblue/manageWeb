<%@ page import="com.oriental.check.commons.enums.ChkType" %>
<%@ page import="com.oriental.check.commons.enums.Status" %>
<%--
  User: 蒯越
  Date: 2016/5/9
  Time: 9:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/payOrgChkCfgApp-580873ff97.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="支付机构对账配置"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/payOrgChkCfg/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">支付机构</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">对账类型</td>
                <td>
                    <select ng-model="vm.queryBean.chkType" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%
                            for (ChkType chkType : ChkType.values()) {
                        %>
                        <option value="<%=chkType.getCode()%>">
                            <%=chkType.getDesc()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
                <td class="text-right">状态</td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%
                            for (Status status : Status.values()) {
                                if (status.name().startsWith("CHK_CFG")) {
                        %>
                        <option value="<%=status.getCode()%>">
                            <%=status.getDesc()%>
                        </option>
                        <%
                                }
                            }
                        %>
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
<shiro:hasPermission name="payOrgChkCfg_add">
        <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="payOrgChkCfg_update">
        <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
        <table table-detail >
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>支付机构</th>
                <th>对账类型</th>
                <th>对账文件方式</th>
                <th>对账文件时间</th>
                <th>对账周期</th>
                <th>退款是否参与对账</th>
                <th>状态</th>
                <th>最后操作人</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.companyCode]}}({{bean.companyCode}})</td>
                <td>{{bean.chkTypeDesc}}</td>
                <td>{{bean.fileMethodDesc}}</td>
                <td>{{bean.fileTime}}</td>
                <td>{{bean.chkCycleDesc}}</td>
                <td>{{bean.refundCheckFlagDesc}}</td>
                <td>{{bean.statusDesc}}</td>
                <td>{{bean.updatedBy}}</td>
                <td>
<shiro:hasPermission name="payOrgChkCfg_open">
                    <button type="button" ng-click="vm.openItem(bean.id)" ng-if="bean.status == 0" class="btn btn-default">开通</button>
    </shiro:hasPermission>
<shiro:hasPermission name="payOrgChkCfg_close">
                    <button type="button" ng-click="vm.closeItem(bean.id)" ng-if="bean.status == 1"class="btn btn-default">关闭</button>
            </shiro:hasPermission>
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
