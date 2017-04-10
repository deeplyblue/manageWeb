<%--
  User: JinXin
  Date: 2016/12/21
  查看支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div class="container-fluid">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    {{vm.item.orgNo}}
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    {{vm.item.orgName}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    {{{'01':'支付机构','02':'分公司'}[vm.item.orgType]}}
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    {{vm.item.orgCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证有效期：
                </td>
                <td>
                    {{vm.item.licenseDate|date:'yyyy-MM-dd'}}
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    {{vm.item.licenseNO}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证发放日期：
                </td>
                <td>
                    {{vm.item.licenseGrantDate|date:'yyyy-MM-dd'}}
                </td>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    {{vm.item.businessCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    住所（省份）：
                </td>
                <td>
                    <%--<input type="hidden" ng-model="vm.pro" ng-model-options="item as item.areaName for item  in vm.cached.ALL_CITY"/>--%>
                        {{vm.item.addrProvince}}
                </td>
                <td class="text-right">
                    住所（城市）：
                </td>
                <td>
                    {{vm.item.addrCity}}
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    住所：
                </td>
                <td>
                    {{vm.item.addr}}
                </td>
                <td class="text-right">
                    实际经营地（省份）：
                </td>
                <td>
                    {{vm.item.realProvince}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地（城市）：
                </td>
                <td>
                    {{vm.item.realCity}}
                </td>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    {{vm.item.realAddr}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    {{vm.item.zipCode}}
                </td>
                <td class="text-right">
                    注册资本：
                </td>
                <td>
                    {{vm.item.registeredCapital}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织形式：
                </td>
                <td>
                    {{vm.item.orgForm}}
                </td>
                <td class="text-right">
                    实缴货币资本：
                </td>
                <td>
                    {{vm.item.moneyCapital}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    法定代表人：
                </td>
                <td>
                    {{vm.item.legalPerson}}
                </td>
                <td class="text-right">
                    投诉电话：
                </td>
                <td>
                    {{vm.item.complainTel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    {{vm.item.fax}}
                </td>
                <td class="text-right">
                    审计机构名称：
                </td>
                <td>
                    {{vm.item.auditName}}
                </td>
            </tr>
            <tr>

                <td class="text-right">
                    出资人及持股比例：
                </td>
                <td>
                    {{vm.item.sponsor}}
                </td>
                <td class="text-right">
                    主要出资人及持股比例：
                </td>
                <td>
                    {{vm.item.mastereSponsor}}
                </td>
            </tr>

            <tr>
                <td colspan="4" class="text-center">业务明细</td>
            </tr>
            <tr>
                <td class="text-center" colspan="2">
                    业务种类：
                </td>
                <td class="text-center" colspan="2">
                    业务范围：
                </td>

            </tr>
            <tbody ng-repeat="bean in vm.item.serviceList ">
                <tr>
                    <td  colspan="2">
                        &nbsp;{{vm.cached.SERVICE_TYPE_FOR_RESERVE[bean.serviceType]}}
                    </td>
                    <td   colspan="2" >
                        <span ng-repeat="beanRange in bean.serviceRange ">
                          &nbsp;&nbsp;&nbsp;{{vm.cached.SERVICE_RANGE_FOR_RESERVE[beanRange.range]}}
                        </span>
                    </td>
                </tr>

            </tbody>
            <tr>
                <td class="text-right">
                    审核状态：
                </td>
                <td >
                    {{vm.cached.HANDLE_STATUS_FOR_RESERVE[vm.item.auditStatus]}}{{{'00':'人行初始下发'}[vm.item.auditStatus]}}
                </td>
                <td class="text-right">
                    是否人行下发：
                </td>
                <td >
                    {{{'01':'是','00':'否'}[vm.item.centerStatus]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作类型：
                </td>
                <td colspan="3">
                    {{{'00':'新增','01':'修改','02':'删除'}[vm.item.operateType]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    备注：
                </td>
                <td colspan="3">
                    {{vm.item.remarks}}
                </td>
            </tr>
        </table>

    </div>
</body>
</html>
