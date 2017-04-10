<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ include file=".././common/taglibs.jsp" %>--%>
<%--<script src="${ctx}/build/js/views/institution/datadictApp-e22206a4c9.js"></script>--%>
<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    银行代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.bankCode" class="form-control input-sm"readonly="readonly">

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    借贷标记：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.cdFlag" class="form-control input-sm" readonly="readonly">

                </td>
            </tr>
            <tr>
            <td class="text-right col-xs-3">
                公私标记：
            </td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.ppFlag" class="form-control input-sm" readonly="readonly">

            </td>
        </tr>
            <tr>
                <td class="text-right col-xs-3">
                    限额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.sizeLimitFlag" class="form-control input-sm" readonly="readonly">

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    代付机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCode" class="form-control input-sm" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    优先级：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.priorityLevel" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    启用标志：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.enableFlag" class="form-control input-sm">--%>
                    <select ng-model="vm.item.enableFlag" class="form-control">
                        <option value="">请选择</option>
                        <option value="1">启用</option>
                        <option value="2">关闭</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
