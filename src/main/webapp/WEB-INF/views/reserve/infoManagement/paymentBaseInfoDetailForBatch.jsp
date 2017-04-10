<%--
  User: JinXin
  Date: 2016/1/6
  查看支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body >
<div class="container-fluid">

        <table class="table table-bordered table-condensed" ng-repeat="bean in vm.item.list track by $index">
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    {{bean.orgNo}}
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    {{bean.orgName}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    {{{'01':'支付机构','02':'分公司'}[bean.orgType]}}
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    {{bean.orgCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证有效期：
                </td>
                <td>
                    {{bean.licenseDate|date:'yyyy-MM-dd'}}
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    {{bean.licenseNO}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    许可证发放日期：
                </td>
                <td>
                    {{bean.licenseGrantDate|date:'yyyy-MM-dd'}}
                </td>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    {{bean.businessCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    住所（省份）：
                </td>
                <td>
                    {{bean.addrProvince}}
                </td>
                <td class="text-right">
                    住所（城市）：
                </td>
                <td>
                    {{bean.addrCity}}
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    住所：
                </td>
                <td>
                    {{bean.addrCity}}
                </td>
                <td class="text-right">
                    实际经营地（省份）：
                </td>
                <td>
                    {{bean.realProvince}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地（城市）：
                </td>
                <td>
                    {{bean.realCity}}
                </td>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    {{bean.realAddr}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    {{bean.zipCode}}
                </td>
                <td class="text-right">
                    注册资本：
                </td>
                <td>
                    {{bean.registeredCapital}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织形式：
                </td>
                <td>
                    {{bean.orgForm}}
                </td>
                <td class="text-right">
                    实缴货币资本：
                </td>
                <td>
                    {{bean.moneyCapital}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    法定代表人：
                </td>
                <td>
                    {{bean.legalPerson}}
                </td>
                <td class="text-right">
                    投诉电话：
                </td>
                <td>
                    {{bean.complainTel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    {{bean.fax}}
                </td>
                <td class="text-right">
                    审计机构名称：
                </td>
                <td>
                    {{bean.auditName}}
                </td>
            </tr>
            <tr>

                <td class="text-right">
                    出资人及持股比例：
                </td>
                <td>
                    {{bean.sponsor}}
                </td>
                <td class="text-right">
                    主要出资人及持股比例：
                </td>
                <td>
                    {{bean.mastereSponsor}}
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
            <tbody ng-repeat="beanService in bean.serviceList ">
                <tr>
                    <td  colspan="2">
                        <%--{{beanService.serviceType}}--%>
                        &nbsp;{{vm.cached.SERVICE_TYPE_FOR_RESERVE[beanService.serviceType]}}
                    </td>
                    <td   colspan="2" >
                        <span ng-repeat="beanRange in beanService.serviceRange ">
                            <%--{{beanRange.range}}--%>
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
                    {{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.auditStatus]}}
                </td>
                <td class="text-right">
                    申请时间：
                </td>
                <td >
                    {{bean.operateTime|date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作类型：
                </td>
                <td colspan="3">
                    {{{'00':'新增','01':'修改','02':'删除'}[bean.operateType]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    备注：
                </td>
                <td colspan="3">
                    {{bean.remarks}}
                </td>
            </tr>
        </table>

    </div>
</body>
</html>
