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
            <tr>
                <td class="text-right">
                   业务机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:10">
                </td>
            </tr>
            <input type="text" ng-model="vm.item.companyType" ng-init="vm.item.companyType = '<%=CompanyType.BUSINESS.getCode()%>'" ng-show="false" />
            <tr>
                <td>
                    配置模式：
                </td>
                <td>
                    <select ng-model="vm.tempParams.temp" class="{{vm.constant.inputClass}}" ng-init="vm.tempParams.temp = '0'"
                            ng-options="key as value for (key,value) in {'0':'自定义','1':'模版'}"/>
                </td>
            </tr>

            <tr ng-if="vm.tempParams.temp == '1'">
                <td class="text-right">
                    请选择模板：
                </td>
                <td>
                    <select ng-model="vm.tempParams.tempId" class="form-control" ng-init="vm.tempParams.tempId = vm.cached.TEMP_CACHE[0]['id']"
                            ng-options="item.id as item.templateName for item in vm.cached.TEMP_CACHE" >
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    有效开始日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.item.validateBeginDate"

                            required
                           required/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    有效结束日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.item.validateEndDate"

                            required
                           required/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    退款是否返回手续费：
                </td>
                <td>

                    <select ng-model="vm.item.reFeeFlag" class="form-control input-sm"
                            ng-options="key as value for (key,value) in {'0':'不返还','1':'返还'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>


            <tbody ng-if="vm.tempParams.temp == '0'">
            <tr>
                <td class="text-right col-xs-3">
                    接入渠道：
                </td>
                <td class="col-xs-4">
                    <select class="form-control input-sm" ng-model="vm.item.connChannel"
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
                    <select ng-model="vm.item.feeMethod" class="form-control input-sm"
                            ng-options="key as value for (key,value) in {'01':'按笔','02':'按周期'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    计费单位：
                </td>
                <td>
                    <select ng-model="vm.item.feeUnit" class="form-control input-sm"
                            ng-options="key as value for (key,value) in {'01':'按金额','02':'按笔'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    手续费计费方法：
                </td>
                <td>

                    <select ng-model="vm.item.feeSegType" class="form-control input-sm"
                            ng-options="key as value for (key,value) in {'01':'分段','02':'累进'}">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    结算精确类型：
                </td>
                <td>

                    <select ng-model="vm.item.rateRoundType" class="form-control input-sm"
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

                    <input type="text" ng-model="vm.item.bankCode" class="form-control input-sm">
                </td>
            </tr>


            <tr>
                <td class="text-right">
                    银行类型：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.bankType" class="form-control input-sm">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    请选择分段数：
                </td>
                <td>
                    <input type="text" ng-model="vm.tempParams.feeSegCount" ng-init="vm.tempParams.feeSegCount = 1" ng-blur="vm.appendSizeFun()" class="form-control input-sm">
                </td>
            </tr>

            <tr>
                <td colspan="5"></td>
            </tr>

            <tr>
                <td>下限 ~ 上限 (单位：按笔)</td>
                <td>手续费计算方法</td>
                <td>手续费(单位：元)</td>
                <td>手续费收取下限(单位：元)</td>
                <td>手续费收取上限(单位：元)</td>
            </tr>

            <tr ng-repeat="temp in vm.tempSize track by $index ">
                <td>
                    <input ng-if="$index==0" ng-init="vm.item.feeSegRateModelList[$index].segLowLmt='0'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt" readonly="readonly" class="form-control input-sm">
                    <input ng-if="$index!=0" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt" readonly="readonly" class="form-control input-sm">
                    ~
                    <input ng-if="$index == vm.tempSize.length-1" readonly="readonly" ng-init="vm.item.feeSegRateModelList[$index].segUppLmt='99999999999999999'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
                           class="form-control input-sm">
                    <input ng-if="$index != vm.tempSize.length-1" type="text" ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
                           ng-blur="vm.item.feeSegRateModelList[$index+1].segLowLmt = vm.item.feeSegRateModelList[$index].segUppLmt"  class="form-control input-sm">
                </td>
                <td>
                    <input ng-show="false" ng-model="vm.item.feeSegRateModelList[$index].orderSeq" ng-init="vm.item.feeSegRateModelList[$index].orderSeq=$index+1"/>
                    <select ng-model="vm.item.feeSegRateModelList[$index].feeSegMethod" ng-options="key as value for (key,value) in {'01':'固定','02':'百分比'}">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td>
                    <input type="text" ng-model="vm.item.feeSegRateModelList[$index].fixFee"  class="form-control input-sm">
                </td>
                <td>
                    <input ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod == '02'" type="text" ng-model="vm.item.feeSegRateModelList[$index].feeLowLmt"  class="form-control input-sm">
                </td>
                <td>
                    <input ng-if="vm.item.feeSegRateModelList[$index].feeSegMethod == '02'" type="text" ng-model="vm.item.feeSegRateModelList[$index].feeUppLmt"  class="form-control input-sm">
                </td>
            </tr>
            </tbody>
        </table>

</div>
</body>
</html>
