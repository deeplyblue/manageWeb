<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构交易名称：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.itemDesc" class="{{vm.constant.inputClass}} " readonly="readonly">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    分账交易代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.spare1" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
