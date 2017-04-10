<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table table-detail>
        <tr>
            <td class="text-right">
                支付机构编号
            </td>
            <td>
            {{vm.item.orgNO}}
            </td>
            <td class="text-right">
                商户编号
            </td>
            <td>{{vm.item.merchantNO}}</td>
        </tr>
        <tr>
            <td class="text-right">
                商户类型
            </td>
            <td>{{vm.cached.BUSINESS_TYPE_FOR_RESERVE[vm.item.merchantType]}}</td>

            <td class="text-right">
                商户名称
            </td>
            <td>{{vm.item.merchantName}}</td>
        </tr>
        <tr>
            <td class="text-right">
                商户结算周期
            </td>
            <td>{{vm.item.merchantSettCycle}}</td>
            <td class="text-right">
                商户结算账户
            </td>
            <td>
                {{vm.item.merchantAcct}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                商户结算账户户名
            </td>
            <td>
                {{vm.item.merchantAcctName}}
            </td>
            <td class="text-right">
                商户结算账户开户行行号
            </td>
            <td>
                {{vm.item.bankCode}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                商户结算账户开户行名称
            </td>
            <td>
                {{vm.item.merchantOpenBank}}
            </td>

            <td class="text-right">
                手续费费率
            </td>
            <td>
                {{vm.item.feeRate}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                经营范围
            </td>
            <td>
                {{vm.item.businessScope}}
            </td>
            <td class="text-right">
                商户联系人
            </td>
            <td>
                {{vm.item.contacts}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                商户联系方式
            </td>
            <td>
                {{vm.item.contactInfo}}
            </td>
            <td class="text-right">
                商户地址
            </td>
            <td>
                {{vm.item.merchantAddr}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                操作类型
            </td>
            <td>
                {{vm.cached.BUSINESS_STATUS_FOR_RESERVE[vm.item.operateType]}}
            </td>

            <td class="text-right">
                操作时间
            </td>
            <td colspan="5">
                {{vm.item.operateTime|date:'yyyy-MM-dd'}}
            </td>
        </tr>
        <tr>
        <td class="text-right">
            支付账户
        </td>
        <td>
            {{vm.item.paymentAcct}}
        </td>

        <td class="text-right">
            MCC码
        </td>
        <td colspan="5">
            {{vm.item.mcc}}
        </td>
    </tr>
        <tr>
            <td class="text-right">
                是否存在代理结算关系
            </td>
            <td>
                {{vm.cached.BUSINESS_AGENT_SETTLEMENT_RESERVE[vm.item.isAgent]}}
            </td>
            <td class="text-right">
                删除标识
            </td>
            <td>
                {{vm.cached.DELETE_STATUS[vm.item.deleteFlag]}}
            </td>
        </tr>

    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
