
<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/8/24
  Time: 20:26
--%>
<%--
  @TODO 参考接口文档，查询展示；预留状态修改按钮【冻结】【锁定】，先不做实现
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/riskfront/riskfrontMessageList-c43046b5f4.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="风险消息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <from id="queryForm" name="queryForm" action="${ctx}/riskfront/messageList/search" autocomplete="off" role="form">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                     类型:
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.dataType"
                            ng-options="key as value for (key,value) in  vm.cached.RISKFRONT_DATA_TYPE">
                        <option value="">请选择</option>
                    </select> </td>

                <td class="text-right">
                    消息类型:
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.txCode"
                            ng-options="key as value for (key,value) in  vm.cached.RISKFRONT_TX_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>

                <td class="text-right">
                    解除状态:
                </td>
                <td>

                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.isRemove"
                            ng-options="key as value for (key,value) in {'Y':'解除','N':'未解除'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    订单号/流水号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.data" class="{{vm.constant.inputClass}}">
                </td>

                <td class="text-right">
                    开始时间:
                </td>
                <td >
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.startDate" required />
                </td>
                <td class="text-right">
                    结束时间:
                </td>
                <td >
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"  required/>
                </td>


            </tr>


            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </from>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>机构号</th>
                <th>消息类型</th>
                <th>报文流水号</th>
                <th>消息处理状态</th>
                <th>业务处理状态</th>
                <th>业务申请编号</th>
                <th>业务类型</th>
                <th>案件编号</th>
                <th>案件类型</th>
                <th>支付机构编号</th>
                <th>操作</th>


            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.messageFrom}}</td>
                <td>{{vm.cached.RISKFRONT_TX_CODE[bean.txCode]}}</td>
                <td>{{bean.transSerialNumber}}</td>
                <td>{{vm.cached.RISKFRONT_STATUS[bean.status]}}</td>
                <td>{{vm.cached.RISKFRONT_DEAL_STATUS[bean.dealStatus]}}</td>
                <td>{{bean.applicationID}}</td>
                <td>{{vm.cached.RISKFRONT_TYPE[bean.applicationType]}}</td>
                <td>{{bean.caseNumber }}</td>
                <td>{{vm.cached.RISKFRONT_CASETYPE[bean.caseType]}}</td>
                <td>{{bean.onlinePayCompanyID}}</td>
                <td>
<shiro:hasPermission name="riskfront-messageList_detail">
                    <button  ng-click="vm.queryCustomerDetail(bean)">查看明细</button>
</shiro:hasPermission>
<shiro:hasPermission name="riskfront-messageList_log">
                    <button  ng-click="vm.queryMessageLog(bean)">处理日志</button>
</shiro:hasPermission>
<shiro:hasPermission name="riskfront-messageList_message">
                    <button  ng-click="vm.toMessage(bean)">结果报文</button>
</shiro:hasPermission>
<shiro:hasPermission name="riskfront-messageList_down">
                    <button type="button" ng-click="vm.downEnclosure(bean)">下载附件</button>
    </shiro:hasPermission>

                </td>
            </tr>
            </tbody>
        </table>


        <core:import url="../common/pageFoot.jsp"/>
    </div>
    <core:import url="../common/nav.jsp"/>
</div>
</body>

</html>
