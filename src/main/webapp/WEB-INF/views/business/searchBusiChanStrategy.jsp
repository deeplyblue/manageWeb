<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/business/BusiChanStrategyApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="业务机构路由配置信息   "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/business/busiChanStrategy/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    业务机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgCode" class="form-control">
                    <%--<select ng-model="vm.queryBean.mchntCode" class="form-control"--%>
                    <%--ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">--%>
                    <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                </td>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="form-control">
                </td>
                <td class="text-right">
                    启用标识：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.enableFlag" class="form-control">

                </td>
            </tr>
            <tr>

                <td class="text-right">
                    公私标识：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.ppFlag" class="form-control">--%>
                    <select ng-model="vm.queryBean.ppFlag" class="form-control"
                            ng-options="key as value for (key,value) in vm.cached.CFG_FLAG">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    限额：
                </td>
                <td>

                    <%--<input type="text" ng-model="vm.queryBean.sizeLimitFlag" class="form-control">--%>
                    <select ng-model="vm.queryBean.sizeLimitFlag" class="form-control"
                            ng-options="key as value for (key,value) in vm.cached.QUOTA_FLAG">
                        <option value="">请选择</option>
                    </select>

                </td>
            </tr>

            <tr align="center">
                <td colspan="6">
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
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>银行 </th>
                <th>借贷标记  </th>
                <th>公私标记  </th>
                <th>限额标记  </th>
                <th>业务机构 </th>
                <th>启用标识  </th>
                <th>操作  </th>


            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.bankCode}}</td>
                <td>{{vm.cached.CARD_TYPE[bean.cdFlag]}}</td>
                <td>{{vm.cached.CFG_FLAG[bean.ppFlag]}}</td>
                <td>{{vm.cached.QUOTA_FLAG[bean.sizeLimitFlag]}}</td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.enableFlag}}</td>
                <td></td>
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
