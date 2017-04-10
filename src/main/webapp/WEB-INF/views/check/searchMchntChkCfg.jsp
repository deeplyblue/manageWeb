<%@ page import="com.oriental.check.commons.enums.ChkType" %>
<%@ page import="com.oriental.check.commons.enums.Status" %>
<%--
  User: 蒯越
  Date: 2016/5/17
  Time: 11:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/check/mchntChkCfgApp.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="商户对账配置"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/mchntChkCfg/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">对账类型</td>
                <td>
                    <select ng-model="vm.queryBean.chkType" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%
                            for (ChkType chkType : ChkType.values()) {
                                if ("01".equals(chkType.getCode()) || "04".equals(chkType.getCode())) {
                        %>
                        <option value="<%=chkType.getCode()%>">
                            <%=chkType.getDesc()%>
                        </option> <%
                                }
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

        <shiro:hasPermission name="merchant-mchntChkCfg_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="merchant-mchntChkCfg_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>商户</th>
                <th>对账类型</th>
                <th>对账文件方式</th>
                <th>对账文件时间</th>
                <th>对账文件模板</th>
                <th>对账周期</th>
                <th>操作实现类</th>
                <th>依赖银行对账标识</th>
                <th>状态</th>
                <th>最后操作人</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}({{bean.companyCode}})</td>
                <td>{{bean.chkTypeDesc}}</td>
                <td>{{bean.fileMethodDesc}}</td>
                <td>{{bean.fileTime}}</td>
                <td>
                    <span ng-if="bean.fileTemplateNo != null && bean.fileTemplateNo != ''">
                        {{vm.cached.FILE_TEMPLATE_NO[bean.fileTemplateNo]}}({{bean.fileTemplateNo}})
                    </span>
                </td>
                <td>{{bean.chkCycleDesc}}</td>
                <td>{{bean.operateImplClassDesc}}({{bean.operateImplClass}})</td>
                <td>{{bean.dependOnBankChkFlagDesc}}</td>
                <td>{{bean.statusDesc}}</td>
                <td>{{bean.updatedBy}}</td>
                <td>
<shiro:hasPermission name="merchant-mchntChkCfg_open">
                    <button type="button" ng-click="vm.openItem(bean.id)" ng-if="bean.status == 0"
                            class="btn btn-default">
                        开通
                    </button></shiro:hasPermission>
<shiro:hasPermission name="merchant-mchntChkCfg_close">
                    <button type="button" ng-click="vm.closeItem(bean.id)" ng-if="bean.status == 1"
                            class="btn btn-default">
                        关闭
                    </button></shiro:hasPermission>
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
