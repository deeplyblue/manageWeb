<%--
  Created by IntelliJ IDEA.
  User: zhangxinhai
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/settlementfront/orgTransDetailApp-1c2064d047.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="结算明细查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlementfront/orgTransDetail/search" class="form-inline"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    商户代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td class="text-right">
                    支付机构代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td class="text-right">
                    交易流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.innerPayTransNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    渠道流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankReqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    商户流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.busiReqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    交易日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate"
                           required
                           required/>
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
<shiro:hasPermission name="orgTransDetail_download">
                    <button down-file="${ctx}/settlementfront/orgTransDetail/download" params="vm.queryBean"
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
                <th>机构</th>
                <th>商户</th>
                <th>交易金额</th>
                <th>交易日期</th>
                <th>支付单号</th>
                <th>渠道流水号</th>
                <th>商户流水号</th>
                <th>商户付款状态</th>
                <th>渠道收款状态</th>
                <th>应收日期</th>
                <th>实收日期</th>
                <th>应付日期</th>
                <th>实付日期</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode + '']}}({{bean.payOrgCode}})</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode + '']}}({{bean.mchntCode}})</td>
                <td>{{bean.transAmt / 100 | currency:''}}</td>
                <td>{{bean.mchntSettleDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.innerPayTransNo}}</td>
                <td>{{bean.bankReqNo}}</td>
                <td>{{bean.busiReqNo}}</td>
                <td>{{vm.cached.CASH_TRANS_FLAG[bean.mchntCashTransFlag]}}</td>
                <td>{{vm.cached.CASH_TRANS_FLAG[bean.payCashTransFlag]}}</td>
                <td>{{bean.payClearDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.payCashTransDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.mchntClearDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.mchntCashTransDate | date:'yyyy-MM-dd'}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td colspan="4" class="text-right">总计：</td>
                <td>总笔数：</td>
                <td>{{vm.sumObject.pageSum}}</td>
                <td>总金额：</td>
                <td>{{vm.sumObject.amtSum / 100 | currency:''}}</td>
                <td>本页笔数：</td>
                <td>{{vm.sumObject.pageCount}}</td>
                <td>本页金额：</td>
                <td>{{vm.sumObject.pageAmt / 100 | currency:''}}</td>
            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
</html>
