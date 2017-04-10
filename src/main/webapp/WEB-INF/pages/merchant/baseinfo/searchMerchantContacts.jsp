<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/baseinfo/merchantContactsApp-e9b77ae56c.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户联系人信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/contacts/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"/>--%>
                        <input type="text" ng-model="vm.queryBean.companyCode"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    联系人类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.cttType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CTT_TYPE">
                        <option value="">请选择类型</option>
                    </select>
                </td>
                <td class="text-right">
                    联系人角色：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.cttRole"
                            ng-options="key as value for (key,value) in {'A':'A角','B':'B角','C':'C角'}">
                        <option value="">请选择角色</option>
                    </select>
                </td>
            </tr>
            <tr valign="top">
                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.cttName" class="{{vm.constant.inputClass}}" />
                </td>

                <td class="text-right" >
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.cttMobile" class="{{vm.constant.inputClass}}" />
                </td>
                <td class="text-right">
                    联系人状态：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.cttStatus"
                            ng-options="key as value for (key,value) in {'0':'可用','1':'不可用'}">
                        <option value="">请选择状态</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6" class="textCenter">
<%--<shiro:hasPermission name="merchant-contacts_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
<%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
<shiro:hasPermission name="merchant-contacts_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
    </shiro:hasPermission>
<shiro:hasPermission name="merchant-contacts_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
    </shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>商户</th>
                <th>联系人类型</th>
                <th>联系人角色</th>
                <th>姓名</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>座机号</th>
                <th>传真</th>
                <th>联系人状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list ">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.companyCode}}[{{vm.cached.MERCHANT_CODE[bean.companyCode]}}]</td>
                <td>{{vm.cached.CTT_TYPE[bean.cttType]}}</td>
                <td>{{{'A':'A角','B':'B角','C':'C角'}[bean.cttRole]}}</td>
                <td>{{bean.cttName}}</td>
                <td>{{bean.cttEmail}}</td>
                <td>{{bean.cttMobile}}</td>
                <td>{{bean.cttPhone}}</td>
                <td>{{bean.cttFax}}</td>
                <td>{{{'0':'可用','1':'不可用'}[bean.cttStatus]}}</td>
                <td>
<shiro:hasPermission name="merchant-contacts_open">
                    <button type="button" ng-click="vm.updateItemEnableFlag(bean,bean.cttStatus)">{{{'1':'可用','0':'不可用'}[bean.cttStatus]}}</button></td>
          </shiro:hasPermission>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
