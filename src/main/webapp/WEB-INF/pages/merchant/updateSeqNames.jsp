<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/23
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form name="updateSeqName" novalidate w5c-form-validate class="form-inline" autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.payOrgCode" class="{{vm.constant.inputClass}}" readonly="readonly">
                        <%--<select ng-model="vm.item.payOrgCode" class="form-control"--%>
                                <%--ng-options="item.key as item.value for item in vm.cached.COMANY_CODE">--%>
                        <%--</select>--%>
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
    </form>
</div>
</body>
</html>
