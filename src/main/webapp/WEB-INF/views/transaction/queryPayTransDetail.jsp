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
                支付机构代码
            </td>
            <td>
                {{vm.item.results.payOrgCode}}
            </td>
            <td class="text-right">
                支付机构名称
            </td>
            <td>
                {{vm.cached.COMANY_CODE[vm.item.results.payOrgCode]}}
            </td>
            <td class="text-right">
                支付类型
            </td>
            <td>
                {{vm.cached.PAY_TRANS_CODE[vm.item.results.transCode]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                订单号
            </td>
            <td>
                {{vm.item.results.orderNo}}
            </td>
            <td class="text-right">
                订单日期
            </td>
            <td>
                {{vm.item.results.orderDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                接入渠道
            </td>
            <td>
                {{vm.cached.CONN_CHANNEL[vm.item.results.channel]}}({{vm.item.results.channel}})
            </td>
        </tr>
        <tr>
            <td class="text-right">
                订单状态
            </td>
            <td>
                {{vm.cached.ORDER_STATUS[vm.item.results.transStatus]}}({{vm.item.results.transStatus}})
            </td>
            <td class="text-right">
                订单金额
            </td>
            <td>
                {{vm.item.results.transAmt}}
            </td>
            <td class="text-right">
                同步状态
            </td>
            <td>
                <div ng-show="vm.item.results.syncStatus == '0'">
                    未同步({{vm.item.results.syncStatus}})
                </div>
                <div ng-show="vm.item.results.syncStatus == '1'">
                    已同步({{vm.item.results.syncStatus}})
                </div>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                平台流水号
            </td>
            <td>
                {{vm.item.results.ourTransNo}}
            </td>
            <td class="text-right">
                支付流水号
            </td>
            <td>
                {{vm.item.results.payTransNo}}
            </td>
            <td class="text-right">
                清算日期
            </td>
            <td>
                {{vm.item.results.settleDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                支付请求流水号
            </td>
            <td>
                {{vm.item.results.payReqNo}}
            </td>
            <td class="text-right">
                支付请求日期
            </td>
            <td>
                {{vm.item.results.payReqDate}}
            </td>
            <td class="text-right">
               银行清算日期
            </td>
            <td>
                {{vm.item.results.paySettleDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                支付账号
            </td>
            <td>
                {{vm.item.results.bankAccId}}
            </td>
            <td class="text-right">
                支付人姓名
            </td>
            <td>
                {{vm.item.results.certName}}
            </td>
            <td class="text-right">
                手机号
            </td>
            <td>
                {{vm.item.results.phone}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                支付响应流水号
            </td>
            <td>
                {{vm.item.results.payRespNo}}
            </td>
            <td class="text-right">
                支付响应日期
            </td>
            <td>
                {{vm.item.results.payRespDate}}
            </td>
            <td class="text-right">
                支付响应描述
            </td>
            <td>
                {{vm.item.results.payRespCode}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                银行代码
            </td>
            <td>
                {{vm.cached.ORG_BANK_CODE_DESC[vm.item.results.bankCode]}}({{vm.item.results.bankCode}})
            </td>
            <td class="text-right">
                银行类型
            </td>
            <td>
                {{vm.cached.BANK_TYPE[vm.item.results.bankType]}}({{vm.item.results.bankType}})
            </td>
            <td class="text-right">
                删除标识
            </td>
            <td>
                {{vm.cached.DELETE_STATUS[vm.item.results.deleteFlag]}}({{vm.item.results.deleteFlag}})
            </td>
        </tr>
        <tr>
            <td class="text-right">
                交易标识
            </td>
            <td>
                {{vm.cached.REVERSE_FLAG[vm.item.results.reverseFlag]}}({{vm.item.results.reverseFlag}})
            </td>
            <td class="text-right">
               退款标识
            </td>
            <td>
                {{vm.cached.COMMON_REFUND_FLAG[vm.item.results.refundFlag]}}({{vm.item.results.refundFlag}})
            </td>
            <td class="text-right">
                原支付流水号
            </td>
            <td>
                {{vm.item.results.oldPayTransNo}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                创建时间
            </td>
            <td>
                {{vm.item.results.createTime | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                最后修改时间
            </td>
            <td>
                {{vm.item.results.lastUpdateTime | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                受理服务器IP
            </td>
            <td>
                {{vm.item.results.acceptSrvIp}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                资金类型
            </td>
            <td>
                {{vm.cached.FUND_TYPE[vm.item.results.fundType]}}
            </td>
            <td class="text-right">
                资金渠道
            </td>
            <td>
                {{vm.item.results.fundChannel}}
            </td>
            <td class="text-right">
                监管账户号
            </td>
            <td>
                {{vm.item.results.supervisionAccNo}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                监管子账户号
            </td>
            <td>
                {{vm.item.results.subSupervisionAccNo}}
            </td>
        </tr>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
