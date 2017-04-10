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
    <script src="${ctx}/build/js/views/reserve/bankReportUploadInfo-485043f945.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询备付金银行报表上传信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/report/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账户：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.accountNo as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10" />
                </td>
                <td>
                    上传状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}"
                    ng-options="key as value for (key,value) in {'0':'请求中','1':'成功','2':'失败','3':'已经重传'} ">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    报表日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.begin"
                           required/>
                </td><td>
                <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                       ng-model="vm.queryBean.end"
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
                <th>银行账户</th>
                <th>银行名称</th>
                <th>报表日期</th>
                <th>上传状态</th>
                <th>响应信息</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.accountNo}}</td>
                <td>{{bean.bankName}}</td>
                <td>{{bean.reportDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.status}}</td>
                <td>{{bean.desc}}</td>
                <td><input type="button" value="查询勾稽状态"/></td>
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
