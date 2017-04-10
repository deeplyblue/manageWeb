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
    <script src="${ctx}/js/views/institution/organizeInfoApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询支付机构信息"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/payment/search" autocomplete="off">
        <table table-form>
            <tr align="center">

                <td>支付机构
                        <input ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                               uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                        </input>
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
<shiro:hasPermission name="organize-info_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="organize-info_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="organize-info_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
</shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>机构代码</th>
                <th>机构种类</th>
                <th>机构类型</th>
                <th>机构全称</th>
                <th>机构简称</th>
                <th>通道类型</th>
                <th>内外部类型</th>
                <th>当前状态</th>
                <th>操作</th>

            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list ">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.orgCode)">
                </td>
                <td>{{bean.orgCode}}</td>
                <td>{{vm.cached.ORG_CATEGORY[bean.orgCategory]}}</td>
                <td>{{vm.cached.ORG_TYPR[bean.orgType]}}</td>
                <td>{{bean.orgName}}</td>
                <td>{{bean.orgAbbrName}}</td>
                <td>{{bean.channelType}}</td>
                <td>{{bean.orgInOrOutFlag}}</td>
                <td>{{vm.cached.ORG_STATUS[bean.orgStatus]}}</td>
                <td ng-if="bean.orgStatus=='01'">
                    <shiro:hasPermission name="organize-info_openSuccess">
                     <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='02')">审核成功</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="organize-info_openErro">
                        <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='05')">审核失败</button>
                 </shiro:hasPermission>
                </td>
                <td ng-if="bean.orgStatus=='02'">
                 <shiro:hasPermission name="organize-info_locking">
                    <button type="button"    ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='03')">锁定</button>
                  </shiro:hasPermission>
                 <shiro:hasPermission name="organize-info_cancel">
                    <button type="button"    ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='04')">注销</button>
                 </shiro:hasPermission>
<shiro:hasPermission name="organize-info_disable">
                    <button type="button"     ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='05')">停用</button>
    </shiro:hasPermission>
                </td>
                <td ng-if="bean.orgStatus=='03'">
<shiro:hasPermission name="organize-info_open">
                    <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='02')">开启</button>
</shiro:hasPermission>
                </td>
                <td ng-if="bean.orgStatus=='04'">
<shiro:hasPermission name="organize-info_open">
                    <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='02')">开启</button>
</shiro:hasPermission>
                </td>
                <td ng-if="bean.orgStatus=='05'">
<shiro:hasPermission name="organize-info_open">
                    <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,bean.orgStatus='02')">开启</button>
</shiro:hasPermission>
                </td>

            </tr>
            </tbody>

        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
 <core:import url="../common/nav.jsp"/>
</body>
</html>
