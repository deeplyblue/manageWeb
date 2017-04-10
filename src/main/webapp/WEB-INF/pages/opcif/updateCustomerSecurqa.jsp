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
    <form name="addFeeCfgBankClassCfg" novalidate w5c-form-validate class="form-inline" autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    编号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.questionNo" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    问题：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.question" class="{{vm.constant.inputClass}}">

                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
