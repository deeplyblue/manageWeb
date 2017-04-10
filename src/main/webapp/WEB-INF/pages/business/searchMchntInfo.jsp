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
    <script src="${ctx}/build/js/views/business/MchntInfoApp-765d35f534.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询业务机构   "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/business/mchntInfo/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    业务机构简称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntAbbrName" class="form-control">
                    <%--<select ng-model="vm.queryBean.mchntCode" class="form-control"--%>
                    <%--ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">--%>
                    <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                </td>
                <td class="text-right">
                    业务机构状态：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntStatus" class="form-control">
                </td>
            </tr>
            <tr>

                <td class="text-right">
                    业务机构性质：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntType" class="form-control">
                </td>
                <td class="text-right">
                    是否有协议：
                </td>
                <td>

                    <input type="text" ng-model="vm.queryBean.guaranteeStatus" class="form-control">
                    <%--<select ng-model="vm.queryBean.enableFlag" class="form-control"--%>
                            <%--ng-options="key as value for (key,value) in vm.cached.ENABLE_FLAG">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
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
                <th>业务机构代码 </th>
                <th>业务机构简称 </th>
                <th>业务机构名称 </th>
                <th>业务机构性质 </th>
                <th>业务机构状态 </th>
                <th>接入渠道 </th>
                <th>上级机构(父级) </th>
                <th>开通人  </th>
                <th>是否有协议 </th>
                <th>操作</th>

            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.mchntCode)">
                </td>
                <td>{{bean.mchntCode}}</td>
                <td>{{bean.mchntAbbrName}}</td>
                <td>{{bean.mchntName}}</td>
                <td>{{bean.mchntType}}</td>
                <td>{{bean.mchntStatus}}</td>
                <td>{{bean.depChannelCode}}</td>
                <td>{{bean.parentOrgCode}}</td>
                <td>{{bean.openUser}}</td>
                <td>{{bean.guaranteeStatus}}</td>
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
