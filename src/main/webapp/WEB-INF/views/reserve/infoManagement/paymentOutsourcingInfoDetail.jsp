<%--
  User: JinXin
  Date: 2016/12/21
  查看支付机构业务外包信息
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
                    外包服务机构名称：
                </td>
                <td>
                    {{vm.item.serviceOrgName}}
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
                    经营范围：
                </td>
                <td>
                    {{vm.item.businessScope}}
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
                    外包服务内容：
                </td>
                <td>
                    {{vm.item.serviceInfo}}
                </td>
                <td class="text-right">
                    成立时间：
                </td>
                <td>
                    {{vm.item.setupDate|date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    注册资本：
                </td>
                <td>
                    {{vm.item.capital}}
                </td>
                <td class="text-right">
                    注册地：
                </td>
                <td>
                    {{vm.item.address}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    {{vm.item.addrCity}}
                </td>
                <td class="text-right">
                    所在城市（省份）：
                </td>
                <td>
                    {{vm.item.localProvince}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    所在城市（城市）：
                </td>
                <td>
                    {{vm.item.localCity}}
                </td>
                <td class="text-right">
                    所在城市：
                </td>
                <td>
                    {{vm.item.city}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    主要负责人：
                </td>
                <td>
                    {{vm.item.manager}}
                </td>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    {{vm.item.zipCode}}
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
                    负责人办公电话：
                </td>
                <td>
                    {{vm.item.tel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    负责人手机：
                </td>
                <td>
                    {{vm.item.mobile}}
                </td>
                <td class="text-right">
                    分润：
                </td>
                <td>
                    {{vm.item.profit}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    保证金：
                </td>
                <td>
                    {{vm.item.bond}}
                </td>
                <td class="text-right">
                    企业资质评估：
                </td>
                <td>
                    {{vm.item.assess}}
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                    {{{'00':'新增','01':'修改','02':'删除'}[vm.item.operateType]}}
                </td>
                <td class="text-right">
                    操作人：
                </td>
                <td>
                    {{vm.item.operator}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作时间：
                </td>
                <td>
                    {{vm.item.operateTime}}
                </td>
                <td class="text-right">
                </td>
                <td>
                </td>
            </tr>

        </table>

    </div>
</body>
</html>
