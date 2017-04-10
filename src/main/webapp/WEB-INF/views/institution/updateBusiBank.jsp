<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body >
<div common-modal-form>

    <table class="table table-bordered">
        <tr>
            <td class="text-left">
                业务银行类型：<select ng-model="vm.queryBean.busiType" class="{{vm.constant.inputClass}}" required name="busiType"
                               ng-options="key as vm.getBusiDesc(key,value) for (key,value) in vm.cached.BUSINESS_FUNDING_SOURCE"
                               ng-change="vm.getBankType(1234)" >
                <option value="">请选择</option>
            </select>
                <span id ="busi_propmt" style="color:red;display: none;">*请先选择业务银行类型</span>
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
            <button type="button" ng-repeat="bean in vm.bankTypes" ng-click="vm.getBusiBankInfo(bean.key)">
                {{bean.value}}
            </button>
        </div>
        <div id="orgBanks" style="display: none;">
            <table table-detail>
                <tr>
                    <td width="2%">
                        <input type="checkbox" ng-click="vm.chooseAll($event)" name="bankCodeCheckBox"/>
                    </td>
                    <td width="55%">全选</td>
                </tr>
                <tbody ng-repeat="bean in vm.orgBanks track by $index">
                <tr>
                    <td>
                        {{bean.choose}}
                        <input type="checkbox" name="bean.choose" ng-checked="bean.choose == '1'"
                               ng-click="vm.updateChecked($event,bean.bankCode)"/>
                    </td>
                    <td>
                       {{bean.bankCodeDesc}}({{bean.bankCode}})
                    </td>
                </tr>
                </tbody>
            </table>

        </div>

    </div>


</div>
</div>
</body>
</html>
