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
    <script src="${ctx}/js/views/reserve/bankTransApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金银行交易信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/bank/trans/down/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.accountNo as item.bankCode for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    交易时间：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.queryBean.date"

                            required
                           required/>
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
            <%--<button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>账号</th>
                <th>时间</th>
                <th>下载状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.date|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.status}}</td>
                <td><input type="button" value="重新处理" ng-click="vm.resetItem(bean)"></td>
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
