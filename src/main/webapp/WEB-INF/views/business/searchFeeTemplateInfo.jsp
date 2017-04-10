<%@ page import="com.oriental.manage.core.enums.CompanyType" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: wuxg
  Date: 2016/5/31
  Time: 9:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/institution/settleManage/feeTemplateInfoApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询手续费模板信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/feeTemplate/search">
        <table table-detail>
            <tr valign="top">
                <td class="text-right">
                    模板名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.templateName" placeholder="支持模糊查询" class="form-control" />
                    <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '<%=CompanyType.BUSINESS.getCode()%>'" ng-show="false" />
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
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>模板名称</th>
                <th>接入渠道</th>
                <th>计费方法</th>
                <th>计费单位</th>
                <th>精确类型</th>
                <th>手续费计费方法</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.feeCfgNo)">
                </td>
                <td>{{bean.templateName}}</td>
                <td>{{bean.connChannel}}</td>
                <td>{{bean.feeMethod}}</td>
                <td>{{bean.feeUnit}}</td>
                <td>{{bean.rateRoundType}}</td>
                <td>{{bean.feeSegType}}</td>
                <td>{{}}</td>
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
