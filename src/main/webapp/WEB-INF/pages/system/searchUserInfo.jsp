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
    <script src="${ctx}/build/js/views/system/userInfoApp-71d7587439.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="用户管理"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/system/user/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    用户类型
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.userType" class="{{vm.constant.inputClass}}"
                           ng-options="lable for (key,value) in vm.getCache('userType')">--%>
                    <select ng-model="vm.queryBean.userType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.USER_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    用户账号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.userName" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           typeahead-min-length="0"
                           uib-typeahead="item.userName for item in vm.getObj('ALL_USER',$viewValue)">
                </td>
                <td class="text-right">
                    商户代码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    当前状态
                </td>
                <td>
                    <select ng-model="vm.queryBean.userStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.USER_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    用户姓名
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.userFullName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    手机号码
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.userMobile" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    所属部门
                </td>
                <td>
                    <select type="text" ng-model="vm.queryBean.deptCode" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.DPET_NAME">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td class="text-right">
                    用户角色
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.roleId" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('ROLE_INFO') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">

                </td>
                <td>

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
        <shiro:hasPermission name="sys-user-info_toAddUser">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys-user-info_doUpdateUser">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys-user-info_doDeleteUser">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
        </shiro:hasPermission>

        <shiro:hasPermission name="sys-user-info_toAudit">
            <button ng-click="vm.auditItem('02')">审核</button>
        </shiro:hasPermission>
            <shiro:hasPermission name="user-info_fixChanStatus_close">
                <button ng-click="vm.auditItem('05')">停用</button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>用户类型</th>
                <th>用户账号</th>
                <th>用户姓名</th>
                <th>状态</th>
                <th>用户角色</th>
                <th>商户</th>
                <th>所属部门</th>
                <th>手机</th>
                <th>上次登录时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{vm.cached.USER_TYPE[bean.userType]}}</td>
                <td>{{bean.userName}}</td>
                <td>{{bean.userFullName}}</td>
                <td>{{vm.cached.USER_STATUS[bean.userStatus]}}</td>
                <td>
                    <span ng-repeat="userRole in bean.userRoles track by $index">
                        <span ng-if="$index != 0">|</span>
                    {{vm.cached.ROLE_INFO[userRole.roleId]}}
                    </span>
                </td>
                <td>{{vm.cached.MERCHANT_CODE[bean.companyCode]}}({{bean.companyCode}})</td>
                <td>{{vm.cached.DPET_NAME[bean.deptCode]}}</td>
                <td>{{bean.userMobile}}</td>
                <td>{{bean.lastLoginTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>
                    <shiro:hasPermission name="user-info_toUserRole">
                        <button ng-click="vm.roleAllocate(bean)">角色分配</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user-info_toResetUserPwd">
                    <button ng-click="vm.resetPwd(bean)">密码重置</button>
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
