<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: shulw
  Date: 2016/12/22
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/report/reserveReport.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询备付金银行报表文件上传信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/reserveReport/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    报表类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reportType" class="{{vm.constant.inputClass}}"
                            ng-options="item.reportType as item.itemDesc for item in vm.cached.RESERVE_REPORT_TYPE_LIST ">
                        <option>请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    报表解析状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reportStatus" class="{{vm.constant.inputClass}}"
                            ng-options="status.value as status.name for status in reserveReportStatus"></select>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    报表年份：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy"
                           ng-model="vm.queryBean.reportYear"
                           required/>
                </td>
                <td class="text-right">
                    报表季度：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reportQuarter" class="{{vm.constant.inputClass}}"
                            ng-options="status.value as status.name for status in reserveReportQuarter">
                    </select>
                </td>

            </tr>



            <tr>
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>

        <p class="btn-group">
            <shiro:hasPermission name="reserveReport_upload">
                <button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
        </p>

        <table table-detail>
            <thead>
            <tr>

                <th>报表类型</th>
                <th>报表年份</th>
                <th>报表季度</th>
                <th>上传文件名称</th>
                <th>上传时间</th>
                <th>文件状态</th>
                <th>上传描述</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">

                <td>{{vm.cached.RESERVE_REPORT_TYPE[bean.reportType]}} </td>
                <td>{{bean.reportYear}}</td>
                <td>{{reserveReportQuarter[bean.reportQuarter].name }}</td>
                <td>{{bean.localFileName}}</td>
                <td>{{bean.createAt|date:yyyy-MM-dd}}</td>
                <td>{{reserveReportStatus[bean.reportStatus].name }} </td>
                <td>{{bean.remarkDesc}}</td>
            </tr>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
