<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/orgCheckFileApp-c7b5e99913.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="批量交易文件管理"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/orgCheckFile/searchBatch" class="form-inline" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                交易类型
            </td>
                <td>
                    <select  ng-model="vm.queryBean.busiType" class="{{vm.constant.inputClass}}" ng-init="vm.queryBean.busiType='B1'">
                        <option value="B1">交易请求</option>
                        <option value="B2">交易返回</option>
                    </select>
                </td>

                <td class="text-right">
                    银行清算时间
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.bankSettleDate"
                           />
                </td>
                <td class="text-right">
                    上传日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.lastUpdTime"
                           />
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
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>机构/商户名称</th>
                <th>上传文件名称</th>
                <th>状态</th>
                <th>操作员</th>
                <th>文件类型</th>
                <th>银行清算日期</th>
                <th>上传日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{bean.companyCode}}({{vm.cached.ALL_COMPANY_CODE[bean.companyCode]}})</td>
                <td>{{bean.localFilename}}</td>
                <td>{{vm.cached.UPLOAD_STATUS[bean.status]}}</td>
                <td>{{bean.operator}}</td>
                <td>{{{'B1':'交易请求','B2':'交易返回'}[bean.busiType]}}</td>
                <td>{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.lastUpdTime  | date:"yyyy-MM-dd"}}</td>
                <td><shiro:hasPermission name="orgCheckFile-batch_down">
                    <input type="button" value="下载" ng-click="vm.downFile(bean)"/></td>
                </shiro:hasPermission>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
