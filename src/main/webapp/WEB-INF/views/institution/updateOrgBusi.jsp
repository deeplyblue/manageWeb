<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>

    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    业务机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.orgCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    业务类型：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.transCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    交易渠道：
                </td>
                <td class="col-xs-4">

                    <select ng-model="vm.item.connChannel" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.CONN_CHANNEL">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    业务渠道：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.busiChannel" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    业务渠道描述：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.busiChannelDesc" class="form-control input-sm">

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    外部业务渠道：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgBusiChannel" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    外部业务渠道描述：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.orgBusiChannelDesc" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    同步标识：
                </td>
                <td>
                    <select  ng-model="vm.item.synchronousFlag" class="form-control">
                    <option value="00">同步</option>
                    <option value="01">异步</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    公私标识：
                </td>
                <td>
                    <select ng-model="vm.item.busiFlag" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.CFG_FLAG">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    限额：
                </td>
                <td>
                    <select ng-model="vm.item.quotaFlag" class="form-control"
                            ng-options="item.key as item.value for item in vm.cached.QUOTA_FLAG">
                    </select>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
