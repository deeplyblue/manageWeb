<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: wuxg
  Date: 2016/6/14
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/reserve/prepaidCard/prepaidCardApp-44784c6289.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询预付卡信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/prepaidCard/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    预付卡卡号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.prepaidCardNum" class="form-control" />
                </td>
                <td class="text-right">
                    预付款状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="form-control" ng-options="key as value for (key,value) in {'0':'未赎回','1':'已申请赎回','2':'已划转资金'}" >
                        <option value="">请选择</option>
                    </select>
                </td>

                <td class="text-right">
                    自有资金划转时间：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.begin"/>
                    ——
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.end"/>
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
            <!--<button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>-->
            <shiro:hasPermission name="prepaidCard_update">
              <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <!--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>-->
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>卡号</th>
                <th>卡类型</th>
                <th>金额（元）</th>
                <th>申请赎回时间</th>
                <th>自有账户资金划转时间</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.prepaidCardNum}}</td>
                <td>{{bean.prepaidCardType}}</td>
                <td>{{bean.prepaidCardAmt}}</td>
                <td>{{bean.ransomDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.fundsTransferDate | date:'yyyy-MM-dd'}}</td>
                <td>{{{'0':'未赎回','1':'已申请赎回','2':'已划转资金'}[bean.status]}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td>总条数:</td>
                <td>{{vm.sumObject.rowCount}}</td>
                <td>总金额:</td>
                <td>{{vm.sumObject.allAmt/100}}</td>
            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
