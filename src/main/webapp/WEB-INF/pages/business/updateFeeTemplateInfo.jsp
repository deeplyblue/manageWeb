<%--
  User: wuxg
  Date: 2016/5/31
  Time: 10:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form>
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    模板名称：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.templateName" class="form-control input-sm">
                </td>
            </tr>
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
                    <input type="text" ng-model="vm.tempParams.feeSegCount" ng-init="vm.tempParams.feeSegCount = vm.item.feeSegRateModelList.length" ng-blur="vm.appendSizeFun()" class="form-control input-sm">
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

            <tbody ng-repeat="temp in vm.tempSize track by $index ">
            <tr >
                {{vm.tempSize.length}}
                <td class="text-right">
                    <input ng-if="$index==0" ng-init="vm.item.feeSegRateModelList[$index].segLowLmt='0'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt" readonly="readonly" class="form-control input-sm">
                    <input ng-if="$index!=0" type="text" ng-model="vm.item.feeSegRateModelList[$index].segLowLmt" readonly="readonly" class="form-control input-sm">
                    ~
                    <input ng-if="$index == vm.tempParams.feeSegCount-1" readonly="readonly" ng-init="vm.item.feeSegRateModelList[$index].segUppLmt='99999999999999999'" type="text" ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
                           lass="form-control input-sm">
                    <input ng-if="$index != vm.tempParams.feeSegCount-1" type="text" ng-model="vm.item.feeSegRateModelList[$index].segUppLmt"
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
    </form>
</div>
</body>
</html>
