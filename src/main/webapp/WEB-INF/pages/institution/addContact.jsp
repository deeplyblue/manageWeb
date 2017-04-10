<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>


        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                        <input ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}" required name="companyCode"
                               uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:6">
                        </input>

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                   联系人类型：
                </td>
                <td class="col-xs-4">
                    <select ng-model="vm.item.cttType" class="{{vm.constant.inputClass}}" required name="cttType"
                            ng-options="item.key as item.value for item in vm.cached.CTT_TYPE">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    联系人角色：
                </td>
                <td>
                    <select  ng-model="vm.item.cttRole" required name="cttRole" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <option value="A">A角</option>
                        <option value="B">B角</option>
                        <option value="C">C角</option>
                     </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    是否接收公告通知邮件：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.acceptNoticeEmailFlag" class="{{vm.constant.inputClass}} ">--%>
                    <select  ng-model="vm.item.acceptNoticeEmailFlag" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="0">否</option>

                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttName" class="{{vm.constant.inputClass}} " required name="cttName">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮箱：
                </td>
                <td>
                    <input type="email" ng-model="vm.item.cttEmail" class="{{vm.constant.inputClass}} " required name="cttEmail">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttMobile"class="{{vm.constant.inputClass}} " />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    座机号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttPhone" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                   传真：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.cttFax" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>

        </table>

</div>
</body>
</html>
