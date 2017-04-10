<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/transaction/queryOrderMainApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="主订单信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/orderMain/queryOrderMainList" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    商户代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    订单状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.orderStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.ORDER_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    平台流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.ourTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    原平台流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldOurTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    通知状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.notifyStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.NOTIFY_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    订单开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginOrderDate"/>
                </td>
                <td class="text-right">
                    订单结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endOrderDate"/>
                </td>
                <td class="text-right">
                    接入渠道
                </td>
                <td>
                    <select ng-model="vm.queryBean.channel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    数字用户号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.digitalPayAccountNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    清算开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginSettleDate"/>
                </td>
                <td class="text-right">
                    清算结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endSettleDate"/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" ng-disabled="queryForm.$invalid"
                            class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="orderMain_download">
                    <button type="button" ng-click="vm.download()" class="btn btn-default"
                            params="vm.queryBean">
                        下载
                    </button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>商户代码</th>
                <th>商户名称</th>
                <th>订单号</th>
                <th>订单日期</th>
                <th>平台流水号</th>
                <th>接入渠道</th>
                <th>订单金额(元)</th>
                <th>订单状态</th>
                <th>通知状态</th>
                <th>交易标识</th>
                <th>数字用户号</th>
                <th>清算日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.merchantCode}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.merchantCode]}}</td>
                <td>{{bean.orderNo}}</td>
                <td>{{bean.orderDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.ourTransNo}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.channel]}}</td>
                <td>{{bean.totalAmt | currency:''}}</td>
                <td>{{vm.cached.ORDER_STATUS[bean.orderStatus]}}</td>
                <td>{{vm.cached.NOTIFY_STATUS[bean.notifyStatus]}}</td>
                <td>{{vm.cached.REVERSE_FLAG[bean.reverseFlag]}}</td>
                <td>{{bean.digitalPayAccountNo}}</td>
                <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
                <td><button class="btn btn-default" ng-click="vm.queryOrderMainDetail(bean.ourTransNo)">查看</button></td>
            </tr>
            </tbody>
            <tfoot ng-if="vm.sumObject">
                <tr style="background-color:#f9f9f9">
                    <td colspan="13">总条数：{{vm.sumObject.sumCount}} 条, 总金额：{{vm.sumObject.sumTotalAmt}} 元</td>
                </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
