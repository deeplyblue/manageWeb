<%@ page import="com.oriental.check.commons.enums.CompanyType" %>
<%@ page import="com.oriental.check.commons.enums.Status" %>
<%--
  User: 蒯越
  Date: 2016/5/11
  Time: 9:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/check/chkFileInfoApp.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="对账文件信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/chkFileInfo/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">机构或商户代码</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('ALL_COMPANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td></td>
                <td></td>
                <td class="text-right">机构或商户类型</td>
                <td>
                    <select ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (CompanyType e : CompanyType.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.settleDateStart"/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.settleDateEnd"/>
                </td>
                <td class="text-right">状态</td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%
                            for (Status e : Status.values()) {
                                if (e.name().startsWith("CHK_FILE_INFO")) {
                        %>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
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
        <table class="table table-bordered table-striped table-condensed">
            <thead>
            <tr>
                <th>序号</th>
                <th>文件类型</th>
                <th>机构或商户</th>
                <th>机构或商户类型</th>
                <th>清算日期</th>
                <th>对账文件</th>
                <th>状态</th>
                <th>备注</th>
                <th>操作人</th>
                <th>操作时间</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list">
                <td>{{$index + 1}}</td>
                <td>{{bean.fileTypeDesc}}</td>
                <td>{{vm.cached.ALL_COMPANY_CODE[bean.companyCode]}}({{bean.companyCode}})</td>
                <td>{{bean.companyTypeDesc}}</td>
                <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
                <td>
                    <span ng-if="bean.fileType == '01'">
                        <span ng-if="bean.originalDfsRemoteFilename != null">
                            原始对账文件:
                            <a target="_blank"
                               href="${ctx}/base/dfsFileInfo/download?localFilename={{bean.localFilename}}&dfsFullFilename={{bean.originalDfsRemoteFilename}}&dfsGroupname={{bean.originalDfsGroupName}}">
                                {{bean.localFilename}}</a>
                            <br/>
                        </span>
                        <span ng-if="bean.dfsRemoteFilename != null">
                            解析后对账文件:
                            <a target="_blank"
                               href="${ctx}/base/dfsFileInfo/download?localFilename={{bean.settleDate | date:'yyyyMMdd'}}_{{bean.companyCode}}_parsed.txt&dfsFullFilename={{bean.dfsRemoteFilename}}&dfsGroupname={{bean.dfsGroupName}}">
                                {{bean.settleDate | date:"yyyyMMdd"}}_{{bean.companyCode}}_parsed.txt</a>
                        </span>
                    </span>
                    <span ng-if="bean.fileType == '02'">
                            <a target="_blank"
                               href="${ctx}/base/dfsFileInfo/download?localFilename={{bean.localFilename}}&dfsFullFilename={{bean.dfsRemoteFilename}}&dfsGroupname={{bean.dfsGroupName}}">
                                {{bean.localFilename}}</a>
                    </span>
                </td>
                <td>{{bean.statusDesc}}</td>
                <td>{{bean.remark}}</td>
                <td>{{bean.updatedBy}}</td>
                <td>{{bean.updatedTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
