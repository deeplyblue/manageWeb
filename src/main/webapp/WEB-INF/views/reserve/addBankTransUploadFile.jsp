<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td class="text-right col-xs-3">
                    备付金账户：
                </td>
                <td class="col-xs-4">
                    <select ng-model="vm.item.accountNo" class="form-control"
                            ng-options="item.accountNo as item.bankCode+'['+item.accountNo+']' for item in vm.cached.RESERVE_LIST ">
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>
            </thead>
            <tbody ng-include="'fileUploader.html'" class="well"></tbody>
            <tbody>
                <tr>
                    <button type="button" ng-click="vm.uploadFile()" class="btn btn-default">确认提交</button>
                </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
