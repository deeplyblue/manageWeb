<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>

    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                   选择银行：
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    订单类型：
                </td>
                <td >
                    <select ng-model="vm.item.transCode" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.TRANS_CODE_02" >
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr >
            <td class="text-right">
                接入渠道：
            </td>
            <td>
                <select ng-model="vm.item.connChannel" class="form-control"
                        ng-options="item.key as item.value for item in vm.cached.CONN_CHANNEL" >
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('ORG_BANK_CODE_DESC') | filter:$viewValue | limitTo:10"
                           >
                </td>
            </tr>

       </tbody>
        </table>

    </form>
</div>
</body>
</html>
