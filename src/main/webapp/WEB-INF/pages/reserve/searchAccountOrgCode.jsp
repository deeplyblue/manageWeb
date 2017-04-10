<%@ page import="com.oriental.reserve.enums.OrgType" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/reserve/accountOrgCode-fc9665f672.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金关联机构代码"/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/account/orgCode/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.accountNo as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10"/>
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
            <shiro:hasPermission name="reserve-orgCode_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserve-orgCode_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="reserve-orgCode_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>账号</th>
                <th>机构号</th>
                <th>机构类型</th>
                <th>对方账户</th>
                <th>结算类型</th>
                <th>备注信息</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{vm.cached.RESERVE_INFO[bean.accountNo]['description']}}[{{bean.accountNo}}]</td>

                <td ng-if="<%=OrgType.PAY.getCode()%> == bean.orgType">
                    {{item.cachedTemp.COMANY_CODE[bean.orgCode]}}[{{bean.orgCode}}]
                </td>
                <td ng-if="<%=OrgType.MERCHANT.getCode()%> == bean.orgType">
                    {{item.cachedTemp.MERCHANT_CODE[bean.orgCode]}}[{{bean.orgCode}}]
                </td>
                <td>{{{
                    <%for (OrgType e : OrgType.values()) {%>
                    '<%=e.getCode()%>':'<%=e.getDesc()%>',
                    <%}%>
                    }[bean.orgType]}}</td>
                <td>{{bean.otherAccountNo}}</td>
                <td>{{bean.clearType}}</td>
                <td>{{bean.remarkDesc}}</td>
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
