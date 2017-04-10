<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/13
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/settleManage/feeCfgInfoApp-f465ad05f4.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询业务机构手续费 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/feeCfg/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    业务机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    接入渠道：
                </td>
                <td>

                        <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}"
                                ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
                            <option value="">请选择</option>
                        </select>

                </td>
                <td class="text-right">
                    启用状态:
                </td>
                <td>
                    <select ng-options="key as value for (key,value) in {'0':'禁用','1':'启用'}"
                            ng-model="vm.queryBean.enableFlag" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '<%=CompanyType.BUSINESS.getCode()%>'" ng-show="false" />
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        <table table-detail >
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>业务机构</th>
                <th>银行类型</th>
                <th>接入渠道</th>
                <%--<th>订单类型</th>--%>
                <%--<th>费率值</th>--%>
                <th>结算精确类型</th>
                <%--<th>最低手续费(元)</th>--%>
                <%--<th>商户起始交易时间</th>--%>
                <th>费率起始时间</th>
                <th>费率截至时间</th>
                <th>操作员</th>
                <th>是否返还</th>
                <th>启用标志</th>
                <%--<th>操作时间</th>--%>
                <%--<th>操作 </th>--%>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.feeCfgNo)">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}[{{bean.companyCode}}]</td>
                <td> {{bean.bankType}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td>{{bean.rateRoundType}}</td>
                <%--<td>{{bean.fixFee}}%</td>--%>
                <%--<td>{{vm.cached.RATE_ROUND_TYPE[bean.rateRoundType]}}</td>--%>
                <%--<td>{{bean.reFeeFlag  | currency:"" }}元</td>--%>
                <%--<td>{{bean.firstSumDate}}</td>--%>
                <td>{{bean.validateBeginDate  | date:"yyyy-MM-dd"}} </td>
                <td>{{bean.validateEndDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.modifier}}</td>
                <td>{{{'0':'不返还','1':'返还'}[bean.reFeeFlag]}}</td>
                <td>{{{'0':'禁用','1':'启用'}[bean.enableFlag]}}</td>
                <%--<td>{{bean.lastUpdTime | date:"yyyy-MM-dd hh:mm:ss"}}</td>--%>
                <%--<td>{{}}</td>--%>

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
