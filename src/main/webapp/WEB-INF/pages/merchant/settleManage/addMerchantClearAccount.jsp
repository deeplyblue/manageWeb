<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>

        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.merchantCode"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:6" required name="merchantCode"/>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    账户名：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.accountName" required name="accountName"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    开户行：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.bankCode"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.ORG_BANK_CODE_DESC | filter:$viewValue | limitTo:6"
                           required name="bankCode"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    开户行行名：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.bankName" required name="bankName"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    联行号：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.cnapsCode" required name="cnapsCode"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    地区码：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.areaCode"
                           typeahead-min-length="0"   uib-typeahead="item.AREA_CODE+item.CITY_CODE as item.AREA_NAME+item.CITY_NAME for item in vm.cached.ALL_AREA | filter:$viewValue | limitTo:10"  required name="areaCode"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    账户号：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}} "  type="text" ng-model="vm.item.accountNo"  required name="accountNo"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    付款摘要：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}} "  ng-model="vm.item.paySummary"></textarea>
                </td>
            </tr>

        </table>

</div>
</body>
</html>