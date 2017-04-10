<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/6/3
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.companyCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                   联系人类型：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.cttType" class="form-control input-sm">--%>
                    <select ng-model="vm.item.cttType" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.CTT_TYPE">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    联系人角色：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.cttRole" class="form-control input-sm">--%>
                    <select  ng-model="vm.item.cttRole" class="form-control">
                        <option value="A">A角</option>
                        <option value="B">B角</option>
                        <option value="C">C角</option>
                    </select>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    是否接收公告通知邮件：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.acceptNoticeEmailFlag" class="form-control input-sm">--%>
                    <select  ng-model="vm.item.acceptNoticeEmailFlag" class="form-control">
                        <option value="1">是</option>
                        <option value="0">否</option>

                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttName" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮箱：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttEmail" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttMobile"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    座机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttPhone" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                   传真：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttFax" class="form-control input-sm">
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
