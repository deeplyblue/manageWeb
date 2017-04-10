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
                    姓名：
                </td>
                <td>
                    {{vm.item.name}}
                </td>
                <td class="text-right">
                    身份类型：
                </td>
                <td>
                    {{{'01':'董事','02':'监事','03':'高管'}[vm.item.type]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    职务：
                </td>
                <td>
                    {{vm.item.rule}}
                </td>
                <td class="text-right">
                    办公电话：
                </td>
                <td>
                    {{vm.item.tel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机：
                </td>
                <td>
                    {{vm.item.mobile}}
                </td>
                <td class="text-right">
                    邮件：
                </td>
                <td>
                    {{vm.item.email}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    学历：
                </td>
                <td>
                    {{vm.item.education}}
                </td>
                <td class="text-right">
                    专业：
                </td>
                <td>
                    {{vm.item.proeession}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    身份证号：
                </td>
                <td>
                    {{vm.item.idCard}}
                </td>
                <td class="text-right">
                    从业经历：
                </td>
                <td>
                    {{vm.item.workExp}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    离职标识：
                </td>
                <td>
                    {{{'01':'在职','02':'离职中','03':'已离职'}[vm.item.offStatus]}}
                </td>
                <td class="text-right">
                    是否经总行批复同意：
                </td>
                <td>
                    {{{'01':'是','02':'否'}[vm.item.isAgree]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    审核状态：
                </td>
                <td >
                    {{vm.cached.HANDLE_STATUS_FOR_RESERVE[vm.item.auditStatus]}}
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
        </table>

    </div>
</body>
</html>
