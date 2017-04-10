<%--
  User: wuxg
  Date: 2016/6/14
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    预付卡卡号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.prepaidCardNum" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    预付卡类型：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.prepaidCardType" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    金额（元）：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.prepaidCardAmt" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    申请赎回时间：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.ransomDate"
                           required/>
                </td><td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    自有账户资金划转时间：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.fundsTransferDate"
                           required/>
                </td><td>
            </tr>
            <tr>
                <td class="text-right">
                    状态：
                </td>
                <td>
                    <select ng-model="vm.item.status" class="form-control"
                            ng-options="key as value for (key,value) in {'0':'未赎回','1':'已申请赎回','2':'已划转资金'} ">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
