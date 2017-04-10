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
                <td class="text-right">字典项名：</td>
                <td>
                    <input type="text" ng-model="vm.item.itemName" class="{{vm.constant.inputClass}}" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td class="text-right">列中文名：</td>
                <td>
                    <input type="text" ng-model="vm.item.colNameCn" class="{{vm.constant.inputClass}}" name="colNameCn" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">列英文名：</td>
                <td>
                    <input type="text" ng-model="vm.item.colName" class="{{vm.constant.inputClass}}" name="colName" required>

                </td>
            </tr>
            <tr>
                <td class="text-right">列值项：</td>
                <td >
                    <input type="text" ng-model="vm.item.itemVal" class="{{vm.constant.inputClass}}" name="itemVal" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">描述：</td>
                <td >
                    <input type="text" ng-model="vm.item.itemDesc" class="{{vm.constant.inputClass}}" name="itemDesc" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">列类型：</td>
                <td >
                    <input type="text" ng-model="vm.item.itemType" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">备注：</td>
                <td >
                    <input type="text" ng-model="vm.item.spare1" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
        </table>
</div>
</body>
</html>
