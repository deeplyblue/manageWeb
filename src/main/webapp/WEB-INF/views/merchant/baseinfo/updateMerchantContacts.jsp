<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <input type="text" name="companyCode" ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}} " readonly="readonly" required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    联系人类型：
                </td>
                <td>
                    <select ng-model="vm.item.cttType" class="{{vm.constant.inputClass}} " required name="cttType"
                            ng-options="temp.key as temp.value for temp in vm.cached.CTT_TYPE">
                        <option value="">请选择类型</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    联系人角色：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}} "  ng-model="vm.item.cttRole" required name="cttRole"
                            ng-options="key as value for (key,value) in {'A':'A角','B':'B角','C':'C角'}">
                        <option value="">请选择角色</option>
                    </select>
                </td>
            </tr>
            <tr valign="top">
                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" name="cttName" ng-model="vm.item.cttName" class="{{vm.constant.inputClass}} "  required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮箱：
                </td>
                <td>
                    <input type="email" name="cttEmail" ng-model="vm.item.cttEmail" class="{{vm.constant.inputClass}} " required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttMobile"  class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    座机：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttPhone" class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttFax" class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    联系人状态：
                </td>
                <td>
                    <select class="{{vm.constant.inputClass}} "  ng-model="vm.item.cttStatus"
                            ng-options="key as value for (key,value) in {'0':'可用','1':'不可用'}">
                        <option value="">请选择状态</option>
                    </select>
                </td>
            </tr>
        </table>

</div>
</body>
</html>
