<%@ page import="com.oriental.reserve.enums.OrgType" %>
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
                    <input type="text" ng-model="vm.item.accountNo" class="{{vm.constant.inputClass}}" />
                    <input type="text" ng-model="vm.item.id" ng-hide="true" class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    机构号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCode"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    机构类型：
                </td>
                <td class="col-xs-4">
                    <select ng-model="vm.item.orgType" class="{{vm.constant.inputClass}}">
                        <%for (OrgType e : OrgType.values()) {%>
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
                    结算类型：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.clearType"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    备注信息：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.remarkDesc"  class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
