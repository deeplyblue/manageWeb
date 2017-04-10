<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<body>

<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    结算周期：
                </td>
                <td>
                    <select ng-model="vm.item.itemKey" class="{{vm.constant.inputClass}}" ng-options="key as value for (key,value) in {'05':'周结','06':'月结'}"
                            ng-init="vm.item.itemKey='05'"></select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算点：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" ng-model="vm.item.itemValue" placeholder="多个以“,”分割" ng-keyup="vm.item.itemDesc = {'05':'周结','06':'月结'}[vm.item.itemKey]+'('+vm.item.itemValue+')'"
               w5c-unique-check="{url:'${ctx}/merchant/itCfg/point/check?itemKey='+vm.item.itemKey+'&itemValue='+vm.item.itemValue}"
                           required name="itemValue"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    结算描述：
                </td>
                <td>
                    <textarea class="{{vm.constant.inputClass}}" ng-model="vm.item.itemDesc"></textarea>
                </td>
            </tr>
        </table>
</div>
</body>
</html>