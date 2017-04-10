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

    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    银行：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankCode">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    订单类型：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.transCode">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    接入渠道：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.connChannel">

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"
                           uib-typeahead="item.key as item.value for item in vm.getCache('ORG_BANK_CODE_DESC') | filter:$viewValue | limitTo:10">
                </td>
            </tr>

            </tbody>
        </table>

    </form>
</div>
</body>
</html>
