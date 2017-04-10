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
    <script src="${ctx}/js/views/opcif/opcifAuthapplyBankApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="客户认证信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/opcif/opcifAuthapplyBank/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td>
                    认证申请编号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.authapplyId" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    申请操作员:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.operatorNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    申请渠道:
                </td>
                <td>
                    <select ng-model="vm.queryBean.applyChannel" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPCIF_APPLY_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    申请客户号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.customerNo" class="{{vm.constant.inputClass}}" />
                </td>
                <td>
                    申请登录号:
                </td>
                <td class="col-md-3">
                    <input type="text" ng-model="vm.queryBean.loginNo" class="{{vm.constant.inputClass}}" />
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
                    申请状态:
                </td>
                <td>
                    <select ng-model="vm.queryBean.authStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.OPCIF_AUTH_STATUS">
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
                <td>
                    申请时间:
                </td>
                <td colspan="3">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-blur="vm.checkStartDay(vm.queryBean.startDate,vm.queryBean.endDate)"    ng-model="vm.queryBean.startDate" />
                    -&nbsp;<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                                  ng-blur="vm.checkEndDay(vm.queryBean.startDate,vm.queryBean.endDate)"  ng-model="vm.queryBean.endDate" />
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
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>认证申请编号</th>
                <th>用户号</th>
                <th>客户号</th>
                <th>证件号</th>
                <th>登录号</th>
                <th>申请时间</th>
                <th>申请渠道</th>
                <th>状态</th>
                <th>认证失败原因</th>
                <th>描述</th>
                <th>帐号类型</th>
                <th>银行预留手机号</th>
                <th>卡号后4位</th>
                <th>银行名称</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    {{$index+1}}
                </td>
                <td>{{bean.authapplyId}}</td>
                <td>{{bean.operatorNo}}</td>
                <td>{{bean.customerNo}}</td>
                <td>{{bean.certificateNo}}</td>
                <td>{{bean.loginNo}}</td>
                <td>{{bean.applyDatetime | date:'yyyy-MM-dd'}}</td>
                <td>{{vm.cached.OPCIF_APPLY_CHANNEL[bean.applyChannel + '']}}</td>
                <td>{{vm.cached.OPCIF_AUTH_STATUS[bean.authStatus + '']}}</td>
                <td>{{bean.failReason}}</td>
                <td>{{bean.description}}</td>
                <td>{{vm.cached.OPCIF_BANK_CARD_TYPE[bean.bankCardType + '']}}</td>
                <td>{{bean.mobile}}</td>
                <td>{{bean.cardnosuf}}</td>
                <td>{{bean.bankName}}</td>
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
