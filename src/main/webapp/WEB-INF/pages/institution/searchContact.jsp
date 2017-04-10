<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/27
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/contactInfoApp-661479715b.js"></script>

</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询支付机构联系人"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/contact/search" autocomplete="off" >
        <table table-form>
            <tr>
                <td class="text-right">
                    支付机构
                </td>
                <td>
                    <input ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                    </input>
                </td>
                <td class="text-right">
                    联系人类型
                </td>
                <td>
                    <select ng-model="vm.queryBean.cttType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CTT_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    联系人角色
                </td>
                <td>
                    <select  ng-model="vm.queryBean.cttRole" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <option value="A">A角</option>
                        <option value="B">B角</option>
                        <option value="C">C角</option>

                    </select>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    姓名
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.cttName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                   手机号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.cttMobile" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    联系人状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.cttStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CTT_STATUS">
                        <option value="">请选择</option>
                    </select>
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
<shiro:hasPermission name="institution-contact_add">
    <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="institution-contact_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="institution-contact_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>支付机构</th>
                <th>联系人类型</th>
                <th>联系人角色</th>
                <th>姓名</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>传真</th>
                <th>联系人状态</th>


            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>
                    {{vm.cached.COMANY_CODE[bean.companyCode] }}
                    ({{bean.companyCode}})
                </td>
                <td>{{vm.cached.CTT_TYPE[bean.cttType]}}</td>
                <td>{{bean.cttRole}}角</td>
                <td>{{bean.cttName}}</td>
                <td>{{bean.cttEmail}}</td>
                <td>{{bean.cttMobile}}</td>
                <td>{{bean.cttFax}}</td>
                <td>{{vm.cached.CTT_STATUS[bean.cttStatus]}}</td>


            </tr>
            </tbody>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
