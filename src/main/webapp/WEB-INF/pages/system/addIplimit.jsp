<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>

    <table class="table table-bordered">

        <tr>
            <td class="text-right">
                用户编号
            </td>
            <td>
                <input type="text" name="userId" required
                       ng-model="vm.item.userId" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right">
                IP类型
            </td>
            <td>
                <%--<input type="text" ng-model="vm.item.userType" class="{{vm.constant.inputClass}} ">--%>
                <select name="ipType" required
                        ng-model="vm.item.ipType" class="{{vm.constant.inputClass}}"
                        ng-options="item.key as item.value for item in vm.cached.IP_TYPE">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr >
            <td class="text-right">
                启用标志
            </td>
            <td>
                <%--<input type="text" ng-model="vm.item.userType" class="{{vm.constant.inputClass}} ">--%>
                <select name="enableFlag" required
                        ng-model="vm.item.enableFlag" class="{{vm.constant.inputClass}}"
                        ng-options="item.key as item.value for item in vm.cached.ENABLE_LIMITFLAG">
                    <option value="">请选择</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right">
                开始时间
            </td>
            <td>
                <input type="type" name="ipBeginTime" required uib-datepicker-popup="yyyy-MM-dd"
                       ng-model="vm.item.ipBeginTime" class="{{vm.constant.inputClass}} ">
            </td>

        </tr>
        <tr>
            <td class="text-right">
                结束时间
            </td>
            <td>
                <input type="type" name="ipEndTime"  required uib-datepicker-popup="yyyy-MM-dd"
                       ng-model="vm.item.ipEndTime" class="{{vm.constant.inputClass}} ">
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">
                客户端IP
            </td>
            <td class="col-xs-4">
                <input type="text" name="clientIp"
                       ng-model="vm.item.clientIp" class="{{vm.constant.inputClass}}">
            </td>
        </tr>
    </table>


</div>
</body>
</html>
