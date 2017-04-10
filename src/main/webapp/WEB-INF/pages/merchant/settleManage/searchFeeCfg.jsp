
<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/13
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/settleManage/feeCfgInfoApp-f465ad05f4.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户手续费 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/feeCfg/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>
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
            <input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '03'"
                   ng-show="false"/>
            <tr align="center">
                <td colspan="6">
<%--<shiro:hasPermission name="merchant-feeCfg_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
  <%--  </shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <shiro:hasPermission name="merchant-feeCfg_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="merchant-feeCfg_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>商户</th>
                <th>银行类型</th>
                <th>接入渠道</th>
                <th>结算精确类型</th>
                <th>费率起始时间</th>
                <th>费率截至时间</th>
                <th>创建人</th>
                <th>操作人</th>
                <th>是否返还</th>
                <th>启用标志</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}[{{bean.companyCode}}]</td>
                <td>{{vm.cached.BANK_CARD_TYPE[bean.bankType]}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel]}}</td>
                <td>{{{'1':'四舍五入','2':'舍位','3':'四舍五一入','4':'五舍五一入'}[bean.rateRoundType]}}</td>
                <td>{{bean.validateBeginDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.validateEndDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.creator}}</td>
                <td>{{bean.modifier}}</td>
                <td>{{{'0':'不返还','1':'返还'}[bean.reFeeFlag]}}</td>
                <td>{{{'0':'禁用','1':'启用'}[bean.enableFlag]}}</td>
                <td>
                    <shiro:hasPermission name="merchant-feeCfg_open">
                    <button type="button" ng-if="bean.enableFlag=='1'" ng-click="vm.updateItemEnableFlag(bean,{'0':'1','1':'0'}[bean.enableFlag])">
                        {{{'1':'禁用','0':'启用'}[bean.enableFlag]}}
                    </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="merchant-feeCfg_close">
                        <button type="button" ng-if="bean.enableFlag=='0'" ng-click="vm.updateItemEnableFlag(bean,{'0':'1','1':'0'}[bean.enableFlag])">
                            {{{'1':'禁用','0':'启用'}[bean.enableFlag]}}
                        </button>
                    </shiro:hasPermission>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
