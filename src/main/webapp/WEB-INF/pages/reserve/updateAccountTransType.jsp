<%@ page import="com.oriental.reserve.enums.ExtTransType" %><%--
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
                    <input type="text" ng-model="vm.item.accountNo" readonly class="{{vm.constant.inputClass}}"/>
                    <input type="text" ng-model="vm.item.id" ng-hide="true">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易类型：
                </td>
                <td class="col-xs-4">
                    <select ng-model="vm.item.transType" class="{{vm.constant.inputClass}}">
                        <%for (ExtTransType e : ExtTransType.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    对方账号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.otherAccountNo"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    对方账户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.otherAccountName"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    银行摘要：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.summary"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    银行备注：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.bankRemark"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    备注：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.remark"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
