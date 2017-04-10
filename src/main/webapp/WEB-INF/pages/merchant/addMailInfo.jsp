<%--
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
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                        <input ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}" required name="companyCode"
                               uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:6">
                        </input>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                      姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.username" class="{{vm.constant.inputClass}}" required name="username">
            </td>
            </tr>
                <tr>
                <td class="text-right">
                     邮箱 ：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.email" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    电话：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.telephone" class="{{vm.constant.inputClass}}">

                </td>
                </tr>
            <tr>
                <td class="text-right">
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.mobile"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮寄地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.postAddr"class="{{vm.constant.inputClass}}" required name="postAddr" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    发票抬头：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.invoiceHead"class="{{vm.constant.inputClass}}"  required name="invoiceHead"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.zipcode"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
            <td class="text-right">
                单位名称：
            </td>
            <td>
                <input type="text" ng-model="vm.item.userCompany"class="{{vm.constant.inputClass}}" />
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    商户归属地：
                </td>
                <td>
                    省:<select ng-model="vm.pro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                                                                                                      ng-init="vm.item.areaCode = vm.pro.areaCode + '' + vm.city.cityCode"  required name="areaCode">

                </select>
                    <div ng-if="vm.pro" >
                        市:<select ng-model="vm.city" ng-options="item as item.cityName for item in vm.pro.citys" class="{{vm.constant.inputClass}} "
                                  ng-change="vm.item.areaCode = vm.pro.areaCode + '' + vm.city.cityCode" required name="cityCode">
                    </select>
                    </div>
                    <input type="text" ng-model="vm.item.areaCode" style="display: none;"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证纳税人名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerName"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    纳税人识别号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerNo"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.certificateAddr"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    财务联系电话：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.financialPhone"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人开户行：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerBankCode"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人银行账户：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerBankAccount"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
        </table>

</div>
</body>
</html>
