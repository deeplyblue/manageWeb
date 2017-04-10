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
    <script src="${ctx}/build/js/views/merchant/itconfig/merchantKeyInfoApp-a81d445027.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户密钥配置 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/itCfg/key/search"  class="form-inline">
        <table table-form>
            <tr align="center">

                <td>商户代码：
                    <input type="text" ng-model="vm.queryBean.companyCode" class="form-control"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6" class="{{vm.constant.inputClass}} "/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-key_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-key_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th width="20%">商户名称</th>
                <th width="20%">数据加密KEY有效期</th>
                <th width="20%">RSA公钥对有效期</th>
                <th width="20%">信息验证类型</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}</td>
                <td>{{bean.dataKeyValidDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.rsaKeyValidDate|date:'yyyy-MM-dd'}}</td>
                <td>{{vm.cached.ENCTYPE[bean.encType]}}</td>
                <td>
<shiro:hasPermission name="merchant-dataKey_query">
                    <button class="btn btn-default" ng-click="vm.queryDataKey(bean.id)">查看数据KEY</button>
    </shiro:hasPermission>
<shiro:hasPermission name="merchant-RSAKey_query">
                    <button class="btn btn-default" ng-click="vm.queryRSAKey(bean.id)">查看RSA公钥</button>
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
