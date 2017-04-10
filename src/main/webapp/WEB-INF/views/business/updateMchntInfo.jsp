<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    商户代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntCode" class="form-control input-sm">

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    发展渠道：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.depChannelCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
            <td class="text-right col-xs-3">
                业务机构简称：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.mchntAbbrName" class="form-control input-sm">
            </td>
        </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务机构名称：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntName" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务机构分类：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntCatagory" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntKind" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntClass" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务机构性质：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntType" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务机构归属地：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.mchntArea" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    父级业务机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.parentOrgCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    是否担保交易商户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.guaranteeFlagId" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    报表类型：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.reportType" class="form-control input-sm">--%>
                    <select ng-model="vm.item.reportType" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.REPRT_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
