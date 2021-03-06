<%--
  Created by IntelliJ IDEA.
  User: zhangxinhai
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/settlement/clear/detail/payFeeClearDetailApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="支付手续费结算查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/clear/detail/payClearDetail/search" class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    支付机构代码
                </td>
                <td class="col-md-3">
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate"

                            required
                           required/>
                </td>
                <td>
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-model="vm.queryBean.endDate"

                                   required
                                  required/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="payFeeClearDetail_down">
                        <button down-file="${ctx}/settlement/clear/detail/payClearDetail/downloadFeeClear" params="vm.queryBean"
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
                <th rowspan="2">序号</th>
                <th rowspan="2">支付机构代码</th>
                <th colspan="2">交易日期</th>
                <th rowspan="2">结算日期</th>
                <th rowspan="2">结算批次号</th>
                <th rowspan="2">结算标识</th>
                <th rowspan="2">结算类型</th>
                <th rowspan="2">手续费收取方式</th>
                <th rowspan="2">应收笔数</th>
                <th rowspan="2">应收金额（元）</th>
                <th rowspan="2">应退笔数</th>
                <th rowspan="2">应退金额（元）</th>
                <th rowspan="2">本金净额（元）</th>
                <th rowspan="2">应付手续费（元）</th>
                <th rowspan="2">应退手续费（元）</th>
                <th rowspan="2">手续费净额（元）</th>
                <th rowspan="2">操作</th>
            </tr>
            <tr>
                <th>起始</th>
                <th>截止</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode + '']}}({{bean.orgCode}})</td>
                <td>{{bean.settleDateBegin | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.settleDateEnd | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.clrDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.clrBatchNo}}</td>
                <td>{{vm.cached.CLR_FLAG[bean.clrFlag + '']}}</td>
                <td>{{vm.cached.CLR_TYPE[bean.clrType + '']}}</td>
                <td>{{vm.cached.FEE_CLEAR_TYPE[bean.feeClearType]}}</td>
                <td>{{bean.transCountR}}</td>
                <td>{{bean.transAmtR / 100 | currency:''}}</td>
                <td>{{bean.transCountP}}</td>
                <td>{{bean.transAmtP / 100 | currency:''}}</td>
                <td>{{(bean.transAmtR / 100-bean.transAmtP / 100) | currency:''}}</td>
                <td>{{bean.feeAmtP / 100 | currency:''}}</td>
                <td>{{bean.feeAmtR / 100 | currency:''}}</td>
                <td>{{(bean.feeAmtP / 100-bean.feeAmtR / 100) | currency:''}}</td>
                <td>
                         <button ng-click="vm.redirectPayClearDay(bean)">结算明细</button>
                </td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td colspan="9" class="text-right">总计：</td>
                <td>{{vm.sumObject.transCountBR}}</td>
                <td>{{vm.sumObject.transAmtR / 100 | currency:''}}</td>
                <td>{{vm.sumObject.transCountBP}}</td>
                <td>{{vm.sumObject.transAmtP / 100 | currency:''}}</td>
                <td>{{(vm.sumObject.transAmtR / 100-vm.sumObject.transAmtP / 100) | currency:''}}</td>
                <td>{{vm.sumObject.feeAmtP / 100 | currency:''}}</td>
                <td>{{vm.sumObject.feeAmtR / 100 | currency:''}}</td>
                <td>{{(vm.sumObject.feeAmtP / 100-vm.sumObject.feeAmtR / 100) | currency:''}}</td>
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
