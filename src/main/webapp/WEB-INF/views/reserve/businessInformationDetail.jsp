<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form ng-repeat="bean in vm.item.list track by $index">
        <table class="table table-bordered" >

            <tr>
                <td class="text-right">
                    支付机构编号
                </td>
                <td>
                    {{bean.orgNO}}
                </td>
                <td class="text-right">
                    商户编号
                </td>
                <td>{{bean.merchantNO}}</td>
            </tr>
            <tr>
                <td class="text-right">
                    商户类型
                </td>
                <td>{{bean.merchantType}}</td>

                <td class="text-right">
                    商户名称
                </td>
                <td>{{bean.merchantName}}</td>
            </tr>
            <tr>
                <td class="text-right">
                    商户结算周期
                </td>
                <td>{{bean.merchantSettCycle}}</td>
                <td class="text-right">
                    商户结算账户
                </td>
                <td>
                    {{bean.merchantAcct}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    商户结算账户户名
                </td>
                <td>
                    {{bean.merchantAcctName}}
                </td>
                <td class="text-right">
                    商户结算账户开户行行号
                </td>
                <td>
                    {{bean.bankCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    商户结算账户开户行名称
                </td>
                <td>
                    {{bean.merchantOpenBank}}
                </td>

                <td class="text-right">
                    手续费费率
                </td>
                <td>
                    {{bean.feeRate}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    经营范围
                </td>
                <td>
                    {{bean.businessScope}}
                </td>
                <td class="text-right">
                    商户联系人
                </td>
                <td>
                    {{bean.contacts}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    商户联系方式
                </td>
                <td>
                    {{bean.contactInfo}}
                </td>
                <td class="text-right">
                    商户地址
                </td>
                <td>
                    {{bean.merchantAddr}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作类型
                </td>
                <td>
                    {{bean.operateType}}
                </td>

                <td class="text-right">
                    操作时间
                </td>
                <td colspan="5">
                    {{bean.operateTime}}
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
