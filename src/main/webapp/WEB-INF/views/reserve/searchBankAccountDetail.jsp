<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form ng-repeat="bean in vm.item.list track by $index">
        <table class="table table-bordered" >

            <tr>
                <td class="text-right col-xs-3">
                    银行名称：
                </td>
                <td class="col-xs-4">
                    {{bean.bankCode}}
                </td>

                <td class="text-right col-xs-3">
                    账号：
                </td>
                <td class="col-xs-4">
                    {{bean.accountNo}}
                </td>

            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    户名：
                </td>
                <td class="col-xs-4">
                    {{bean.accountName}}
                </td>

                <td class="text-right col-xs-3">
                    开户行行号：
                </td>
                <td class="col-xs-4">
                    {{bean.openBankCode}}
                </td>
            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    开户行名称：
                </td>
                <td class="col-xs-4">
                    {{bean.openBankName}}
                </td>

                <td class="text-right col-xs-3">
                    账户类型：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.ACCT_TYPE[bean.accountType]}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    开户地：
                </td>
                <td cols=40 rows=4>
                    {{bean.openAccountAddr}}
                    </textarea>
                </td>
            </tr>
        </table>
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    是否跨境人民币账户：
                </td>

                <td class="col-xs-4">
                    {{vm.cached.IS_CROSS_TRANS[bean.isCrossType]}}
                    </textarea>
                </td>

                <td class="text-right col-xs-3">
                    备付金银行性质：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.ACCT_TYPE[bean.bankType]}}
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>