<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/6/24
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>

        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    业务类型：
                </td>
                <td class="col-xs-4">
                    <input  ng-model="vm.item.busiType" class="{{vm.constant.inputClass}}"  readonly="readonly">
                    </input>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    支付机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.payOrgCode" class="{{vm.constant.inputClass}}"  readonly="readonly"/>
                </td>
            </tr>

                <td class="text-right">
                    银行类型：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.bankType" class="{{vm.constant.inputClass}}"  readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.bankCode" class="{{vm.constant.inputClass}}"  readonly="readonly"/>
                </td>
                </tr>
            <tr>
                <td class="text-right">
                    启用标记：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.clrMethod"class="{{vm.constant.inputClass}}"  />--%>
                    <select name="clrType" ng-model="vm.item.enableFlag" class="{{vm.constant.inputClass}}"
                            ng-options=" key as value for (key,value) in {'0':'关闭','1':'启用'}" required name="enableFlag">

                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>




        </table>

</div>
</body>
</html>
