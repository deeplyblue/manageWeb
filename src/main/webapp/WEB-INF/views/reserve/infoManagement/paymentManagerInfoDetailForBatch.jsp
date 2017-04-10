<%--
  User: JinXin
  Date: 2016/12/21
  查看支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<title>Title</title>--%>
    <%--<script src="${ctx}/js/views/reserve/infoManagement/paymentManagerInfo.js"></script>--%>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

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
                    姓名：
                </td>
                <td>
                    {{bean.name}}
                </td>
                <td class="text-right">
                    身份类型：
                </td>
                <td>
                    {{{'01':'董事','02':'监事','03':'高管'}[bean.type]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    职务：
                </td>
                <td>
                    {{bean.rule}}
                </td>
                <td class="text-right">
                    办公电话：
                </td>
                <td>
                    {{bean.tel}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机：
                </td>
                <td>
                    {{bean.mobile}}
                </td>
                <td class="text-right">
                    邮件：
                </td>
                <td>
                    {{bean.email}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    学历：
                </td>
                <td>
                    {{bean.education}}
                </td>
                <td class="text-right">
                    专业：
                </td>
                <td>
                    {{bean.proeession}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    身份证号：
                </td>
                <td>
                    {{bean.idCard}}
                </td>
                <td class="text-right">
                    从业经历：
                </td>
                <td>
                    {{bean.workExp}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    离职标识：
                </td>
                <td>
                    {{{'01':'在职','02':'离职中','03':'已离职'}[bean.offStatus]}}
                </td>
                <td class="text-right">
                    是否经总行批复同意：
                </td>
                <td>
                    {{{'01':'是','02':'否'}[bean.isAgree]}}
                </td>
            </tr>
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

        </table>

    </div>
</body>
</html>
