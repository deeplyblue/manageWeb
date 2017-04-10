<%@ page import="com.oriental.check.commons.enums.CompanyType" %>
<%@ page import="com.oriental.check.commons.enums.Status" %>
<%@ page import="com.oriental.manage.core.utils.SessionUtils" %>
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
    <script src="${ctx}/build/js/views/mchntplatform/mchntChkFileInfoApp-841e37a82f.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="商户对账文件信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/mchntplatform/chkFileInfo/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">商户代码</td>
                <td>
                    {{vm.cached.ALL_COMPANY_CODE["<%=SessionUtils.getMchntCode()%>"]}}({{"<%=SessionUtils.getMchntCode()%>"}})

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
                <th>清算日期</th>
                <th>对账文件</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list">
                <td>{{$index + 1}}</td>
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
                <td>{{bean.remark}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>

