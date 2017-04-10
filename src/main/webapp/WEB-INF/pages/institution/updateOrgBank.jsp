<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<form class="form-inline" autocomplete="off">
    <table class="table table-bordered">
        <tr>
            <td class="text-right">
                机构代码：
            </td>
            <td class="">
                <select ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}}"
                        ng-options="key as vm.getBusiDesc(key,value) for (key,value) in vm.orgBankInfo"
                        ng-change="vm.getBankType(1234)" >
                </select>
            </td>

            <td class="text-right">
                机构退款限额：
            </td>
            <td class="">
                <input type="text" ng-model="vm.item.orgRefundLimit" class="{{vm.constant.inputClass}}">
            </td>

            <td class="text-right">
                银行退款期限：
            </td>
            <td>
                <input type="text" ng-model="vm.item.refundDeadline" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
    </table>


</form>

<div class="row">
    <div class="col-md-2">
        <%--{{vm.cached.BANK_TYPE}}--%>
        <div ng-repeat="bean in vm.cached.CONN_CHANNEL">
            <button type="button" style="width: 100px;margin-bottom: 2px" ng-click="vm.getBankType(bean.key)" class="btn btn-info">
                {{bean.value}}
            </button>
        </div>
    </div>
    <div class="col-md-10" style="border: dashed grey 1px;padding:2px 0px 5px 0px;margin-left: inherit;margin-right: 5px;
    height: 50%;overflow:auto">
        <div>
            <button type="button" ng-repeat="bean in vm.bankTypes" ng-click="vm.getOrgBankInfo(bean.key)">
                {{bean.value}}
            </button>
        </div>
        <div id="orgBanks" style="display: none;">
            <table table-detail>
                <tr>
                    <td width="2%">
                        <input type="checkbox" ng-click="vm.chooseAll($event)" name="bankCodeCheckBox"/>
                    </td>
                    <td width="15%">银行</td>
                    <td width="15%">退款方式</td>
                    <td width="10%">外系统银行名称</td>
                    <td width="10%">银行统计分类</td>
                    <td width="10%">外系统银行代码</td>
                    <td width="10%">交易下限(分)</td>
                    <td width="10%">交易上限(分)</td>
                    <td width="8%">手机与账号是否一致</td>
                    <td width="10%">是否发送至预留手机号</td>
                </tr>
                <tbody ng-repeat="bean in vm.orgBanks track by $index">
                <tr>
                    <td>
                        <input type="checkbox" name="bean.choose" ng-checked="bean.choose == '1'"
                               ng-click="vm.updateChecked($event,bean.bankCode)"/>
                    </td>
                    <td>
                        <div style="width:90px;">{{bean.bankName}}({{bean.bankCode}})
                            <input type="checkbox" name="bean.bankType" value="bean.bankType" style="display: none"/>
                            <input type="checkbox" name="bean.connChannel" value="05" style="display: none"/>
                        </div>
                    </td>
                    <td width="10%">
                        <select class="noSelectStyle" ng-model="bean.refundOlFlag">
                            <option value="0">线下退款</option>
                            <option value="1">线上退款</option>
                            <option value="2">脱机退款</option>
                        </select>
                    </td>
                    <td width="10%">
                        <input type="text" name="orgBankCode" style="width:100%;" ng-model="bean.orgBankCode"/>
                    </td>
                    <td width="10%">
                        <input type="text" name="bankCatalog" style="width:100%;" ng-model="bean.bankCatalog"/>
                    </td>
                    <td width="10%">
                        <input type="text" name="orgBankCodeDesc" style="width:100%;" ng-model="bean.orgBankCodeDesc"/>
                    </td>
                    <td width="10%">
                        <input type="text" name="transAmtLowlimit" style="width:100%;"
                               ng-model="bean.transAmtLowlimit"/>
                    </td>
                    <td width="10%">
                        <input type="text" name="transAmtUpplimit" style="width:100%;"
                               ng-model="bean.transAmtUpplimit"/>
                    </td>
                    <td width="8%">
                        <input type="checkbox" name="bean.payLvl" ng-click="vm.setPayLvlBox($event,bean.bankCode)"
                               ng-checked="bean.payLvl=='1'"/>
                    </td>
                    <td width="10%">
                        <select class="noSelectStyle" onchange="giveSelectValue(this)" ng-model="bean.phoneLvl">
                            <option value="0">低风险</option>
                            <option value="1">高风险</option>
                            <option value="2">中风险</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>

    </div>


</div>
</body>
</html>
