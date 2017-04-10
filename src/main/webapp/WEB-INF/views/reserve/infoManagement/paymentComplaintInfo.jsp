<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2016/12/22
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/reserveMsgPaymentComplaintInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="支付投诉信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/paymentComplaintInfo/search" class="form-inline"
          autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    投诉类型：
                </td>
                <td>
                    <%--<input ng-model="vm.queryBean.accuseType" class="{{vm.constant.inputClass}}">--%>
                    <%--</input>--%>
                    <select ng-model="vm.queryBean.accuseType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.COMPLAINT_TYPE_FOR_RESERVE">
                        <option value="">请选择类型</option>
                    </select>
                </td>

                <td class="text-right">
                    处理状态：
                </td>
                <td>
                    <%--<input ng-model="vm.queryBean.accuseType" class="{{vm.constant.inputClass}}">--%>
                    <%--</input>--%>
                    <select ng-model="vm.queryBean.handleStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.HANDLE_STATUS_FOR_RESERVE">
                        <option value="">请选择类型</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    投诉时间：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.beginTime"
                           required/>
                    ——
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endTime"
                           required/>

                </td>
                <td class="text-right">
                    是否删除：
                </td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'0':'否','1':'是'}">
                        <option value="">请选择</option>
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
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>投诉类型</th>
                <th>投诉内容</th>
                <th>投诉人</th>
                <th>投诉电话</th>
                <th>投诉传真</th>
                <th>邮编</th>
                <th>投诉时间</th>
                <th>是否删除</th>
                <th>处理状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>{{$index+1}}</td>
                <td>{{vm.cached.COMPLAINT_TYPE_FOR_RESERVE[bean.accuseType]}}</td>
                <td>{{bean.accuseInfo}}</td>
                <td>{{bean.accuse}}</td>
                <td>{{bean.complainTel}}</td>
                <td>{{bean.accuseFax}}</td>
                <td>{{bean.zipCode}}</td>
                <td>{{bean.accuseDate | date:'yyyy-MM-dd'}}</td>
                <td>{{{'0':'否','1':'是'}[bean.deleteFlag]}}</td>
                <td ng-if=" bean.handleStatus == '00'">
                    未处理
                </td>
                <td ng-if=" bean.handleStatus != '00'">
                    {{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.handleStatus]}}
                </td>
                <td ng-if=" bean.handleStatus == '00'">
                    <button type="button" ng-click="vm.handle(bean)">处理</button>
                </td>
                <td ng-if=" bean.handleStatus == '03'|| bean.handleStatus == '04'|| bean.handleStatus == '07' ">
                    <button type="button" ng-click="vm.handle(bean)">处理</button>
                    <button type="button" ng-click="vm.detailShow(bean)">查看</button>
                </td>
                <td ng-if=" bean.handleStatus == '01' || bean.handleStatus == '02' || bean.handleStatus == '05'|| bean.handleStatus == '06'|| bean.handleStatus == '08' ">
                    <button type="button" ng-click="vm.detailShow(bean)">查看</button>
                </td>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
