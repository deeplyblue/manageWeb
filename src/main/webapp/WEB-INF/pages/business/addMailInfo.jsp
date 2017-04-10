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
            <tr>
                <td class="text-right col-xs-3">
                    业务机构：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.companyCode" class="form-control input-sm">--%>
                    <select ng-model="vm.item.companyCode" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.COMANY_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                      姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.username" class="form-control input-sm">
            </td>
            </tr>
                <tr>
                <td class="text-right">
                     邮箱 ：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.email" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    电话：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.telephone" class="form-control input-sm">

                </td>
                </tr>
            <tr>
                <td class="text-right">
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.mobile"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮寄地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.postAddr"class="form-control input-sm" />


                </td>
            </tr>
            <tr>
                <td class="text-right">
                    发票抬头：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.invoiceHead"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.zipcode"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
            <td class="text-right">
                单位名称：
            </td>
            <td>
                <input type="text" ng-model="vm.item.userCompany"class="form-control input-sm" />
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    商户归属地：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.areaCode"class="form-control input-sm" />--%>
                    省:<select ng-model="vm.pro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                              ng-init="vm.item.areaCode = vm.pro.areaCode + '' + vm.city.cityCode">

                </select>
                    <div ng-if="vm.pro" >
                        市:<select ng-model="vm.city" ng-options="item as item.cityName for item in vm.pro.citys" class="{{vm.constant.inputClass}} "
                                  ng-change="vm.item.areaCode = vm.pro.areaCode + '' + vm.city.cityCode">
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
                    <input type="text" ng-model="vm.item.taxpayerName"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    纳税人识别号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerNo"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.certificateAddr"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    财务联系电话：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.financialPhone"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人开户行：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerBankCode"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人银行账户：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.taxpayerBankAccount"class="form-control input-sm" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
