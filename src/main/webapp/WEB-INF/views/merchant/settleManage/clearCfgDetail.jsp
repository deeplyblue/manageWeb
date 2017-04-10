<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed">
        <tr>
            <td class="text-right">
                商户名称
            </td>
            <td>
                {{vm.cached.MERCHANT_CODE[vm.item.companyCode]}}[{{vm.item.companyCode}}]
            </td>
        </tr>
        <tr>
            <td class="text-right">
                结算周期
            </td>
            <td>
                {{vm.cached.CLR_CYCLE[vm.item.clrCycle]}}
            </td>
        </tr>
        <tr ng-if="vm.item.clrCycle != '01' && vm.item.clrCycle != null">
        <td class="text-right">
        结算点
        </td>
        <!--结算点开始-->
        <td>
        {{vm.item.clrPointDesc}}
        </td>
        </tr>

        <%--<tr ng-if="vm.item.clrCycle != '01' && vm.item.clrCycle != null">--%>
            <%--<td class="text-right">--%>
                <%--结算点：--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<select ng-if="vm.item.clrCycle=='03'|| vm.item.clrCycle=='04'" ng-model="vm.item.clrPoint"--%>
                        <%--class="{{vm.constant.inputClass}}"--%>
                        <%--ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT">--%>
                    <%--<option value="">请选择</option>--%>
                <%--</select>--%>
                <%--<select ng-if="vm.item.clrCycle=='02'|| vm.item.clrCycle=='05'" ng-model="vm.item.clrPoint"--%>
                        <%--class="{{vm.constant.inputClass}}"--%>
                        <%--ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT_WEEK">--%>
                    <%--<option value="">请选择</option>--%>
                <%--</select>--%>
            <%--</td>--%>
        <%--</tr>--%>

        <tr>
            <td class="text-right">
                结算范围
            </td>
            <td>
                {{vm.cached.CLR_RANGE[vm.item.clrRange] }}
            </td>

        </tr>
        <tr>
            <td class="text-right">
                结算类型
            </td>
            <td>
                {{vm.cached.CLR_TYPE[vm.item.clrType]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                报表类型

            </td>
            <td>
                {{vm.cached.REPORT_TYPE[vm.item.reportType]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                手续费收取方式
            </td>
            <td>
                {{vm.cached.FEE_CLEAR_TYPE[vm.item.feeClearType]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                结算账户
            </td>
            <td>
                {{vm.item.clrAccount}}
            </td>
        </tr>
        <tr ng-if="vm.item.clrType!=01 && vm.item.clrRules !=null ">
            <td class="text-right">
                新增结算规则
            </td>
            <td ng-if="vm.item.clrType==02">
                {{vm.cached.CLR_RULES_1[vm.item.clrRules]}}
            </td>
            <td ng-if="vm.item.clrType==03">
                {{vm.cached.CLR_RULES_2[vm.item.clrRules]}}
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 01">
        <tr>
            <td colspan="5" style="text-align: center">按支付机构结算配置</td>
        </tr>
        <tr>
            <td>支付机构</td>
            <td>结算周期</td>
            <td>结算点</td>
            <td>经办范围</td>
        </tr>
        <tr ng-repeat="bean in vm.item.bankClearCfgList track by $index">
            <td>
                {{vm.cached.COMANY_CODE[bean.companyCode]}}[{{bean.companyCode}}]
            </td>
            <td>
                {{vm.cached.CLR_CYCLE[bean.clrCycle]}}
            </td>
            <!--结算点开始-->
            <td ng-if="bean.clrCycle != '01' && bean.clrCycle != null">
                <select ng-if="bean.clrCycle=='03'|| bean.clrCycle=='04'" ng-model="bean.clrPoint"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT" disabled="disabled">
                    <option value="">请选择</option>
                </select>
                <select ng-if="bean.clrCycle=='02'|| bean.clrCycle=='05'" ng-model="bean.clrPoint"
                        ng-options="value as key for (key,value) in vm.cached.CLEAR_VALUE_POINT_WEEK" disabled="disabled">
                    <option value="">请选择</option>
                </select>
            </td>
            <td ng-if="bean.clrCycle == '01' || bean.clrCycle == null">

            </td>
            <!--结算点结束-->
            <td>
                {{vm.cached.CLR_RANGE[bean.clrRange]}}
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 02">
        <tr>
            <td colspan="5" style="text-align: center">商户分账配置</td>
        </tr>
        <tr>
            <td>商户名称</td>
            <td>结算比例(%)</td>
        </tr>
        <tr ng-repeat="bean in vm.item.accountClearCfgList track by $index">
            <td>
                {{vm.cached.COMANY_CODE[bean.companyCode]}}[{{bean.companyCode}}]
            </td>
            <td>
                {{bean.propNum}}
            </td>
        </tr>
    </table>
    <table class="table table-bordered" ng-if="vm.item.clrRules == 03">
        <tr>
            <td colspan="5" style="text-align: center">商户分润配置</td>
        </tr>
        <tr>
            <td>商户名称</td>
            <td>结算比例(%)</td>
        </tr>
        <tr ng-repeat="bean in vm.item.propClearCfgList track by $index">
            <td>
                {{vm.cached.COMANY_CODE[bean.companyCode]}}[{{bean.companyCode}}]
            </td>
            <td>
                {{bean.propNum}}
            </td>
        </tr>
    </table>
</div>
<%--<core:import url="../../common/nav.jsp"/>--%>
</body>
</html>
