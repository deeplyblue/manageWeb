<%--
  Created by IntelliJ IDEA.
  User: yutao
  Date: 2016/12/22
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>
        <table class="table table-bordered">

            <%--<tr>
                <td class="text-right col-xs-3">
                    支付机构编号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgNO" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>--%>
            <tr>
                <td class="text-right col-xs-3">
                    银行行号：
                </td>
                <td class="col-xs-4">
                    <input type="text" name="bankCode" maxlength="14" style="ime-mode:disabled" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    结算日期：
                </td>
                <td class="col-xs-4">
                    <input type="text" name="settleDate" ng-model="vm.item.settleDate" class="form-control"
                           uib-datepicker-popup="yyyy-MM-dd" required>

                </td>
            </tr>
            <tr>
                <td class="text-right ">
                    申请功能：
                </td>
                <td>
                    <select name="type" ng-model="vm.item.type" class="form-control"
                            ng-options="key as value for (key,value) in {'01':'结算单核对','02':'明细核对'} " required>
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
        </table>
</div>
</body>
</html>
