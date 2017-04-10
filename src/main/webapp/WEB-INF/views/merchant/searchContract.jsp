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
    <script src="${ctx}/js/views/merchant/ContranctApp.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询合同信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/contaract/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    机构代码：
                </td>
                <td>
                        <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">

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
                    <input type="text" ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('AUDIT_STATUS') | filter:$viewValue | limitTo:10">
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

            <shiro:hasPermission name="org-contaract_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="org-contaract_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="org-contaract_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>

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
                <td>{{vm.cached.AUDIT_STATUS[bean.auditStatus]}}</td>
                <td>{{bean.contSignedDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.contBgnDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.contEndDate|date:'yyyyMMdd'}}</td>
                <td>{{bean.signedOrgCode}}</td>
                <td>{{bean.enterpriseCode}}</td>
                <td>
                    <shiro:hasPermission name="org-contaract_audit">
                        <button ng-if="bean.auditStatus=='01'" type="button" ng-click="vm.updateItemEnableFlag(bean,'02')">审核成功</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_fail">
                        <button ng-if="bean.auditStatus=='01'" type="button" ng-click="vm.updateItemEnableFlag(bean,'03')">审核失败</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_on">
                        <button ng-if="bean.auditStatus=='02'" type="button" ng-click="vm.updateItemEnableFlag(bean,'05')">上架</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_on">
                        <button ng-if="bean.auditStatus=='04'" type="button" ng-click="vm.updateItemEnableFlag(bean,'05')">上架</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_off">
                        <button ng-if="bean.auditStatus=='02'" type="button" ng-click="vm.updateItemEnableFlag(bean,'04')">下架</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_off">
                        <button ng-if="bean.auditStatus=='05'" type="button" ng-click="vm.updateItemEnableFlag(bean,'04')">下架</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="org-contaract_down">
                        <button type="button" ng-click="vm.downEnclosure(bean)">下载全部附件</button>
                    </shiro:hasPermission>
                </td>
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
