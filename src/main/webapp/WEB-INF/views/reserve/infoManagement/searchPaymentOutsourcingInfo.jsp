<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: JinXin
  Date: 2016/12/22
  查询支付机构分公司信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/paymentOutsourcingInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="支付机构业务外包信息管理"/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/paymentOutsourcingInfo/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"/>--%>
                        <input type="text" ng-model="vm.queryBean.orgNo"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"/>--%>
                        <input type="text" ng-model="vm.queryBean.orgName"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>

                </td>
            </tr>
            <tr>

                <td class="text-right">
                    外包服务机构名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.serviceOrgName" class="{{vm.constant.inputClass}}" />
                </td>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.businessCode" class="{{vm.constant.inputClass}}" />
                </td>

            </tr>

            <tr align="center">
                <td colspan="6" class="textCenter">
<%--<shiro:hasPermission name="merchant-contacts_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
<%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-contacts_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>

            <shiro:hasPermission name="merchant-contacts_update">
                    <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>

            <shiro:hasPermission name="institution-contact_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>机构编号</th>
                <th>机构名称</th>
                <th>外包服务机构名称</th>
                <th>营业执照编号</th>
                <th>经营范围</th>
                <th>组织机构代码</th>
                <th>外包服务内容</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list ">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.orgNo}}</td>
                <td>{{bean.orgName}}</td>
                <td>{{bean.serviceOrgName}}</td>
                <td>{{bean.businessCode}}</td>
                <td>{{bean.businessScope}}</td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.serviceInfo}}</td>
                <td>
<shiro:hasPermission name="merchant-contacts_open">
    <button  ng-click="vm.queryOutsourcingInfoDetail(bean)">查看</button>
    </shiro:hasPermission>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
