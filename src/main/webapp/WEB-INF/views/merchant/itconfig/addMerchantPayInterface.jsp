<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>
    <%--<form name="payInterfaceAdd" w5c-form-validate novalidate>--%>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    商户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.merchantCode" class="{{vm.constant.inputClass}}"
                           name="merchantCode" required
                           uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:6"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    业务内容：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.busRemark"></textarea>
                </td>
            </tr>
            <%--陆鹏飞建议删除没什么用--%>
            <%--<tr>--%>
            <%--<td class="text-right">--%>
            <%--消费方式：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<div>--%>
            <%--<span ng-repeat="tag in vm.cached.CONSUMPTION_PATTERNS track by $index">--%>
            <%--<input type="checkbox" id={{tag.key}} ng-model="vm.item.consumptionPatterns{{tag.key}}" />{{tag.value}}--%>
            <%--</span>--%>
            <%--</div>--%>
            <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <td class="text-right">
                    商户webService回调地址：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.merchantWsUrl"></textarea>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户IP：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.merchantIp"></textarea>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户http回调地址：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.merchantCallbackUrl"></textarea>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户电话接入号：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}}" type="text" ng-model="vm.item.merchantIvrNo"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户证书路径：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}}" type="text" ng-model="vm.item.certPath"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户是否校验退款IP：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.isRefundIpVal"
                            ng-options="key as value for (key,value) in {'0':'不校验','1':'校验'}"
                            ng-init="vm.item.isRefundIpVal='1'"></select>
                </td>
            </tr>


            <tr ng-if="vm.item.isRefundIpVal == 1">
                <td class="text-right">
                    商户退款IP：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" required name="merchantRefundIp"
                              ng-model="vm.item.merchantRefundIp" w5c-dynamic-element></textarea>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    是否自动补单：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.isAutoCheckOrder"
                            ng-options="key as value for (key,value) in {'0':'不需要','1':'需要'}"
                            ng-init="vm.item.isAutoCheckOrder='0'"></select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    域名：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.domainName"></textarea>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    退款通知地址：
                </td>
                <td>
                    <input class="{{vm.constant.inputClass}}" type="text" ng-model="vm.item.merchantRefundNotifyUrl"/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    是否交易IP：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.isIpVal"
                            ng-options="key as value for (key,value) in {'0':'不校验','1':'校验'}"
                            ng-init="vm.item.isIpVal='0'"></select>
                </td>
            </tr>


        </table>
    <%--</form>--%>
</div>
</body>
</html>