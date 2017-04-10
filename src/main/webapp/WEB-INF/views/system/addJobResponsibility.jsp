<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">职责名称：</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.jobName" class="{{vm.constant.inputClass}}"
                           w5c-unique-check="{url:'${ctx}/system/jobResponsibility/checkName?name='+vm.item.jobName}"
                           name="itemName" required>
                </td>
            </tr>
            <tr>
                <td class="text-right">优先级：</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.priority" class="{{vm.constant.inputClass}}"
                           ng-pattern="/^([1-9]|10)$/" name="colNameCn" required>
                </td>
            </tr>
        </table>

        <table class="table">
            <thead>
            <tr>
                <td class="text-left">步骤：</td>
                <td class="text-left">步骤内容描述：</td>
                <%--<td class="text-right">操作：</td>--%>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.basicData track by $index">
            <tr class="dataDictInfo">
                <td class="text-left">
                <input type="text" ng-model="bean.step" class="{{vm.constant.inputClass}}" w5c-dynamic-element
                       name="step" required readonly="readonly">
                </td>
                <td class="text-left">
                    <textarea type="text" ng-model="bean.stepContent" class="{{vm.constant.inputClass}}" w5c-dynamic-element
                              w5c-dynamic-name="'stepContent'+ $index"required/>
                </td>

                <td class="text-right" ng-if="vm.basicData.length==$index+1">
                    <div  ng-click="vm.addDataDictInfo($index)" title="增加">增加</div>
                    <div  ng-click="vm.deleteDataDictInfo($index)" title="删除">删除</div>
                </td>
            </tr>
            </tbody>
        </table>

</div>
</body>
</html>
