<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/settlement/clear/day/mchntClearDayApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="商户日结明细查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/clear/day/mchntClearDay/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    商户代码
                </td>
                <td class="col-md-3" uib-tooltip="{{vm.cached.MERCHANT_CODE[vm.queryBean.orgCode]}}" tooltip-placement="right">
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td class="text-right">交易类型</td>
                <td>
                    <select ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.MCHNT_TRANS_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">渠道</td>
                <td>
                    <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    清算日期
                </td>
                <td colspan="3">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate"

                            required
                           required/>
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-model="vm.queryBean.endDate"

                                   required
                                  required/>
                </td>
                <td class="text-right">
                    机构代码
                </td>
                <td class="col-md-3">
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           typeahead-show-hint="true"/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>

                    <shiro:hasPermission name="mchntClearDay_down">
                        <button down-file="${ctx}/settlement/clear/day/mchntClearDay/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm" down-cfg="vm.downCfg" >
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
                <th>序号</th>
                <th>商户代码</th>
                <th>机构代码</th>
                <th>清算日期</th>
                <th>交易类型</th>
                <th>渠道</th>
                <th>交易笔数</th>
                <th>交易金额(元)</th>
                <th>佣金(元)</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode + '']}}({{bean.mchntCode}})</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode + '']}}({{bean.payOrgCode}})</td>
                <td>{{bean.settleDate | date:'yyyy-MM-dd'}}</td>
                <td>{{vm.cached.MCHNT_TRANS_CODE[bean.transCode + '']}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.connChannel + '']}}</td>
                <td>{{bean.transCount}}</td>
                <td>{{bean.transAmt / 100 | currency:''}}</td>
                <td>{{bean.feeAmt / 100 | currency:''}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td colspan="6" class="text-right">总计：</td>
                <td>{{vm.sumObject.transCountBP}}</td>
                <td>{{vm.sumObject.transAmtP /100 | currency:''}}</td>
                <td>{{(vm.sumObject.feeAmtP + vm.sumObject.feeAmtR) /100 | currency:''}}</td>
            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../../../common/pageFoot.jsp"/>
</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
