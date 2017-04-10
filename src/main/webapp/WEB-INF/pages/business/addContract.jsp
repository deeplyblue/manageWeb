<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div>
    <form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.companyCode" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contCode" class="form-control input-sm">
            </td>
            </tr>
                <tr>
                <td class="text-right">
                    合同名称 ：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contName" class="form-control input-sm">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同概要：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contSummary" class="form-control input-sm">

                </td>
                </tr>
            <tr>
                <td class="text-right">
                    合同签订日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.item.contSignedDate "
                           is-open="vm.datepicker.contSignedDate "
                            required
                           required/>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    合同生效日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.item.contBgnDate"

                            required
                           required/>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同结束日期：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup
                           ng-model="vm.item.contEndDate"

                            required
                           required/>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    签约机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.signedOrgCode"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
            <td class="text-right">
                企业代码：
            </td>
            <td>
                <input type="text" ng-model="vm.item.enterpriseCode"class="form-control input-sm" />
            </td>
        </tr>
            <tr>
                <td class="text-right">
                    合同附件：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsContAttach"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    开户行许可证：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsOpenBankCert"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人证书：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsRatePayerCert"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsTaxRegisterCert"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsBizLicenseCert"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织机构代码证：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsOrganizationCodeCert"class="form-control input-sm" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行基本信息表：
                </td>
                <td>
                    <input type="file" ng-model="vm.item.dfsBankFile"class="form-control input-sm" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
