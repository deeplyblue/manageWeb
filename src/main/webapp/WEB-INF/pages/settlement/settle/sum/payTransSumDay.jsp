<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <script src="${ctx}/build/js/views/settlement/settle/sum/payTransSumDayApp-fba5c9fcdf.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="支付交易汇总查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/settle/sum/payTransSumDay/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    汇总方式
                </td>
                <td class="col-md-10">
                    <input type="radio" ng-model="vm.queryBean.radioValue" value="01" />
                    支付机构汇总
                    <input type="radio" ng-model="vm.queryBean.radioValue" value="02" />
                    支付机构子银行汇总
                </td>
            </tr>
            <tr>
                <td class="text-right col-md-2">
                    支付机构代码
                </td>
                <td uib-tooltip="{{vm.cached.COMANY_CODE[vm.queryBean.orgCode]}}" tooltip-placement="right">
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
            </tr>
            <tr ng-if="vm.queryBean.radioValue == '02'">
                <td class="text-right col-md-2">
                    银行名称
                </td>
                <td uib-tooltip="{{vm.cached.BANK_INFO[vm.queryBean.bankCode]}}" tooltip-placement="right">
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('BANK_INFO') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
            </tr>
            <tr>
                <td class="text-right col-md-2">
                    清算日期
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
            <tr>
                <td class="text-right col-md-2">
                    查询类型
                </td>
                <td>
                    <select ng-model="vm.queryBean.sumType" class="{{vm.constant.inputClass}}" ng-init = "vm.queryBean.sumType = '01'"
                            ng-options="key as value for (key,value) in {'01':'按天汇总','02':'按时间段汇总'}">

                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="payTransSumDay_down">
                    <button down-file="${ctx}/settlement/settle/sum/payTransSumDay/download" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default" down-cfg="vm.downCfg">
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
                <th ng-if="vm.queryBean.radioValue == '02'" rowspan="2">银行名称</th>
                <th rowspan="2">日期</th>
                <th colspan="2">交易情况</th>
                <th colspan="3">支付成功</th>
                <th colspan="3">退款成功</th>
                <th colspan="3">支付失败</th>
            </tr>
            <tr>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>成功率</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>退款率</th>
                <th>笔数</th>
                <th>金额(元)</th>
                <th>失败率</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode + '']}}({{bean.orgCode}})</td>
                <td ng-if="vm.queryBean.radioValue == '02'">{{vm.cached.BANK_INFO[bean.bankCode + '']}}</td>
                <td>{{bean.sumDate | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.transCountSum}}</td>
                <td>{{bean.transAmtSum /100 | currency:''}}</td>
                <td>{{bean.transCountBP}}</td>
                <td>{{bean.transAmtP /100 | currency:''}}</td>
                <td>{{bean.rateP}}</td>
                <td>{{bean.transCountBR}}</td>
                <td>{{bean.transAmtR /100 | currency:''}}</td>
                <td>{{bean.rateR}}</td>
                <td>{{bean.transCountSumC}}</td>
                <td>{{bean.transAmtSumC /100 | currency:''}}</td>
                <td>{{bean.rateC}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td ng-if="vm.queryBean.radioValue == '01'" colspan="3" class="text-right">总计：</td>
                <td ng-if="vm.queryBean.radioValue == '02'" colspan="4" class="text-right">总计：</td>
                <td>{{vm.sumObject.transCountSum}}</td>
                <td>{{vm.sumObject.transAmtSum /100 | currency:''}}</td>
                <td>{{vm.sumObject.transCountBP}}</td>
                <td>{{vm.sumObject.transAmtP /100 | currency:''}}</td>
                <td>{{vm.sumObject.rateP}}</td>
                <td>{{vm.sumObject.transCountBR}}</td>
                <td>{{vm.sumObject.transAmtR /100 | currency:''}}</td>
                <td>{{vm.sumObject.rateR}}</td>
                <td>{{vm.sumObject.transCountSumC}}</td>
                <td>{{vm.sumObject.transAmtSumC /100 | currency:''}}</td>
                <td>{{vm.sumObject.rateC}}</td>
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
