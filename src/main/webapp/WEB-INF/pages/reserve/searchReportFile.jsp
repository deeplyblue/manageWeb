<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/reserve/reportFileInfoApp-b095221bfb.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金银行交易文件上传信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/report/file/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    报表类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <select ng-model="vm.queryBean.reportType" class="form-control"
                            ng-options="key as value for (key,value) in {'0':'日报','1':'周报','2':'月报'} ">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    报表日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginDate"

                            required
                           required/>
                </td><td>
                <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                       ng-model="vm.queryBean.endDate"

                        required
                       required/>
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
            <%--<button ng-click="vm.upload()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>报表类型</th>
                <th>文件名</th>
                <th>报表日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{{'0':'日报','1':'周报','2':'月报'}[bean.reportType]}}</td>
                <td>{{bean.orgFileName}}</td>
                <td>{{bean.generateDate|date:'yyyy-MM-dd'}}</td>
                <td><input type="button" value="重新生成" ng-click="vm.resetGenerateReport(bean)"/>
                    <shiro:hasPermission name="reportFile_down">
                         <input type="button" value="下载" ng-click="vm.downFile(bean)"/></td>
                </shiro:hasPermission>
            </tr>
            </tbody>

            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
