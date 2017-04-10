<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    备付金账户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.accountNo" readonly class="{{vm.constant.inputClass}} ">
                    <input type="text" ng-model="vm.item.id" ng-hide="true" readonly class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易日期：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text"  ng-model="vm.item.transDate" readonly class="{{vm.constant.inputClass}} ">--%>
                    {{vm.item.transDate|date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    金额：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.amt" readonly class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易类型：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text"  ng-model="vm.item.transType" class="{{vm.constant.inputClass}} ">--%>
                    <select ng-model="vm.item.transType" class="{{vm.constant.inputClass}} "
                    ng-options="key as value for (key,value) in vm.cached.TRANS_TYPE"></select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
