<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/24
  Time: 17:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../.././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/settlement/report/dailyReport/payClearRelationApp-c1e824bfcf.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="支付结算关系日报"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/daily/payRelation/search"
          class="form-inline" novalidate w5c-form-validate="vm.validateOptions"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td class="text-right">
                    应收日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.clrDateBegin" name="clrDateBegin" placeholder="必填项"
                           required
                           required/>
                    -&nbsp;
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.clrDateEnd" name="clrDateEnd" placeholder="必填项"
                           required
                           required/>
                </td>
            </tr>
            <input type="text" ng-model="vm.queryBean.clrType" ng-init="vm.queryBean.clrType = '02'" ng-show="false" />
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" ng-disabled="queryForm.$invalid"
                            class="btn btn-default">查询
                    </button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="payRelation_down">
                        <button down-file="${ctx}/settlement/report/daily/payRelation/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm">
                            下载
                        </button>
                    </shiro:hasPermission>
                    <div growl></div>
                </td>
            </tr>
        </table>
    </form>

    <div>

        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>机构</th>
                <th>应收本金</th>
                <th>应收日期</th>
                <th>实收日期</th>
                <th>应退本金</th>
                <th>实退日期</th>
                <th>商户</th>
                <th>应付本金</th>
                <th>是否已付</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{$index + 1}}</td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{bean.receivableAmt / 100 | currency:'￥'}}</td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{bean.clearDate | date:'yyyy-MM-dd'}}
                </td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{bean.cashTransDateR | date:'yyyy-MM-dd'}}
                </td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{bean.payableAmt / 100 | currency:'￥'}}</td>
                <td rowspan="{{bean.orgClearDetailModelList.length}}" style="text-align: center;vertical-align:middle;">
                    {{bean.cashTransDateP | date:'yyyy-MM-dd'}}
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.orgClearDetailModelList[0].orgCode]}}({{bean.orgClearDetailModelList[0].orgCode}})</td>
                <td>{{bean.orgClearDetailModelList[0].transAmtP / 100 | currency:'￥'}}</td>
                <td>{{vm.cached.CASH_TRANS_FLAG[bean.orgClearDetailModelList[0].cashTransFlag]}}</td>
            </tr>
            <tr ng-repeat="detail in bean.orgClearDetailModelList" ng-if="$index != 0">
                <td>{{vm.cached.MERCHANT_CODE[detail.orgCode]}}({{detail.orgCode}})</td>
                <td>{{detail.transAmtP / 100 | currency:'￥'}}</td>
                <td>{{vm.cached.CASH_TRANS_FLAG[detail.cashTransFlag]}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../../../common/pageFoot.jsp"/>

</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
