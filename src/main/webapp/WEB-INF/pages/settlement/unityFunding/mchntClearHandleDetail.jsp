<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<head>
    <script type="text/javascript" src="${ctx}/js/jquery/tableExport.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.base64.js"></script>
</head>
<body>
<div common-modal-form>
    <div id="withExport">
        <shiro:hasPermission name="mchntClearDetail_audit">
            <button ng-click="vm.handleSome()" ng-if="vm.clrFlag == '0' && vm.flag != '002'">经办</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="mchntClearDetail_down">
            <button onclick="$('#withExport .myTable').tableExport({type:'excel',escape:'false',ignoreColumn:[0,21],tableName:'商户周期经办'})">
                下载
            </button>
        </shiro:hasPermission>
        <table table-detail>
            <thead>
            <tr>
                <th ng-if="vm.flag != '002' && vm.flag != '003'">
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.item.mchntPayOrgClearDetailModelList" >
                </th>
                <th >序号</th>
                <th>商户</th>
                <th>支付机构</th>
                <th>结算类型</th>
                <th>应付笔数</th>
                <th>应付金额</th>
                <th>应退笔数</th>
                <th>应退金额</th>
                <th>结算净额</th>
                <th>应收佣金</th>
                <th>应退佣金</th>
                <th>应付净额</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>结算时间</th>
                <th>报表类型</th>
                <th>经办批次号</th>
                <th>经办人</th>
                <th>调整</th>
                <th>结算</th>
                <th ng-if="vm.flag !=002">操作</th>
            </tr>
            </thead>

            <tbody ng-repeat-start="bean in vm.item.mchntPayOrgClearDetailModelList track by $index">
            <tr my-color="bean.clrAdjustFlag" my-color-true="02" my-color-code="#f08080">
                <td ng-if="vm.flag != '002' && vm.flag != '003'" >
                    <input name="check" type="checkbox"ng-change="vm.checkA(bean)" ng-model="bean._checked" ng-if="bean.clrFlag == '0'">
                </td>
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.mchntCode + '']}}({{bean.mchntCode}})</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode + '']}}({{bean.payOrgCode}})</td>
                <td>{{vm.cached.CLR_TYPE[bean.clrType]}}</td>
                <td>{{bean.transCountP}}</td>
                <td>{{bean.transAmtP/100 | currency:''}}</td>
                <td>{{bean.transCountR}}</td>
                <td>{{bean.transAmtR/100 | currency:''}}</td>
                <td>{{(bean.transAmtP - bean.transAmtR)/100 | currency:''}}</td>
                <td>{{bean.feeAmtR/100 | currency:''}}</td>
                <td>{{bean.feeAmtP/100 | currency:''}}</td>
                <td>{{(bean.feeAmtR - bean.feeAmtP)/100 | currency:''}}</td>
                <td>{{bean.settleDateBegin | date:'yyyyMMdd'}}</td>
                <td>{{bean.settleDateEnd | date:'yyyyMMdd'}}</td>
                <td>{{bean.clrDate | date:'yyyyMMdd' }}</td>
                <td>{{vm.cached.REPORT_TYPE[bean.reportType]}}</td>
                <td>{{bean.handleBatchNo}}</td>
                <td>{{bean.manager}}</td>
                <td>{{vm.cached.CLR_ADJUST_FLAG[bean.clrAdjustFlag]}}</td>
                <td>{{vm.cached.CLR_FLAG[bean.clrFlag]}}</td>
                <td ng-if="vm.flag !=002">
                    <shiro:hasPermission name="mchntClearDetail_audit">
                        <button ng-show="bean.clrAdjustFlag == '01'"
                                ng-click="vm.toggleAdjust(bean)" ng-if="vm.clrFlag == '0'">调整
                        </button>
                    </shiro:hasPermission>
                    <button ng-show="bean.clrAdjustFlag == '02'" ng-click="vm.queryAdjust(bean)">查看</button>
                </td>
            </tr>
            </tbody>
            <tbody ng-repeat-end uib-collapse="bean.collapse">
            <tr class="ignoreRow">
                <th colspan="5" ng-if="vm.flag != '003'">调整</th>
                <th colspan="4" ng-if="vm.flag == '003'">调整</th>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.payableCount">
                </td>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.payableAmt">
                </td>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.receivableCount"></td>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.receivableAmt">
                </td>
                <td>{{vm.adjustModel.payableAmt - vm.adjustModel.receivableAmt | currency:''}}</td>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px"
                                                    ng-model="vm.adjustModel.reFeeAmt">
                </td>
                <td style="padding: 0 0 0 0"><input type="number" style="width: 45px" ng-model="vm.adjustModel.feeAmt">
                </td>
                <td>{{vm.adjustModel.reFeeAmt - vm.adjustModel.feeAmt | currency:''}}</td>
                <td colspan="7">备注：<input ng-model="vm.adjustModel.remark"></td>
                <td>
                    <button ng-show="bean.clrFlag == '0'" ng-disabled="commonModalForm.$invalid"
                            ng-click="vm.saveAdjust(bean,vm.adjustModel)">保存
                    </button>
                </td>
            </tr>
            <tr class="ignoreRow">
                <th colspan="5" ng-if="vm.flag != '003'">调整后</th>
                <th colspan="4" ng-if="vm.flag == '003'">调整后</th>
                <td>{{bean.transCountP - (-vm.adjustModel.payableCount)}}</td>
                <td>{{bean.transAmtP/100 - (-vm.adjustModel.payableAmt) | currency:'￥'}}</td>
                <td>{{bean.transCountR - (-vm.adjustModel.receivableCount)}}</td>
                <td>{{bean.transAmtR/100 - (-vm.adjustModel.receivableAmt) | currency:'￥'}}</td>
                <td>{{((bean.transAmtP - bean.transAmtR)/100) - (-(vm.adjustModel.payableAmt -
                    vm.adjustModel.receivableAmt)) | currency:'￥'}}
                </td>
                <td>{{bean.feeAmtR/100 - (-vm.adjustModel.reFeeAmt) | currency:'￥'}}</td>
                <td>{{bean.feeAmtP/100 - (-vm.adjustModel.feeAmt) | currency:'￥'}}</td>
                <td>{{((bean.feeAmtR - bean.feeAmtP)/100) - (-(vm.adjustModel.reFeeAmt - vm.adjustModel.feeAmt)) |
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
