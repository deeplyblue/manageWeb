<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/23
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
                    <%--<input type="text" ng-model="vm.item.payOrgCode" class="{{vm.constant.inputClass}}">--%>
                        <input type="text" ng-model="vm.item.payOrgCode" class="{{vm.constant.inputClass}}" required name="companyCode"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:10">
                </td>
            </tr>



            <td class="text-right">
                序列：
            </td>
            <td>
                <input type="text" ng-model="vm.item.seqName"class="{{vm.constant.inputClass}}"  required name="seqName"/>

            </td>

        </tr>
        </table>

</div>
</body>
</html>
