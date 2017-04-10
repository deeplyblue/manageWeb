<%--
  Created by IntelliJ IDEA.
  User: zhangxinhai
  Date: 2016/8/25
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/opcif/opcifBindCardApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="客户绑卡信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/opcif/opcifBindCard/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td>
                    协议号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.agreementNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    客户号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.customerNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    协议类型:
                </td>
                <td>
                    <select ng-model="vm.queryBean.agreementType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPCIF_AGREEMENT_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    账户姓名:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.accountName" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    支付机构代码:
                </td>
                <td class="col-md-3">
                    <input type="text" ng-model="vm.queryBean.orgBankCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td>
                    帐号类型:
                </td>
                <td>
                    <select ng-model="vm.queryBean.bankCardType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPCIF_BANK_CARD_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    卡号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.cardNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    银行预留手机号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mobile" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    协议状态:
                </td>
                <td>
                    <select ng-model="vm.queryBean.agreementStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPCIF_AGREEMENT_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    证件号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.certificateNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td >
                    协议申请号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.authApplyId" class="{{vm.constant.inputClass}}">
                </td>
                <td>
                    签约时间:
                </td>
                <td colspan="3">
                    <input type="text" class="{{vm.constant.inputClass}}" style="width: 90px;" uib-datepicker-popup="yyyy-MM-dd"
                           ng-blur="vm.checkStartDay(vm.queryBean.startDate,vm.queryBean.endDate)"      ng-model="vm.queryBean.startDate" />
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" style="width: 90px;" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-blur="vm.checkEndDay(vm.queryBean.startDate,vm.queryBean.endDate)"    ng-model="vm.queryBean.endDate" />
                </td>
            </tr>
            <tr>
                <td >
                    用户号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.entityNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <button down-file="${ctx}/opcif/opcifBindCard/download" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default">
                        下载
                    </button>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>协议号</th>
                <th>客户号</th>
                <th>用户号</th>
                <th>协议类型</th>
                <th>签约时间</th>
                <th>账户姓名</th>
                <th>卡号后4位</th>
                <th>支付机构</th>
                <th>帐号类型</th>
                <th>银行预留手机号</th>
                <th>协议状态</th>
                <th>卡有效期</th>
                <th>发卡行城市</th>
                <th>银行名称</th>
                <th>协议申请号</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{bean.agreementNo}}</td>
                <td>{{bean.customerNo}}</td>
                <td>{{bean.entityNo}}</td>
                <td>{{vm.cached.OPCIF_AGREEMENT_TYPE[bean.agreementType + '']}}</td>
                <td>{{bean.signDatetime | date:'yyyy-MM-dd'}}</td>
                <td>{{bean.accountName}}</td>
                <td>{{bean.cardnosuf}}</td>
                <td ng-if="bean.orgBankCode!=null">
                    {{vm.cached.COMANY_CODE[bean.orgBankCode + '']}}({{bean.orgBankCode}})
                </td>
                <td ng-if="bean.orgBankCode==null">

                </td>
                <td>{{vm.cached.OPCIF_BANK_CARD_TYPE[bean.bankCardType + '']}}</td>
                <td>{{bean.mobile}}</td>
                <td>{{vm.cached.OPCIF_AGREEMENT_STATUS[bean.agreementStatus + '']}}</td>
                <td>{{bean.accountValiddatetime}}</td>
                <td>{{bean.cardIssuersCity}}</td>
                <td>{{bean.bankName}}</td>
                <td>{{bean.authApplyId}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
</html>
