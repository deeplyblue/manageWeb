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
                接入渠道
            </td>
            <td>
                {{vm.cached.CONN_CHANNEL[vm.item.results.channel]}}({{vm.item.results.channel}})
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
                清算日期
            </td>
            <td>
                {{vm.item.results.settleDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                订单类型
            </td>
            <td>
                {{vm.cached.ORDER_TYPE[vm.item.results.orderType]}}({{vm.item.results.orderType}})
            </td>
            <td class="text-right">
                订单金额
            </td>
            <td>
                {{vm.item.results.totalAmt}}
            </td>
            <td class="text-right">
                币种
            </td>
            <td>
                {{vm.item.results.curType}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                订单状态
            </td>
            <td>
                {{vm.cached.ORDER_STATUS[vm.item.results.orderStatus]}}({{vm.item.results.orderStatus}})
            </td>
            <td class="text-right">
                通知状态
            </td>
            <td>
                {{vm.cached.NOTIFY_STATUS[vm.item.results.notifyStatus]}}({{vm.item.results.notifyStatus}})
            </td>
            <td class="text-right">
                通知响应
            </td>
            <td>
                {{vm.item.results.notifyDesc}}
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
                原平台流水号
            </td>
            <td>
                {{vm.item.results.oldOurTransNo}}
            </td>
            <td class="text-right">
                回调类型
            </td>
            <td>
                {{vm.item.results.callbackType}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                前台通知地址
            </td>
            <td colspan="5">
                {{vm.item.results.pgUrl}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                后台通知地址
            </td>
            <td colspan="5">
                {{vm.item.results.bgUrl}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                数字用户号
            </td>
            <td colspan="5">
                {{vm.item.results.digitalPayAccountNo}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                MAC
            </td>
            <td colspan="5">
                {{vm.item.results.mac}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                备注
            </td>
            <td colspan="5">
                {{vm.item.results.attach}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                商品类型
            </td>
            <td>
                {{vm.item.results.goodsType}}
            </td>
            <td class="text-right">
                商品名称
            </td>
            <td>
                {{vm.item.results.goodsName}}
            </td>
            <td class="text-right">
                产品ID
            </td>
            <td>
                {{vm.item.results.productId}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                MCC
            </td>
            <td>
                {{vm.item.results.mccType}}
            </td>
            <td class="text-right">
                MCC名称
            </td>
            <td>
                {{vm.item.results.mccName}}
            </td>
            <td class="text-right">
                客户IP
            </td>
            <td>
                {{vm.item.results.clientIp}}
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
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
