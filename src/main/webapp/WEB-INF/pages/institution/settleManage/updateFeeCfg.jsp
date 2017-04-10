<%--
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
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" readonly="readonly" ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}">
                    <br/>{{vm.cached.COMANY_CODE[vm.item.companyCode]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    有效开始日期：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.item.validateBeginDate"
                            required name="validateBeginDate"
                           required/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    有效结束日期：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.item.validateEndDate"
                            required name="validateEndDate"
                           required/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    是否返回手续费：
                </td>
                <td>
                    <select ng-model="vm.item.reFeeFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'0':'不返还','1':'返还'}"
                            required name="reFeeFlag">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    业务交易类型：
                </td>
                <td class="col-xs-4">
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.transCode"
                            ng-options="temp.key as temp.value for temp in vm.cached.PAY_TRANS_CODE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    接入渠道：
                </td>
                <td class="col-xs-4">
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.connChannel"
                            ng-options="temp.key as temp.value for temp in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    计费方法：
                </td>
                <td>
                    <select ng-model="vm.item.feeMethod" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'按笔','02':'按周期'}"  required name="feeMethod">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    计费单位：
                </td>
                <td>
                    <select ng-model="vm.item.feeUnit" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'按金额','02':'按笔'}"  required name="feeUnit">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    手续费计费方法：
                </td>
                <td>
                    <select ng-model="vm.item.feeSegType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'阶梯','02':'累进'}"  required name="feeSegType">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    结算精确类型：
                </td>
                <td>
                    <select ng-model="vm.item.rateRoundType" class="{{vm.constant.inputClass}}"  required name="rateRoundType"
                            ng-options="key as value for (key,value) in {'1':'四舍五入','2':'舍位','3':'四舍五一入','4':'五舍五一入'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}" ng-blur=""
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.BANK_INFO | filter:$viewValue | limitTo:10">
                </td>
            </tr>


            <tr>
                <td class="text-right col-xs-3">
                    银行类型：
                </td>
                <td class="col-xs-4">
                    <select class="{{vm.constant.inputClass}}" ng-model="vm.item.bankType"
                            ng-options="temp.key as temp.value for temp in vm.cached.BANK_CARD_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    请选择分段数：<br/>(最多支持10段)&nbsp;
                </td>
                <td>
                    <input type="text" ng-model="vm.tempParams.feeSegCount"
                           ng-blur="vm.appendSizeFun()" class="{{vm.constant.inputClass}}" required name="feeSegCount"
                           w5c-dynamic-element
                           ng-pattern="/^([1-9]|10)$/">
                </td>
            </tr>
        </table>

        <table class="table table-bordered" >
            <tr>
                <td>
                    下限 ~ 上限 (单位：<span ng-if="vm.item.feeUnit == '01'">元</span><span ng-if="vm.item.feeUnit == '02'">笔</span>)<br/>
                    PS: 左 &lt; 金额 &lt;= 右
                </td>
                <td>手续费计算方法</td>
                <td>手续费</td>
                <td>手续费收取下限(单位：元)</td>
                <td>手续费收取上限(单位：元)</td>
            </tr>

            <tr ng-repeat="temp in vm.item.feeSegRateModelList track by $index ">
                <td width="300px">
                    <input ng-if="$index==0" ng-init="vm.item.feeSegRateModelList[$index].segLowLmt='0'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt" readonly="readonly" class="{{vm.constant.inputClass}}">
                    <input ng-if="$index!=0" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt"
                           readonly="readonly" class="{{vm.constant.inputClass}}">
                    ~
                    <input ng-if="$index == vm.item.feeSegRateModelList.length-1" readonly="readonly" ng-init="vm.item.feeSegRateModelList[$index].segUppLmt='99999999999999999'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
                           class="{{vm.constant.inputClass}}">
                    <input ng-if="$index != vm.item.feeSegRateModelList.length-1" type="text"
                           ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
                           w5c-dynamic-element w5c-dynamic-name="'feeSegRateMode' + $index" required
                           ng-blur="vm.checkUppLmtAmt($index)"  class="{{vm.constant.inputClass}}">
                </td>
                <td width="110px">
                    <input ng-show="false" ng-model="vm.item.feeSegRateModelList[$index].orderSeq" ng-init="vm.item.feeSegRateModelList[$index].orderSeq=$index+1"/>
                    <select ng-model="vm.item.feeSegRateModelList[$index].feeSegMethod"
                            class="{{vm.constant.inputClass}}" style="width: 100px"
                            ng-options="key as value for (key,value) in {'01':'固定','02':'百分比'}"
                            w5c-dynamic-element w5c-dynamic-name="'feeSegMethod' + $index"
                            required >
                        <option value="">请选择</option>
                    </select>
                </td>
                <td width="150px">
                    <input type="text" ng-model="vm.item.feeSegRateModelList[$index].fixFee"
                           class="{{vm.constant.inputClass}}" style="width: 100px"
                           w5c-dynamic-element w5c-dynamic-name="'fixFee' + $index"
                           required >
                    <span ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod=='01'">元</span>
                    <span ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod=='02'">%</span>
                </td>
                <td>
                    <input ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod == '02'" type="text"
                           ng-model="vm.item.feeSegRateModelList[$index].feeLowLmt"
                           ng-init="vm.item.feeSegRateModelList[$index].feeLowLmt = vm.item.feeSegRateModelList[$index].feeLowLmt / 100"
                           w5c-dynamic-element w5c-dynamic-name="'feeLowLmt' + $index" required
                           ng-blur="vm.checkFeeLowLmt($index)"
                           class="{{vm.constant.inputClass}}" style="width: 100px">
                </td>
                <td>
                    <input ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod == '02'" type="text"
                           ng-model="vm.item.feeSegRateModelList[$index].feeUppLmt"
                           ng-init="vm.item.feeSegRateModelList[$index].feeUppLmt = vm.item.feeSegRateModelList[$index].feeUppLmt / 100"
                           w5c-dynamic-element w5c-dynamic-name="'feeUppLmt' + $index" required
                           ng-blur="vm.checkFeeUppLmt($index)" class="{{vm.constant.inputClass}}" style="width: 100px">
                </td>
            </tr>
        </table>

</div>
</body>
</html>
