<%@ page import="com.oriental.manage.core.enums.CompanyType" %><%--
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
        <input type="text" ng-model="vm.item.companyType"
               ng-init="vm.item.companyType = '<%=CompanyType.MERCHANT.getCode()%>'" ng-show="false"/>
        <tr>
            <td class="text-right col-xs-3">
                商户代码：
            </td>
            <td class="col-xs-4">
                <input type="text" name="companyCode" ng-model="vm.item.companyCode"
                       class="{{vm.constant.inputClass}}"
                       typeahead-min-length="0"
                       uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:10"
                       required>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                结算周期：
            </td>
            <td>
                <select name="clrCycle" ng-model="vm.item.clrCycle" class="{{vm.constant.inputClass}}"
                        ng-options="key as value for (key,value) in vm.cached.CLR_CYCLE" required>
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr ng-if="vm.item.clrCycle != '01' && vm.item.clrCycle != null">
            <td class="text-right">
                结算点：
            </td>
            <td>
                <select ng-if="vm.item.clrCycle=='03'|| vm.item.clrCycle=='04'" ng-model="vm.item.clrPoint"
                        class="{{vm.constant.inputClass}}"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT">
                    <option value="">请选择</option>
                </select>
                <select ng-if="vm.item.clrCycle=='02'|| vm.item.clrCycle=='05'" ng-model="vm.item.clrPoint"
                        class="{{vm.constant.inputClass}}"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT_WEEK">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                结算范围：
            </td>
            <td>
                <select name="clrRange" ng-model="vm.item.clrRange" class="{{vm.constant.inputClass}}"
                        ng-options="key as value for (key,value) in vm.cached.CLR_RANGE" required>
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                结算类型：
            </td>
            <td>
                <select name="clrType" ng-model="vm.item.clrType" class="{{vm.constant.inputClass}}"
                        ng-init="vm.item.clrType = '01'"
                        ng-options="key as value for (key,value) in vm.cached.CLR_TYPE"
                        required>
                </select>
            </td>
        </tr>

        <tr>
            <td class="text-right">
                报表类型：
            </td>
            <td>
                <select ng-model="vm.item.reportType" class="{{vm.constant.inputClass}}"
                        ng-init="vm.item.reportType = '01'"
                        ng-options="key as value for (key,value) in vm.cached.REPORT_TYPE">
                </select>
            </td>
        </tr>

        <tr>
            <td class="text-right">
                手续费收取方式：
            </td>
            <td>
                <select ng-model="vm.item.feeClearType" class="{{vm.constant.inputClass}}"
                        ng-options="key as value for (key,value) in vm.cached.FEE_CLEAR_TYPE">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>


        <tr>
            <td class="text-right">
                结算账户：
            </td>
            <td>
                <input type="text" ng-model="vm.item.clrAccount" class="{{vm.constant.inputClass}}"/>
            </td>
        </tr>
        <tr ng-if="vm.item.clrType!=01">
            <td class="text-right">
                特殊结算规则：
            </td>
            <td ng-if="vm.item.clrType==02">
                <select ng-model="vm.item.clrRules" ng-options="key as value for (key,value) in vm.cached.CLR_RULES_1"
                        class="{{vm.constant.inputClass}}">
                    <option value="">请选择</option>
                </select>
            </td>
            <td ng-if="vm.item.clrType==03">
                <select ng-model="vm.item.clrRules" ng-options="key as value for (key,value) in vm.cached.CLR_RULES_2"
                        class="{{vm.constant.inputClass}}">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 01 && vm.item.clrType==02">
        <tr>
            <td colspan="5" style="text-align: center">按支付机构结算配置</td>
        </tr>
        <tr>
            <td>支付机构</td>
            <td>结算周期</td>
            <td>结算点</td>
            <td>经办范围</td>
            <td>操作</td>
        </tr>
        <tr ng-repeat="bean in vm.companyBank track by $index">
            <%--<select ng-model="bean.companyCode"--%>
            <%--ng-options="item.key as item.value for item in vm.cached.MERCHANT_CODE">--%>
            <%--<option value="">请选择</option>--%>

            <%--</select>--%>
            <td>
                <input type="text" name="companyCode" ng-model="bean.companyCode"
                       typeahead-min-length="0"
                       uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10"
                >
            </td>
            <td>
                <select ng-model="bean.clrCycle"
                        ng-options="key as value for (key,value) in vm.cached.CLR_CYCLE">
                    <option value="">请选择</option>
                </select>
            </td>
            <td ng-if="bean.clrCycle != '01' && bean.clrCycle != null">
                <select ng-if="bean.clrCycle=='03'|| bean.clrCycle=='04'" ng-model="bean.clrPoint"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT">
                    <option value="">请选择</option>
                </select>
                <select ng-if="bean.clrCycle=='02'|| bean.clrCycle=='05'" ng-model="bean.clrPoint"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT_WEEK">
                    <option value="">请选择</option>
                </select>
            </td>
            <td ng-if=" bean.clrCycle == null || bean.clrCycle == '01'">

            </td>
            <td>
                <select ng-model="bean.clrRange" ng-options="key as value for (key,value) in vm.cached.CLR_RANGE">
                    <option value="">请选择</option>
                </select>
            </td>
            <td>
                <button ng-click="vm.addCompanyBank($index)" title="增加">增加</button>
                <button ng-click="vm.deleteCompanyBank($index)" title="删除">删除</button>
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 02  && vm.item.clrType==02">
        <tr>
            <td colspan="5" style="text-align: center">商户分账配置</td>
        </tr>
        <tr>
            <td>商户</td>
            <td>结算比例(%)</td>
            <td>操作</td>
        </tr>
        <tr ng-repeat="bean in vm.companyRout track by $index">
            <td>
                <input type="text" name="companyCode" ng-model="bean.companyCode"
                       typeahead-min-length="0"
                       uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10"
                >
            </td>
            <td>
                <input type="text" ng-model="bean.propNum"/>
            </td>
            <td>
                <button ng-click="vm.addCompanyRout($index)" title="增加">增加</button>
                <button ng-click="vm.deleteCompanyRout($index)" title="删除">删除</button>
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 03 && vm.item.clrType==03">
        <tr>
            <td colspan="5" style="text-align: center">商户分润配置</td>
        </tr>
        <tr>
            <td>商户</td>
            <td>结算比例(%)</td>
            <td>操作</td>
        </tr>
        <tr ng-repeat="bean in vm.companyProfit track by $index">
            <td>
                <input type="text" name="companyCode" ng-model="bean.companyCode"
                       typeahead-min-length="0"
                       uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10"
                >
            </td>
            <td>
                <input type="text" ng-model="bean.propNum"/>
            </td>
            <td>
                <button ng-click="vm.addCompanyProfit($index)" title="增加">增加</button>
                <button ng-click="vm.deleteCompanyProfit($index)" title="删除">删除</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
