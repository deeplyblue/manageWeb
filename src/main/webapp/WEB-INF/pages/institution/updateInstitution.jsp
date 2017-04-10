<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">

            <tr>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    <select ng-model="vm.item.orgType" class="{{vm.constant.inputClass}}" required name="orgType"
                            ng-options="item.key as item.value for item in vm.cached.ORG_TYPR">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    通道类型：
                </td>
                <td>
                    <select ng-model="vm.item.channelType" class="{{vm.constant.inputClass}}" required name="orgType"
                            ng-options="item.key as item.value for item in vm.cached.ORG_CHANNEL_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付银行代码：
                </td>
                <td>
                    <select ng-model="vm.item.payBankCode" class="{{vm.constant.inputClass}}" required name="payBankCode"
                            ng-options="item.key as item.value for item in vm.cached.PAY_BANK_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
            <tr>
            <tr>
                <td class="text-right">
                    机构全称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} "
                           w5c-unique-check="{url:'${ctx}/institution/payment/checkName?orgName='+vm.item.orgName+'&code='+vm.item.orgCode}"
                           required name="orgName">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构简称：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgAbbrName" class="{{vm.constant.inputClass}} "
                           w5c-unique-check="{url:'${ctx}/institution/payment/checkAbbrName?orgAbbrName='+vm.item.orgAbbrName+'&code='+vm.item.orgCode}"
                           required name="orgAbbrName">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构介绍：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgIntro" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构归属地：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.orgArea"class="{{vm.constant.inputClass}} " />--%>
                    省:<select name="merchantArea" ng-model="vm.pro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                              ng-init="vm.item.orgArea = vm.pro.areaCode + '' + vm.city.cityCode" required>

                </select>
                    <div ng-if="vm.pro" >
                        市:<select ng-model="vm.city" ng-options="item as item.cityName for item in vm.pro.citys" class="{{vm.constant.inputClass}} "
                                  ng-change="vm.item.orgArea = vm.pro.areaCode + '' + vm.city.cityCode" required>
                    </select>
                    </div>
                    <input type="text" ng-model="vm.item.orgArea" style="display: none;"/>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构接入时间：
                </td>
                <td>
                    <input  type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.orgConnDate" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgOrganizationCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    法定代表人：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgLegalProxy" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照注册号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bizLicenseNo" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构注册地址：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgRegAddr" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--退款交易是否需要对账：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input type="text" ng-model="vm.item.refundHasCheck" class="{{vm.constant.inputClass}} ">--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td class="text-right">
                    机构告警级别：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.isAlarm" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付机构商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.payOrgMchnt" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付机构级别：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.payOrgLevel" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--是否支持部分退款：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input type="text" ng-model="vm.item.partRefundFlag" class="{{vm.constant.inputClass}} ">--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td class="text-right">--%>
                <%--手续费收取方式：--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<input type="text" ng-model="vm.item.feeCollectWay" class="{{vm.constant.inputClass}} ">--%>
            <%--</td>--%>
        <%--</tr>--%>
            <tr>
                <td class="text-right">
                    内外部类型：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgInOrOutFlag" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--退款方式：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input type="text" ng-model="vm.item.refundMode" class="{{vm.constant.inputClass}} ">--%>
                <%--</td>--%>
            <%--</tr>--%>
        </table>

</div>
</body>
</html>
