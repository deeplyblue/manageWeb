<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <input type="text" ng-model="vm.item.companyType" ng-init="vm.item.companyType = '03'" ng-show="false" />
            <tr>
                <td class="text-right col-xs-3">
                    业务机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCode" class="form-control input-sm"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:10">
                    <%--<select ng-model="vm.item.orgCode" class="form-control"--%>
                            <%--ng-options="item.key as item.value for item in vm.cached.CONN_CHANNEL">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                      结算周期：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.clrCycle" class="form-control input-sm">--%>

                    <select ng-model="vm.item.clrCycle" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.CLR_CYCLE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
                <tr ng-if="vm.item.clrCycle == '06' || vm.item.clrCycle == '05'">
                <td class="text-right">
                     结算点：
                </td>
                <td>
                    <select ng-model="vm.item.clrPoint" class="form-control input-sm"
                            ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算范围：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.clrRange" class="form-control input-sm">--%>
                    <select ng-model="vm.item.clrRange" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.CLR_RANGE">
                        <option value="">请选择</option>
                    </select>
                </td>
                </tr>
            <tr>
                <td class="text-right">
                    结算类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.clrMethod"class="form-control input-sm" />--%>
                    <select ng-model="vm.item.clrType" class="form-control"
                            ng-options="key as value for (key,value) in {'01':'本金+手续费结算','02':'本金结算','03':'手续费结算'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    报表类型：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.clrMethod"class="form-control input-sm" />--%>
                    <select ng-model="vm.item.reportType" class="form-control" ng-init="vm.item.reportType = '01'"
                            ng-options="key as value for (key,value) in {'01':'正常','02':'积分','03':'账单支付'}">
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
