<%@ page import="com.oriental.manage.core.enums.CompanyType" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: wuxg
  Date: 2016/5/31
  Time: 9:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/settleManage/feeTemplateInfoApp-201eb8e580.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询手续费模板信息"/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/feeTemplate/search" autocomplete="off">
        <table table-form>
            <tr  align="center">

                <td>模板名称：
                    <input type="text" ng-model="vm.queryBean.templateName" placeholder="支持模糊查询" class="{{vm.constant.inputClass}}" />
                    <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '<%=CompanyType.MERCHANT.getCode()%>'" ng-show="false" />
                </td>
            </tr>
            <tr align="center">
                <td colspan="8" class="textCenter">
<%--<shiro:hasPermission name="merchant-feeTemplate_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
    <%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
<shiro:hasPermission name="merchant-feeTemplate_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
    </shiro:hasPermission>
<shiro:hasPermission name="merchant-feeTemplate_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
    </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>模板名称</th>
                <th>接入渠道</th>
                <th>计费方法</th>
                <th>计费单位</th>
                <th>精确类型</th>
                <th>手续费计费方法</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.templateName}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td>{{{'01':'按笔','02':'按周期'}[bean.feeMethod]}}</td>
                <td>{{{'01':'按金额','02':'按笔'}[bean.feeUnit]}}</td>
                <td>{{{'1':'四舍五入','2':'舍位','3':'四舍五一入','4':'五舍五一入'}[bean.rateRoundType]}}</td>
                <td>{{{'01':'阶梯','02':'累进'}[bean.feeSegType]}}</td>
                <td>{{}}</td>
            </tr>
            </tbody>

    </table>
</div>

</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
