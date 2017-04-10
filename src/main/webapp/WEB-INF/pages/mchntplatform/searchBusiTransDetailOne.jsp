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
                商户代码
            </td>
            <td>
                {{vm.item.results.merchantCode}}
            </td>
            <td class="text-right">
                商户名称
            </td>
            <td>
                {{vm.cached.MERCHANT_CODE[vm.item.results.merchantCode]}}
            </td>
            <td class="text-right">
                交易类型
            </td>
            <td>
                {{vm.cached.MCHNT_TRANS_CODE[vm.item.results.transCode]}}
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
                清算日期
            </td>
            <td>
                {{vm.item.results.settleDate | date:"yyyy-MM-dd"}}
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
                业务流水号
            </td>
            <td>
                {{vm.item.results.busiNo}}
            </td>
            <td class="text-right">
                主业务域
            </td>
            <td>
                {{vm.item.results.mainBusiField}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                业务请求流水号
            </td>
            <td>
                {{vm.item.results.busiReqNo}}
            </td>
            <td class="text-right">
                业务请求日期
            </td>
            <td>
                {{vm.item.results.busiReqDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                业务响应流水号
            </td>
            <td>
                {{vm.item.results.busiRespNo}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                业务响应日期
            </td>
            <td>
                {{vm.item.results.busiRespDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                业务响应码
            </td>
            <td>
                {{vm.item.results.busiRespCode}}
            </td>
            <td class="text-right">
                业务响应描述
            </td>
            <td>
                {{vm.item.results.busiRespDesc}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                删除标识
            </td>
            <td>
                {{vm.cached.DELETE_STATUS[vm.item.results.deleteFlag]}}({{vm.item.results.deleteFlag}})
            </td>
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
        </tr>

    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
