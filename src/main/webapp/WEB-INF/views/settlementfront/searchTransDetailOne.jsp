<%--
  User: 蒯越
  Date: 2016/6/23
  Time: 10:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed" ng-repeat="bean in vm.item.results.mchntTransDetailDTOList">
        <tr>
            <td colspan="6">商户交易订单【{{$index + 1}}】</td>
        </tr>
        <tr>
            <td class="text-right">商户</td>
            <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode]}}({{bean.mchntCode}})</td>
            <td class="text-right">支付订单号</td>
            <td>{{bean.innerPayTransNo}}</td>
            <td class="text-right">接入渠道</td>
            <td>{{bean.connChannelDesc}}({{bean.connChannel}})</td>
        </tr>
        <tr>
            <td class="text-right">订单号</td>
            <td>{{bean.orderNo}}</td>
            <td class="text-right">订单时间</td>
            <td>{{bean.orderTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">订单金额(元)</td>
            <td>{{bean.orderAmt / 100 | currency:""}}</td>
        </tr>
        <tr>
            <td class="text-right">交易类型</td>
            <td>{{bean.transCodeDesc}}({{bean.transCode}})</td>
            <td class="text-right">交易状态</td>
            <td>{{bean.transStatusDesc}}({{bean.transStatus}})</td>
            <td class="text-right">交易金额(元)</td>
            <td>{{bean.transAmt / 100 | currency:""}}</td>
        </tr>
        <tr>
            <td class="text-right">业务请求流水号</td>
            <td>{{bean.busiReqNo}}</td>
            <td class="text-right">业务响应流水号</td>
            <td>{{bean.busiRespNo}}</td>
            <td class="text-right">业务响应码</td>
            <td>{{bean.busiRespCode}}</td>
        </tr>
        <tr>
            <td class="text-right">业务请求时间</td>
            <td>{{bean.busiReqTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">业务响应时间</td>
            <td>{{bean.busiRespTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">业务响应描述</td>
            <td>{{bean.busiRespDesc}}</td>
        </tr>
        <tr>
            <td class="text-right">勾兑状态</td>
            <td>{{bean.chkStatusDesc}}({{bean.chkStatus}})</td>
            <td class="text-right">平台清算日期<br/>(内部)</td>
            <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
            <td class="text-right">业务清算日期<br/>(外部)</td>
            <td>{{bean.busiSettleDate | date:"yyyy-MM-dd"}}</td>
        </tr>
        <tr>
            <td class="text-right">是否参与清算</td>
            <td>

                {{vm.cached.SEETLE_FLAG[bean.settleFlag]}}
            </td>
            <td class="text-right">清算状态</td>
            <td>{{bean.settleStatusDesc}}({{bean.settleStatus}})</td>
            <td class="text-right">清算批次号</td>
            <td>{{bean.settleBatchNo}}</td>
        </tr>
        <tr>
            <td class="text-right">正反交易标识</td>
            <td>{{bean.reverseFlagDesc}}({{bean.reverseFlag}})</td>
            <td class="text-right">退款标识</td>
            <td>{{bean.refundFlagDesc}}({{bean.refundFlag}})</td>
            <td class="text-right">原业务请求流水号<br/>(或原订单号)</td>
            <td>{{bean.oldBusiReqNo}}</td>
        </tr>
        <tr>
            <td class="text-right">银行代码</td>
            <td>
                <span ng-if="bean.bankCode != null">
                    {{vm.cached.BANK_INFO[bean.bankCode]}}({{bean.bankCode}})
                </span>
            </td>
            <td class="text-right">银行借贷标识</td>
            <td>
                <span ng-if="bean.bankCdFlag == '01'">借记卡(01)</span>
                <span ng-if="bean.bankCdFlag == '02'">贷记卡(02)</span>
            </td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">创建时间</td>
            <td>{{bean.createdTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">创建人</td>
            <td>{{bean.createdBy}}</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">最后更新时间</td>
            <td>{{bean.updatedTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">最后更新人</td>
            <td>{{bean.updatedBy}}</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">附加信息</td>
            <td colspan="5">{{bean.attach}}</td>
        </tr>
        <tr>
            <td class="text-right">备注</td>
            <td colspan="5">{{bean.remark}}</td>
        </tr>
    </table>

    <table class="table table-bordered table-condensed" ng-repeat="bean in vm.item.results.bankTransDetailDTOList">
        <tr>
            <td colspan="6">银行交易订单【{{$index + 1}}】</td>
        </tr>
        <tr>
            <td class="text-right">商户</td>
            <td>{{vm.cached.MERCHANT_CODE[bean.platformCode]}}({{bean.platformCode}})</td>
            <td class="text-right">支付订单号</td>
            <td>{{bean.innerPayTransNo}}</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">支付机构</td>
            <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
            <td class="text-right">接入渠道</td>
            <td>{{bean.connChannelDesc}}({{bean.connChannel}})</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">银行代码</td>
            <td>
                <span ng-if="bean.bankCode != null">
                    {{vm.cached.BANK_INFO[bean.bankCode]}}({{bean.bankCode}})
                </span>
            </td>
            <td class="text-right">银行借贷标识</td>
            <td>
                <span ng-if="bean.bankCdFlag == '01'">借记卡</span>
                <span ng-if="bean.bankCdFlag == '02'">贷记卡</span>
            </td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">交易类型</td>
            <td>{{bean.transCodeDesc}}({{bean.transCode}})</td>
            <td class="text-right">交易状态</td>
            <td>{{bean.transStatusDesc}}({{bean.transStatus}})</td>
            <td class="text-right">交易金额(元)</td>
            <td>{{bean.transAmt / 100 | currency:""}}</td>
        </tr>
        <tr>
            <td class="text-right">银行请求流水号</td>
            <td>{{bean.bankReqNo}}</td>
            <td class="text-right">银行响应流水号</td>
            <td>{{bean.bankRespNo}}</td>
            <td class="text-right">银行响应码</td>
            <td>{{bean.bankRespCode}}</td>
        </tr>
        <tr>
            <td class="text-right">银行请求时间</td>
            <td>{{bean.bankReqTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">银行响应时间</td>
            <td>{{bean.bankRespTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">银行响应描述</td>
            <td>{{bean.bankRespDesc}}</td>
        </tr>
        <tr>
            <td class="text-right">勾兑状态</td>
            <td>{{bean.chkStatusDesc}}({{bean.chkStatus}})</td>
            <td class="text-right">平台清算日期<br/>(内部)</td>
            <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
            <td class="text-right">银行清算日期<br/>(外部)</td>
            <td>{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
        </tr>
        <tr>
            <td class="text-right">是否参与清算</td>
            <td>
                {{vm.cached.SEETLE_FLAG[bean.settleFlag]}}
            </td>
            <td class="text-right">清算状态</td>
            <td>{{bean.settleStatusDesc}}({{bean.settleStatus}})</td>
            <td class="text-right">清算批次号</td>
            <td>{{bean.settleBatchNo}}</td>
        </tr>
        <tr>
            <td class="text-right">正反交易标识</td>
            <td>{{bean.reverseFlagDesc}}({{bean.reverseFlag}})</td>
            <td class="text-right">退款标识</td>
            <td>{{bean.refundFlagDesc}}({{bean.refundFlag}})</td>
            <td class="text-right">原银行请求流水号</td>
            <td>{{bean.oldBankReqNo}}</td>
        </tr>
        <tr>
            <td class="text-right">创建时间</td>
            <td>{{bean.createdTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">创建人</td>
            <td>{{bean.createdBy}}</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">最后更新时间</td>
            <td>{{bean.updatedTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
            <td class="text-right">最后更新人</td>
            <td>{{bean.updatedBy}}</td>
            <td class="text-right"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text-right">附加信息</td>
            <td colspan="5">{{bean.attach}}</td>
        </tr>
        <tr>
            <td class="text-right">备注</td>
            <td colspan="5">{{bean.remark}}</td>
        </tr>
    </table>
</div>
<c:import url="../common/nav.jsp"/>
</body>
</html>
