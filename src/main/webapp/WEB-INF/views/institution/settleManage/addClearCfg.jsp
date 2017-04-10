<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">
            <input type="text" ng-model="vm.item.companyType" ng-init="vm.item.companyType = '<%=CompanyType.PAY.getCode()%>'" ng-show="false" />
            <tr>
                <td class="text-right col-xs-3">
                    机构代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" name="companyCode" ng-model="vm.item.companyCode"
                           class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10"
                           required>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算周期：
                </td>
                <td>
                    <select name="clrCycle" ng-model="vm.item.clrCycle" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_CYCLE" required>
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr ng-if="vm.item.clrCycle != '01' && vm.item.clrCycle != null">
                <td class="text-right">
                    结算点：
                </td>
                <td>
                    <select ng-if="vm.item.clrCycle=='03'|| vm.item.clrCycle=='04'" ng-model="vm.item.clrPoint" class="{{vm.constant.inputClass}}"
                            ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT" >
                        <option value="">请选择</option>
                    </select>
                    <select ng-if="vm.item.clrCycle=='02'|| vm.item.clrCycle=='05'" ng-model="vm.item.clrPoint" class="{{vm.constant.inputClass}}"
                            ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT_WEEK" >
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算范围：
                </td>
                <td>
                    <select name="clrRange" ng-model="vm.item.clrRange" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.CLR_RANGE" required>
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算类型：
                </td>
                <td>
                    <select name="clrType" ng-model="vm.item.clrType" class="{{vm.constant.inputClass}}"
                            ng-init="vm.item.clrType = '01'"
                            ng-options="key as value for (key,value) in vm.cached.CLR_TYPE"
                            required>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    报表类型：
                </td>
                <td>
                    <select ng-model="vm.item.reportType" class="{{vm.constant.inputClass}}"
                            ng-init="vm.item.reportType = '01'"
                            ng-options="key as value for (key,value) in vm.cached.REPORT_TYPE">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手续费收取方式：
                </td>
                <td>
                    <select ng-model="vm.item.feeClearType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.FEE_CLEAR_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
        </table>

</div>
</body>
</html>
