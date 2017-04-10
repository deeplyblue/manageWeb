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
                <td class="text-right col-xs-3">字典项名：</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.itemName" class="{{vm.constant.inputClass}}" name="itemName" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">列中文名：</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.colNameCn" class="{{vm.constant.inputClass}}" name="colNameCn" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">列英文名：</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.colName" class="{{vm.constant.inputClass}}" name="colName" required>

                </td>
            </tr>
        </table>

        <table class="table">
            <thead>
            <tr>
                <td class="text-right">列值项：</td>
                <td class="text-right">描述：</td>
                <td class="text-right">列类型：</td>
                <td class="text-right">备注：</td>
                <%--<td class="text-right">操作：</td>--%>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.basicData track by $index">
            <tr class="dataDictInfo">
                <td class="text-right">
                <input type="text" ng-model="bean.itemVal" class="form-control input-sm" name="itemVal" required>
                </td>
                <td class="text-right">
                    <input type="text" ng-model="bean.itemDesc" class="form-control input-sm" name="itemDesc" required/>
                </td>
                <td class="text-right">
                    <input type="text" ng-model="bean.itemType" class="form-control input-sm">
                </td>
                <td class="text-right">
                    <input type="text" ng-model="bean.spare1" class="form-control input-sm">
                </td>
                <td class="text-right">
                    <div  ng-click="vm.addDataDictInfo($index)" title="增加">增加</div>
                    <div  ng-click="vm.deleteDataDictInfo($index)" title="删除">删除</div>
                </td>
            </tr>
            </tbody>
        </table>

</div>
</body>
</html>
