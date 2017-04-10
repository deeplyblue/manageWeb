<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.oriental.manage.core.enums.CompanyType" %>
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
    <script src="${ctx}/js/views/merchant/trans/merchantTransRightApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户交易权限配置 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/trans/right/search" >
        <table table-form>
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <input type="text"  ng-model="vm.queryBean.companyCode"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6"   class="{{vm.constant.inputClass}} " />
                    <input type="text" ng-hide="true" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType='<%=CompanyType.MERCHANT.getCode()%>'">

                </td>
                <td class="text-right">
                    分账标识：
                </td>
                <td>
                    <select ng-model="vm.queryBean.splitRightFlag"  ng-options="key as value for (key,value) in vm.splitRightFlag"  class="{{vm.constant.inputClass}} " >
                        <option value="">请选择</option>
                    </select>
                </td>

                <td class="text-right">
                    正反标识：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reverseFlag"  ng-options="key as value for (key,value) in vm.reverseFlag"  class="{{vm.constant.inputClass}} " >
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="8" class="textCenter">
<%--<shiro:hasPermission name="merchant-trans-right_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
<%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-trans-right_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-trans-right_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-trans-right_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>商户</th>
                <th>接入渠道</th>
                <th>订单类型</th>
                <th>正反标识</th>
                <th>分账标识</th>
                <th>退款状态</th>
                <th>修改人</th>
            </tr>
            </thead>
            <tbody >
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td ng-if="bean.rowSpan != 0" style="text-align: center;vertical-align:middle;" rowspan="{{bean.rowSpan}}">
                    <input name="check" type="checkbox" ng-model="bean._checked">
                    <%--<input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.companyCode)">--%>
                </td>
                <td ng-if="bean.rowSpan != 0" style="text-align: center;vertical-align:middle;" rowspan="{{bean.rowSpan}}">{{vm.cached.MERCHANT_CODE[bean.companyCode]}}[{{bean.companyCode}}]</td>
                <td style="text-align: center;">{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td style="text-align: center;">{{vm.cached.TRANS_CODE_ALL[bean.transCode]}}</td>
                <td style="text-align: center;">{{vm.reverseFlag[bean.reverseFlag]}}</td>
                <td style="text-align: center;">{{vm.splitRightFlag[bean.splitRightFlag]}}</td>
                <td style="text-align: center;">{{vm.cached.REFUND_FLAG[bean.refundFlag]}}</td>
                <td style="text-align: center;">{{bean.modifyUser}}</td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
