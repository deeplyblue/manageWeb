<%@ page import="com.oriental.reserve.enums.MessageStatus" %>
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
    <script src="${ctx}/js/views/reserve/reserveBatchInformation.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询批次报备 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/batch/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    报送类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}">--%>
                    <select class="{{vm.constant.inputClass}}" ng-change="vm.detailType()" ng-model="vm.temp.messageType"
                            ng-options="key as value for (key,value) in vm.cached.TYPE_FOR_RESERVE">
                        <option value="">请选择</option>
                    </select>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.messageType"
                            ng-options="key as value for (key,value) in vm.temp.DETAIL_TYPE">
                        <option value="">请选择</option>
                    </select>
                <td class="text-right">
                    报送时间：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.queryStartDate"
                           required/>
                    ——
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.queryEndDate"
                           required/>

                </td>
                <td class="text-right">
                    消息状态：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.messageStatus">
                        <option value="">请选择</option>
                        <%for (MessageStatus messageStatus : MessageStatus.values()) {%>
                        <option value="<%=messageStatus.getCode()%>"><%=messageStatus.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    账户号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <input type="text" ng-model="vm.queryBean.accountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.accountNo as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    商户号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <input type="text" ng-model="vm.queryBean.merchantCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.accountNo as item.desc for item in vm.cached.RESERVE_LIST | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    批次号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.accountNo" class="form-control" />--%>
                    <input type="text" ng-model="vm.queryBean.pkgNo" class="{{vm.constant.inputClass}}"/>

                </td>
            </tr>
            <tr class="text-center">
                <td colspan="6" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <div class="text-left">
            <button type="button" ng-click="vm.audit('<%=MessageStatus.PASS.getCode()%>')">审核通过</button>
            <button type="button" ng-click="vm.audit('<%=MessageStatus.REJECT.getCode()%>')">审核拒绝</button>
            <%--<button type="button" ng-click="vm.downloadCheckFile()">下载</button>--%>
        </div>
        <table table-detail>
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list"></th>
                <%--<th>机构号</th>--%>
                <th>报送类型</th>
                <th>工作日期</th>
                <th>消息状态</th>
                <th>批次号</th>
                <th>删除标识</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked"
                           ng-if="<%=MessageStatus.INIT.getCode()%> == bean.messageStatus">
                    <input name="check" type="checkbox" ng-model="bean._checked" disabled="disabled"
                           ng-if="<%=MessageStatus.INIT.getCode()%> != bean.messageStatus">
                </td>
                <%--<td>{{bean.orgNo}}</td>--%>
                <td>{{vm.cached.MESSAGE_TYPE_FOR_RESERVE[bean.messageType]}}[{{bean.messageType}}]</td>
                <td>{{bean.workDate|date:'yyyy-MM-dd'}}</td>
                <td>{{{
                    <%for (MessageStatus e : MessageStatus.values()) {%>
                    '<%=e.getCode()%>':'<%=e.getDesc()%>',
                    <%}%>
                    }[bean.messageStatus]}}
                </td>
                <td>{{bean.pkgNo}}</td>
                <td>{{{'0':'未删除','1':'已删除'}[bean.deleteFlag]}}</td>
                <td>
                    <button type="button" ng-click="vm.queryInformationDetail(bean)">查看明细</button>
                    <button type="button" ng-if="<%=MessageStatus.PASS.getCode()%> == bean.messageStatus"
                            ng-click="vm.reSend(bean)">重发
                    </button>
                    <button type="button" ng-if="<%=MessageStatus.REJECT.getCode()%> == bean.messageStatus ||
                    <%=MessageStatus.INIT.getCode()%> == bean.messageStatus" ng-click="vm.reGenerate(bean)">重新生成
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
        <br/>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
