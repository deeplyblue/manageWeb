<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/16
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/ContranctApp-5d4d802725.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询合同信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/business/contaract/search">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    支付机构：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                    <select ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.COMANY_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    合同编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.contCode" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    审核状态:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}">
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    生效时间：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.contBgnDate" class="{{vm.constant.inputClass}}">--%>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.item.contBgnDate"

                            required
                           required/>
                </td>
                <td class="text-right">
                    结束时间:
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.contEndDate" class="{{vm.constant.inputClass}}">--%>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.item.contEndDate"

                            required
                           required/>
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
        <p class="btn-group">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>支付机构</th>
                <th>合同编号</th>
                <th>合同名称  </th>
                <th>审核状态 </th>
                <th>合同签订日期  </th>
                <th>合同生效日期</th>
                <th>合同结束日期  </th>
                <th>签约机构 </th>
                <th>企业代码 </th>
                <th>操作 </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td> {{vm.cached.COMANY_CODE[bean.companyCode]}}{{bean.companyCode}}</td>
                <td>{{bean.contCode}}</td>
                <td>{{bean.contName}}</td>
                <td>{{bean.auditStatus}}</td>
                <td>{{bean.contSignedDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.contBgnDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.contEndDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.signedOrgCode}}</td>
                <td>{{bean.enterpriseCode}}</td>
                <td></td>
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
