
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
    <script src="${ctx}/build/js/views/opcif/customerInfoApp-67690e85c6.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="客户信息查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <from id="queryForm" name="queryForm" action="${ctx}/cif/customerInfo/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    登录号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.loginNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    操作员编号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.operatorNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    证件号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.certificateNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    客户号:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.customerNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    手机:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.mobile" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    联系邮箱:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.email" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作员状态:
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.queryBean.operatorStatus"
                            ng-options="key as value for (key,value) in {'FREEZE':'冻结','NORMAL':'正常','CLOSE':'销户'}">
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
    </from>

    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>登录号</th>
                <th>用户编号</th>
                <th>证件类型</th>
                <th>证件号</th>
                <th>简称</th>
                <th>客户号</th>
                <th>客户名称</th>
                <%--<th>客户角色</th>--%>
                <th>客户类型</th>
                <th>手机</th>
                <th>邮箱</th>
                <th>实名级别</th>
                <th>用户状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="bean in vm.pagination.list track by $index">
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.loginNo}}</td>
                <td>{{bean.operatorNo}}</td>
                <td>{{vm.cached.OPCIF_CERTIFICATE_TYPE[bean.certificateType]}}</td>
                <td>{{bean.certificateNo}}</td>
                <td>{{bean.shortName}}</td>
                <td>{{bean.customerNo}}</td>
                <td>{{bean.customerName}}</td>
                <%--<td>{{bean.customerGrade}}</td>--%>
                <td>{{vm.cached.ACCOUNT_TYPE[bean.customerType]}}</td>
                <td>{{bean.mobile}}</td>
                <td>{{bean.email}}</td>
                <td>{{vm.cached.REALNAME_LV[bean.realnameLv]}}</td>
                <td>{{{'FREEZE':'冻结','NORMAL':'正常','CLOSE':'销户','UNFREEZE':'解冻中'}[bean.operatorStatus]}}</td>
                <td>
                <shiro:hasPermission name="opcif-customer_froze">
                    <button ng-if="bean.operatorStatus=='NORMAL'" ng-click="vm.freezeOperator(bean)">冻结</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="opcif-customer_lock">
                    <button ng-if="bean.operatorStatus=='FREEZE'" ng-click="vm.defrosting(bean)">申请解冻</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="opcif-customer_unFreeze">
                        <button ng-if="bean.operatorStatus=='UNFREEZE'" ng-click="vm.unFreezeOperator(bean)">确认解冻</button>
                </shiro:hasPermission>
               <button  ng-click="vm.queryCustomerDetail(bean)">查看</button>
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
