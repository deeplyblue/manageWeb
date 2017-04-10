<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2016/12/20
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/reserveMsgApplyInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="信息管理类数据下发申请 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/applyInfo/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    申请报文类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.applyMsgType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.MESSAGE_APPLY_TYPE_FOR_RESERVE">
                        <option value="">请选择类型</option>
                    </select>
                </td>
                <td class="text-right">
                    申请时间：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="form-control" uib-datepicker-popup=""
                           ng-model="vm.queryBean.beginTime"
                           required/>
                    —
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endTime"
                           required/>

                </td>
            </tr>
            <tr>
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <button type="button" ng-click="vm.applyForm()" class="btn btn-default">申请</button>
                </td>
            </tr>
        </table>
    </form>

    <div>

        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>批次号</th>
                <th>申请类型</th>
                <th>申请时间</th>
                <th>申请状态</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{$index+1}}</td>
                <td>{{bean.pkgNo}}</td>
                <td>{{vm.cached.MESSAGE_APPLY_TYPE_FOR_RESERVE[bean.applyMsgType]}}</td>
                <td>{{bean.createTime| date:'yyyy-MM-dd'}}</td>
                <td ng-if="bean.applyStatus != '09'">{{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.applyStatus]}}</td>
                <td ng-if="bean.applyStatus == '09'">人行审批成功</td>
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
