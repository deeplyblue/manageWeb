<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/9
  Time: 9:51
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <script type="text/javascript" src="${ctx}/js/jquery/tableExport.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.base64.js"></script>
</head>
<body>
<div common-modal-form>
    <div id="withExport">

            <button ng-click="vm.handleSomeFee()" ng-if="vm.clrFlag == '0'">经办</button>


            <button onclick="$('#withExport .myTable').tableExport({type:'excel',escape:'false',ignoreColumn:[20],tableName:'商户周期经办'})">
                下载
            </button>

        <table table-detail>
            <thead>
            <tr>
                <%--
                    flag为002分账分账结算明细
                    flag为003手续费
                --%>
                <%--<th ng-if="vm.flag == '003'">
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.item">
                </th>--%>
                <th>序号</th>
                <th>商户</th>
                <th>分账商户</th>
                <th>结算类型</th>
                <th ng-if="vm.flag == '002'">应付笔数</th>
                <th ng-if="vm.flag == '002'">应付金额</th>
                <th ng-if="vm.flag == '002'">应退笔数</th>
                <th ng-if="vm.flag == '002'">应退金额</th>
                <th ng-if="vm.flag == '002'">结算净额</th>
                <th ng-if="vm.flag == '003'">应收佣金</th>
                <th ng-if="vm.flag == '003'">应退佣金</th>
                <th ng-if="vm.flag == '003'">应付净额</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>结算时间</th>
                <th>报表类型</th>
                <th>经办批次号</th>
                <th>经办人</th>
                <th>调整</th>
                <th>结算</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody ng-repeat-start="bean in vm.item.mchntClearDetailSplitModelList track by $index">
            <tr my-color="bean.clrAdjustFlag" my-color-true="02" my-color-code="#f08080">
                <%--<td ng-if="vm.flag == '003'">
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>--%>
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode + '']}}({{bean.mchntCode}})</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCodeSplit + '']}}({{bean.mchntCodeSplit}})</td>
                <td>{{vm.cached.CLR_TYPE[bean.clrType]}}</td>
                <td ng-if="vm.flag == '002'">{{bean.payableCount}}</td>
                <td ng-if="vm.flag == '002'">{{bean.payableAmt/100 | currency:''}}</td>
                <td ng-if="vm.flag == '002'">{{bean.receivableCount}}</td>
                <td ng-if="vm.flag == '002'">{{bean.receivableAmt/100 | currency:''}}</td>
                <td ng-if="vm.flag == '002'">{{(bean.payableAmt - bean.receivableAmt)/100 | currency:''}}</td>
                <td ng-if="vm.flag == '003'">{{bean.feeAmt/100 | currency:''}}</td>
                <td ng-if="vm.flag == '003'">{{bean.reFeeAmt/100 | currency:''}}</td>
                <td ng-if="vm.flag == '003'">{{(bean.feeAmt - bean.reFeeAmt)/100 | currency:''}}</td>
                <td>{{bean.startDate | date:'yyyyMMdd'}}</td>
                <td>{{bean.endDate | date:'yyyyMMdd'}}</td>
                <td>{{bean.clrDate | date:'yyyyMMdd'}}</td>
                <td>{{vm.cached.REPORT_TYPE[ vm.item.reportType]}}</td>
                <td>{{vm.item.handleBatchNo}}</td>
                <td>{{vm.item.manager}}</td>
                <td>{{vm.cached.CLR_ADJUST_FLAG[bean.clrAdjustFlag]}}</td>
                <td>{{vm.cached.CLR_FLAG[vm.item.clrFlag]}}</td>
                <td>

                        <button ng-show="bean.clrAdjustFlag == '01'"
                                ng-click="vm.toggleAdjustFee(bean)" ng-if="vm.clrFlag == '0'">调整
                        </button>

                    <button ng-show="bean.clrAdjustFlag == '02'" ng-click="vm.queryAdjustFee(bean)">查看</button>
                </td>
            </tr>
            </tbody>
            <tbody ng-repeat-end uib-collapse="bean.collapse">
            <tr class="ignoreRow">
                <th colspan="4">调整</th>
                <td ng-if="vm.flag == '002'" style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.payableCount">
                </td>
                <td ng-if="vm.flag == '002'" style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.payableAmt">
                </td>
                <td ng-if="vm.flag == '002'" style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.receivableCount"></td>
                <td ng-if="vm.flag == '002'" style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.receivableAmt">
                </td>
                <td ng-if="vm.flag == '002'">{{vm.adjustModel.payableAmt - vm.adjustModel.receivableAmt | currency:''}}</td>
                <td ng-if="vm.flag == '003'" style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.reFeeAmt">
                </td>
                <td ng-if="vm.flag == '003'" style="padding: 0 0 0 0"><input type="number" style="width: 45px" ng-model="vm.adjustModel.feeAmt">
                </td>
                <td ng-if="vm.flag == '003'">{{vm.adjustModel.reFeeAmt - vm.adjustModel.feeAmt | currency:''}}</td>
                <td colspan="7">备注：<input ng-model="vm.adjustModel.remark"></td>
                <td>
                    <button ng-show="vm.item.clrFlag == '0'" ng-disabled="commonModalForm.$invalid"
                            ng-click="vm.saveFeeAdjust(bean,vm.adjustModel)">保存
                    </button>
                </td>
            </tr>
            <tr class="ignoreRow">
                <th  colspan="4">调整后</th>
                <td ng-if="vm.flag == '002'">{{bean.payableCount - (-vm.adjustModel.payableCount)}}</td>
                <td ng-if="vm.flag == '002'">{{bean.payableAmt/100 - (-vm.adjustModel.payableAmt) | currency:'￥'}}</td>
                <td ng-if="vm.flag == '002'">{{bean.receivableCount - (-vm.adjustModel.receivableCount)}}</td>
                <td ng-if="vm.flag == '002'">{{bean.receivableAmt/100 - (-vm.adjustModel.receivableAmt) | currency:'￥'}}</td>
                <td ng-if="vm.flag == '002'">{{((bean.payableAmt - bean.receivableAmt)/100) - (-(vm.adjustModel.payableAmt -
                    vm.adjustModel.receivableAmt)) | currency:'￥'}}
                </td>
                <td ng-if="vm.flag == '003'">{{bean.feeAmt/100 - (-vm.adjustModel.reFeeAmt) | currency:'￥'}}</td>
                <td ng-if="vm.flag == '003'">{{bean.reFeeAmt/100 - (-vm.adjustModel.feeAmt) | currency:'￥'}}</td>
                <td ng-if="vm.flag == '003'">{{((bean.feeAmt - bean.reFeeAmt)/100) - (-(vm.adjustModel.reFeeAmt - vm.adjustModel.feeAmt)) |
                    currency:'￥'}}
                </td>
                <td colspan="8"></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
