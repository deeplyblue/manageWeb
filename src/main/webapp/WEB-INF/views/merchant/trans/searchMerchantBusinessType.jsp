<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/merchant/trans/merchantBusinessTypeApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户业务资金源配置 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/business/type/search" class="form-inline">
        <table table-form>
            <tr >
                <td class="text-right">
                    业务资金源：
                </td>
                <td>
                    <select ng-model="vm.queryBean.businessType" class="{{vm.constant.inputClass}}"
                    ng-options="key as value for (key,value) in vm.cached.BUSINESS_FUNDING_SOURCE">
                        <option value="" >请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantCode" placeholder="根据简称获取商代码" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>
                </td>
            </tr>


            <tr align="center">
                <td colspan="6" class="textCenter">
                    <%--<shiro:hasPermission name="merchant-business-type_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                  <%--  </shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
<shiro:hasPermission name="merchant-business-type_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
    </shiro:hasPermission>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>商户代码</th>
                <th>商户简称</th>
                <th>业务代码</th>
                <th>业务描述</th>
                <th>启用标记</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.merchantCode}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.merchantCode]}}</td>
                <td>{{bean.businessType}}</td>
                <td>{{bean.businessTypeDesc}}</td>
                <td>{{{'0':'关闭','1':'启用'}[bean.enableType]}}</td>
                <td>
<shiro:hasPermission name="merchant-business-type_open">
                    <button type="button" ng-if="bean.enableType=='0'" value="0" ng-click="vm.updateItemEnableFlag(bean)">启用</button>
    </shiro:hasPermission>
<shiro:hasPermission name="merchant-business-type_close">
                    <button type="button" ng-if="bean.enableType=='1'" value="1" ng-click="vm.updateItemEnableFlag(bean)">关闭</button>
</shiro:hasPermission>
                </td>
    </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
