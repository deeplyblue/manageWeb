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
    <script src="${ctx}/build/js/views/institution/orgbankApp-00847012ad.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询机构银行信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/orgbank/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    机构银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgBankCode" class="{{vm.constant.inputClass}}">
                </td> <td class="text-right">
                  渠道：
                </td>
                <td>

                    <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
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
<shiro:hasPermission name="orgbank_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="orgbank_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
    </shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>机构</th>
                <th>银行代码 </th>
                <th>机构银行代码</th>
                <th>渠道 </th>
                <th>银行类型</th>
                <th>启用标识 </th>
                <th>操作 </th>


            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>

                <%--<td>{{vm.cached.COMANY_CODE[bean.orgCode]}}({{bean.orgCode}})</td>--%>
                <td ng-if="bean.rowSpan != 0" style="text-align: center;vertical-align:middle;" rowspan="{{bean.rowSpan}}">
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.orgCode)">
                </td>
                <td ng-if="bean.rowSpan != 0" style="text-align: center;vertical-align:middle;" rowspan="{{bean.rowSpan}}">
                    {{vm.cached.COMANY_CODE[bean.orgCode]}}[{{bean.orgCode}}]</td>

                <td>{{bean.bankCode}}</td>
                <td>{{bean.orgBankCodeDesc}}{{bean.orgBankCode}}</td>
                <td>
                    <%--{{bean.bankType}}--%>
                        {{vm.cached.CONN_CHANNEL[bean.bankType.substring(0,2)]}}
                </td>
                <td>
                    {{vm.cached.BANK_TYPE[bean.bankType]}}
                </td>
                <td>{{vm.cached.ENABLE_FLAG[bean.enableFlag]}}</td>
<shiro:hasPermission name="orgbank_updateEnableFlag">
                <td><input type="button" value="{{{'1':'关闭','0':'启用'}[bean.enableFlag]}}" ng-click="vm.updateItemEnableFlag(bean)"/></td>
</shiro:hasPermission>
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
