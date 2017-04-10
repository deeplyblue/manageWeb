<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/payment/paymentApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="付款信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/payment/searchPaymentBatchList" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    文件名称
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.queryOrderNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    上传开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate"/>
                </td>
                <td class="text-right">
                    上传结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <option value="01">待审核</option>
                        <option value="02">已审核</option>
                        <option value="03">取消</option>
                        <option value="04">已执行</option>
                    </select>
                </td>
                <td class="text-right">
                    处理开始日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.operateBeginDate"/>
                </td>
                <td class="text-right">
                    处理结束日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.operateEndDate"/>
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
        <p class="btn-group">
            <shiro:hasPermission name="payment_upload">
                <button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i>业务上传</button>
            </shiro:hasPermission>
            <shiro:hasPermission name="payment_confirmUpload">
                <button ng-click="vm.confirmUpload()"><i class="glyphicon glyphicon-plus"></i>财务上传</button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>文件名</th>
                <th>批次号</th>
                <th>总笔数</th>
                <th>总金额(元)</th>
                <th>上传人</th>
                <th>上传时间</th>
                <th>审核人</th>
                <th>审核时间</th>
                <th>处理人</th>
                <th>处理时间</th>
                <th>当前状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.queryOrderNo}}.xls</td>
                <td>{{bean.batchNo}}</td>
                <td>{{bean.sumNum}}</td>
                <td>{{bean.sumAmt | currency:''}}</td>
                <td>{{bean.creator}}</td>
                <td>{{bean.reqTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.auditor}}</td>
                <td>{{bean.auditTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.operator}}</td>
                <td>{{bean.operateTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>
                    <div ng-show="bean.status == '01'">待审核</div>
                    <div ng-show="bean.status == '02'">已审核</div>
                    <div ng-show="bean.status == '03'">取消</div>
                    <div ng-show="bean.status == '04'">已执行</div>
                </td>
                <td>
                    <button class="btn btn-default" ng-click="vm.queryPaymentDetail(bean.batchNo)">查看</button>
                    <div ng-show="bean.status == '01'">
                        <shiro:hasPermission name="payment_cancel">
                            <button class="btn btn-default" ng-click="vm.cancel(bean.batchNo)">取消</button>
                        </shiro:hasPermission>
                    </div>
                       <div ng-show="bean.status == '02'">
                        <shiro:hasPermission name="payment_financeCancel">
                            <button class="btn btn-default" ng-click="vm.cancel(bean.batchNo)">取消</button>
                        </shiro:hasPermission>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
