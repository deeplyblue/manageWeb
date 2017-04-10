<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/transaction/queryPayTransApp-55aa78b22b.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="支付订单信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/payTrans/queryPayTransList" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">
                    订单号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    交易状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.transStatus" class="{{vm.constant.inputClass}}"
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
                    支付请求流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payReqNo" class="{{vm.constant.inputClass}}">
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
                    支付流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    原支付流水号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldPayTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    支付类型
                </td>
                <td>
                    <select ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.PAY_TRANS_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行清算开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginOrderDate"/>
                </td>
                <td class="text-right">
                    银行清算结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endOrderDate"/>
                </td>
                <td class="text-right">
                    交易标识
                </td>
                <td>
                    <select ng-model="vm.queryBean.reverseFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.REVERSE_FLAG">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
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
                <td class="text-right">
                    删除标识
                </td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}" ng-init="vm.queryBean.deleteFlag = '0'"
                            ng-options="key as value for (key,value) in vm.cached.DELETE_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付响应流水号
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}"
                           ng-model="vm.queryBean.payRespNo"/>
                </td>
                <td class="text-right">
                    身份证号
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}"
                           ng-model="vm.queryBean.certCode"/>
                </td>
                <td class="text-right">
                    手机号
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}"
                           ng-model="vm.queryBean.payUserMobile"/>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
<shiro:hasPermission name="payTrans_download">
                    <button down-file="${ctx}/payTrans/download" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default">
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
                <th>支付机构代码</th>
                <th>支付机构名称</th>
                <th>订单号</th>
                <th>接入渠道</th>
                <th>平台流水号</th>
                <th>支付类型</th>
                <th>支付请求流水号</th>
                <th>支付请求日期</th>
                <th>支付金额(元)</th>
                <th>交易状态</th>
                <th>交易标识</th>
                <th>清算日期</th>
                <th>银行清算日期</th>
                <th>删除标识</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.payOrgCode}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}</td>
                <td>{{bean.orderNo}}</td>
                <td>{{vm.cached.CONN_CHANNEL[bean.channel]}}</td>
                <td>{{bean.ourTransNo}}</td>
                <td>{{vm.cached.PAY_TRANS_CODE[bean.transCode]}}</td>
                <td>{{bean.payReqNo}}</td>
                <td>{{bean.payReqDate}}</td>
                <td>{{bean.transAmt | currency:''}}</td>
                <td>{{vm.cached.ORDER_STATUS[bean.transStatus]}}</td>
                <td>{{vm.cached.REVERSE_FLAG[bean.reverseFlag]}}</td>
                <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.paySettleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{vm.cached.DELETE_STATUS[bean.deleteFlag]}}</td>
                <td><button class="btn btn-default" ng-click="vm.queryPayTransDetail(bean.payTransNo)">查看</button></td>
            </tr>
            </tbody>
            <tfoot ng-if="vm.sumObject">
                <tr style="background-color:#f9f9f9">
                    <td colspan="15">总条数：{{vm.sumObject.sumCount}} 条, 总金额：{{vm.sumObject.sumTotalAmt}} 元</td>
                </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
