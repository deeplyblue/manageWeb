<%--
  User: JinXin
  Date: 2016/12/21
  查看支付机构分公司信息
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
                    分公司编号：
                </td>
                <td>
                    {{vm.item.branchNO}}
                </td>
                <td class="text-right">
                    分公司名称：
                </td>
                <td>
                    {{vm.item.branchName}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    成立时间：
                </td>
                <td>
                    {{vm.item.setupDate|date:'yyyy-MM-dd'}}
                </td>
                <td class="text-right">
                    备案完成时间：
                </td>
                <td>
                    {{vm.item.regDate|date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    备案业务类型：
                </td>
                <td>
                    {{vm.item.regService}}
                </td>
                <td class="text-right">
                    住所（省份）：
                </td>
                <td>
                    {{vm.item.addrProvince}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    住所（城市）：
                </td>
                <td>
                    {{vm.item.addrCity}}
                </td>
                <td class="text-right">
                    住所：
                </td>
                <td>
                    {{vm.item.addrCity}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地（省份）：
                </td>
                <td>
                    {{vm.item.realProvince}}
                </td>
                <td class="text-right">
                    实际经营地（城市）：
                </td>
                <td>
                    {{vm.item.realCity}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    {{vm.item.realAddr}}
                </td>
                <td class="text-right">
                    主要负责人：
                </td>
                <td>
                    {{vm.item.manager}}
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
                    传真：
                </td>
                <td>
                    {{vm.item.fax}}
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
                    负责人办公电话：
                </td>
                <td>
                    {{vm.item.tel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    员工人数：
                </td>
                <td>
                    {{vm.item.peopleNum}}
                </td>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                    {{{'00':'新增','01':'修改','02':'删除'}[vm.item.operateType]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    操作人：
                </td>
                <td>
                    {{vm.item.operator}}
                </td>
                <td class="text-right">
                    操作时间：
                </td>
                <td>
                    {{vm.item.operateTime}}
                </td>
            </tr>

        </table>

    </div>
</body>
</html>
