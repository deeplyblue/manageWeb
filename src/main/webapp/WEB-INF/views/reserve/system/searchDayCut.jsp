<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/20
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../.././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/system/dayCut.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询日切信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/dayCut/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    工作日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.currWorkDate"/>
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
        <table table-detail>
            <thead>
            <tr>
                <th>前一工作日期</th>
                <th>当前工作日期</th>
                <th>下一工作日期</th>
                <th>是否节假日</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
                <td ng-if="bean.deleteFlag == 0" style="color: red">{{bean.preWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag == 0" style="color: red">{{bean.currWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag == 0" style="color: red">{{bean.nextWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag == 0" style="color: red">{{{'01':'非节假日','02':'节假日'}[bean.isHoliday]}}</td>
                <td ng-if="bean.deleteFlag !=0">{{bean.preWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag !=0">{{bean.currWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag !=0">{{bean.nextWorkDate | date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.deleteFlag !=0">{{{'01':'非节假日','02':'节假日'}[bean.isHoliday]}}</td>
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
