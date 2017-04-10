<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
  desc:添加支付策略2
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>

    <form name="itemForm" novalidate w5c-form-validate autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    银行：
                </td>
                <td colspan="4">
                    <input type="text" ng-model="vm.item.bankCode" typeahead-min-length="0" ng-change="vm.queryOrgs()"
                           uib-typeahead="item.key as item.value for item in vm.cached.ORG_BANK_CODE_DESC | filter:$viewValue | limitTo:6"
                           class="{{vm.constant.inputClass}}" ng-disabled="vm.disable">
                </td>
            </tr>
            <%--<tr>
                <td class="text-right">
                    银行类型：
                </td>
                <td colspan="4">
                    <select type="text" ng-model="vm.item.bankType" typeahead-min-length="0" ng-change="vm.queryOrgs()"
                            ng-options="item.key as item.value for item in vm.cached.bankType"
                            class="{{vm.constant.inputClass}}" ng-disabled="vm.disable">
                    </select>
                </td>
            </tr>--%>
            <tr>
                <td class="text-right">
                    订单类型：
                </td>
                <td colspan="4">
                    <select type="text" ng-model="vm.item.transCode" ng-change="vm.queryOrgs()"
                            ng-options="item.key as item.value for item in vm.cached.PAY_TRANS_CODE"
                            class="{{vm.constant.inputClass}}" ng-disabled="vm.disable">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    接入渠道：
                </td>
                <td colspan="4">
                    <select type="text" ng-model="vm.item.connChannel" ng-change="vm.queryOrgs()"
                            ng-options="item.key as item.value for item in vm.cached.CONN_CHANNEL"
                            class="{{vm.constant.inputClass}}" ng-disabled="vm.disable">
                    </select>

                </td>
            </tr>

            </tbody>
            <tbody ng-show="vm.item.bankCode && vm.item.transCode && vm.item.connChannel">
            <tr>
                <td></td>
                <td>机构代码</td>
                <td>优先级(0表示优先级最高)</td>
                <td>启用标记</td>
                <td>操作</td>
            </tr>
            <tr ng-repeat="bean in vm.item.orgs track by $index">
                <td>{{vm.orgBanks[bean.orgCode]}}</td>
                <td ng-show="false">
                    <input ng-model="bean.bankCode" ng-init="vm.item.bankCode">
                    <input ng-model="bean.transCode" ng-init="vm.item.transCode">
                    <input ng-model="bean.connChannel" ng-init="vm.item.connChannel">
                </td>
                <td>
                    <input ng-model="bean.orgCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.orgBanks | filter:$viewValue | limitTo:5"
                           typeahead-min-length="0">
                </td>
                <td><input ng-model="bean.stgyPriority" class="{{vm.constant.inputClass}}"></td>
                <td><i class="glyphicon glyphicon-remove" ng-if="bean.enableFlag == '0'" style="color: red"></i>
                    <select ng-model="bean.enableFlag" class="{{vm.constant.inputClass}}">
                        <option value="0">关闭</option>
                        <option value="1">开启</option>
                    </select>
                </td>
                <td>
                    <button ng-click="vm.addItem()">增加</button>
                    <button ng-click="vm.deleteItem($index)">删除</button>
                </td>
            </tr>
            </tbody>
        </table>

    </form>
</div>
</body>
</html>
