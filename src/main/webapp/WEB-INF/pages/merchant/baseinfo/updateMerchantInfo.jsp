<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    商户代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" readonly="readonly" ng-model="vm.item.merchantCode" class="{{vm.constant.inputClass}} ">
                    <input type="text" ng-model="vm.item.companyType" ng-init="vm.item.companyType='03'" ng-hide="true">
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--业务类型：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--&lt;%&ndash;<input type="text" ng-model="vm.item.companyType" readonly="readonly" class="{{vm.constant.inputClass}} "/>&ndash;%&gt;--%>
                    <%--<select name="companyType" ng-model="vm.item.companyType"  class="{{vm.constant.inputClass}} "--%>
                            <%--ng-options="key as value for (key,value) in {'<%=CompanyType.MERCHANT.getCode()%>':'<%=CompanyType.MERCHANT.getDesc()%>','<%=CompanyType.BUSINESS.getCode()%>':'<%=CompanyType.BUSINESS.getDesc()%>'}" required>--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td class="text-right">
                    商户简称：
                </td>
                <td>
                    <input type="text" name="merchantAbbrName" ng-model="vm.item.merchantAbbrName"
                           w5c-unique-check="{url:'${ctx}/merchant/info/checkAbbName?temp='+vm.item.merchantAbbrName+'&code='+vm.item.merchantCode}"
                           class="{{vm.constant.inputClass}} " required ng-pattern="/^[\s\S]{3,50}$/">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户名称：
                </td>
                <td>
                    <input type="text" name="merchantName" ng-model="vm.item.merchantName"
                           w5c-unique-check="{url:'${ctx}/merchant/info/checkAbbName?temp='+vm.item.merchantName+'&code='+vm.item.merchantCode}"
                           class="{{vm.constant.inputClass}} " required ng-pattern="/^[\s\S]{3,50}$/">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户归属地：
                </td>
                <td>
                    省:<select name="merchantArea" ng-model="vm.pro" ng-options="item as item.areaName for item  in vm.cached.ALL_CITY" class="{{vm.constant.inputClass}} "
                            ng-init="vm.item.merchantArea = vm.pro.areaCode + '' + vm.city.cityCode" required>

                    </select>
                    <div ng-if="vm.pro" >
                        市:<select ng-model="vm.city" ng-options="item as item.cityName for item in vm.pro.citys" class="{{vm.constant.inputClass}} "
                                  ng-change="vm.item.merchantArea = vm.pro.areaCode + '' + vm.city.cityCode" required>
                        </select>
                    </div>
                    <input type="text" ng-model="vm.item.merchantArea" style="display: none;"/>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    商户上线日期：
                </td>
                <td>
                    <input name="merchantOnlineDate" type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"  required
                           ng-model="vm.item.merchantOnlineDate" />
                </td>
            </tr>
            <tr>
                <td class="text-right">

                    商户类型码：
                </td>
                <td>

                    <select name="mccCode" ng-model="vm.mccOne" ng-options="item as item.des for item  in vm.cached.MCC_CODE" class="{{vm.constant.inputClass}}"
                            ng-init="vm.item.mccCode=vm.mccOne.oValue+''+vm.mccTwo.tValue+''+vm.mccThree.trValue" required>
                    </select>
                    <div ng-if="vm.mccOne">
                        <select ng-model="vm.mccTwo" ng-options="item as item.des for item  in vm.mccOne.tList" class="{{vm.constant.inputClass}}"
                                ng-change="vm.item.mccCode=vm.mccOne.oValue+''+vm.mccTwo.tValue+''+vm.mccThree.trValue" required>

                        </select>
                        <div ng-if="vm.mccTwo">
                            <select ng-model="vm.mccThree" ng-options="item as item.des for item  in vm.mccTwo.trList" class="{{vm.constant.inputClass}}"
                                    ng-change="vm.item.mccCode=vm.mccOne.oValue+''+vm.mccTwo.tValue+''+vm.mccThree.trValue">

                            </select>
                        </div>
                    </div>
                    <input type="text" ng-model="vm.item.mccCode" style="display: none;"/>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--是否允许修改手机号：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.alterMobileFlag" class="{{vm.constant.inputClass}} ">--%>
                        <%--<option value="0">否</option>--%>
                        <%--<option value="1">是</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>


            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--报表类型：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.reportType" class="{{vm.constant.inputClass}} "--%>
                            <%--ng-options="item.key as item.value for item in vm.cached.REPRT_TYPE">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <td>详细信息：分割线</td>
            </tr>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--结算机构：--%>
                <%--</td>--%>
                <%--<td>--%>

                    <%--<input  type="text" ng-model="vm.item.clrOrgCode" class="{{vm.constant.inputClass}} ">--%>
                <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <td class="text-right">
                    商户地址：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantAddress" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户邮编：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantZipCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户传真：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantFax" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    商户URL地址：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantUrl" placeholder="域名,多个以“;”分割" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    商户电话接入号：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantIvrNo" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    商户介绍：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantIntro" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>


            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--优惠规则介绍：--%>
                <%--</td>--%>
                <%--<td>--%>

                    <%--<input type="text" ng-model="vm.item.discountRuleIntro" class="{{vm.constant.inputClass}} ">--%>
                <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <td class="text-right">
                    商户LOGO：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.merchantLogo" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>


            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--对账方式：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.chkMethod" class="{{vm.constant.inputClass}} "--%>
                            <%--ng-options="item.key as item.value for item in vm.cached.CHK_METHOD" >--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>

            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--差异处理标识：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.chkDealFlag" class="{{vm.constant.inputClass}} "--%>
                            <%--ng-options="item.key as item.value for item in vm.cached.CHK_DEAL_FLAG">--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>



            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--银联快捷二级商户号：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input ng-model="vm.item.unionPayMerchantCode" class="{{vm.constant.inputClass}} " type="text"/>--%>
                <%--</td>--%>
            <%--</tr>--%>

            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--暂停付款标识：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.suspendFlag" class="{{vm.constant.inputClass}} " required>--%>
                        <%--<option value="0">否</option>--%>
                        <%--<option value="1">是</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <td class="text-right">
                    服务电话：
                </td>
                <td>
                    <input ng-model="vm.item.serviceTelephone" class="{{vm.constant.inputClass}} " type="text"/>
                </td>
            </tr>

        </table>
</div>
</body>
</html>
