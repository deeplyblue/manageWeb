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
    <script src="${ctx}/build/js/views/merchant/settleManage/merchantClearAccountApp-a9c0f8809b.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询业务机构账户信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/settle/account/search">
        <table table-detail>
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantCode" class="form-control"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6" />
                </td>
                <td class="text-right">
                    银行账户：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />
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
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <%--<input type="checkbox" name="checkAll">--%>
                </th>
                <th>商户</th>
                <th>账户名</th>
                <th>标准开户行</th>
                <th>联行号</th>
                <th>账户号</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.merchantCode]}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.bankName}}</td>
                <td>{{bean.cnapsCode}}</td>
                <td>{{bean.accountNo}}</td>
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
