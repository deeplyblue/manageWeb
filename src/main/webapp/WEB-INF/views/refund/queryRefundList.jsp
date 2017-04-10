<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/refund/refundApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="退款信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/refund/queryRefundList" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    银行代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgBankCode" class="{{vm.constant.inputClass}}">
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
                    商户号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mchntCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    原支付请求流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldPayReqNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    退款状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.refundStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.REFUND_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    申请开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.refundBeginDate"

                            required
                           required/>
                </td>
                <td class="text-right">
                    申请结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.refundEndDate"

                            required
                           required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    退款类型
                </td>
                <td>
                    <select ng-model="vm.queryBean.errType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.REFUND_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    审核开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.auditBeginDate"


                           required/>
                </td>
                <td class="text-right">
                    审核结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.auditEndDate"


                           required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    原平台流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldOurTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    处理开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.proBeginDate"


                           required/>
                </td>
                <td class="text-right">
                    处理结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.proEndDate"


                           required/>
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
                <th>商户号</th>
                <th>商户名称</th>
                <th>支付机构代码</th>
                <th>支付机构名称</th>
                <th>订单号</th>
                <th>支付请求流水号</th>
                <th>银行代码</th>
                <th>接入渠道</th>
                <th>订单金额</th>
                <th>退款金额</th>
                <th>退款类型</th>
                <th>退款状态</th>
                <th>申请人</th>
                <th>申请日期</th>
                <th>审核人</th>
                <th>审核日期</th>
                <th>处理人</th>
                <th>处理日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.mchntCode}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode]}}</td>
                <td>{{bean.payOrgCode}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}</td>
                <td>{{bean.orderNo}}</td>
                <td>{{bean.oldPayReqNo}}</td>
                <td>{{bean.orgBankCode}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.channel]}}</td>
                <td>{{bean.totalAmt | currency:''}}</td>
                <td>{{bean.refundAmt | currency:''}}</td>
                <td>{{vm.cached.REFUND_TYPE[bean.errType]}}</td>
                <td>{{vm.cached.REFUND_STATUS[bean.refundStatus]}}</td>
                <td>{{bean.refundApplicant}}</td>
                <td>{{bean.createTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.auditor}}</td>
                <td>{{bean.auditDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.procEmpId}}</td>
                <td>{{bean.procDatetime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>
                    <div ng-show="bean.refundStatus == 2">
<shiro:hasPermission name="refund_onlineRefund">
                        <button class="btn btn-default" ng-click="vm.onlineRefund(bean.id)">线上退款</button>
                   </shiro:hasPermission>
                    </div>
                </td>
            </tr>
            </tbody>
            <tfoot ng-if="vm.sumObject">
            <tr style="background-color:#f9f9f9">
                <td colspan="19">总条数：{{vm.sumObject.sumCount}} 条, 总退款金额：{{vm.sumObject.sumTotalAmt}} 元</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
