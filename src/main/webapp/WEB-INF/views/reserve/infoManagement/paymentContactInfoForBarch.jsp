<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2017/1/9
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off" ng-repeat="bean in vm.item.list track by $index">
        <table class="table table-bordered" >

            <tr>
                <td class="text-right col-xs-3">
                    联系人姓名：
                </td>
                <td class="col-xs-4">
                    {{bean.connName}}
                </td>
                <td class="text-right col-xs-3">
                    归属机构编号：
                </td>
                <td class="col-xs-4">
                    {{bean.ownOrgNO}}
                </td>
            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    职务：
                </td>
                <td class="col-xs-4">
                    {{bean.rule}}
                </td>
                <td class="text-right col-xs-3">
                    办公电话：
                </td>
                <td class="col-xs-4">
                    {{bean.tel}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    手机：
                </td>
                <td class="col-xs-4">
                    {{bean.mobile}}
                </td>
                <td class="text-right col-xs-3">
                    邮件：
                </td>
                <td class="col-xs-4">
                    {{bean.email}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    备注：
                </td>
                <td class="col-xs-4">
                    {{bean.remarks}}
                </td>
                <td class="text-right col-xs-3">
                    主要联系人：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.MAIN_CONN_FOR_RESERVE[bean.mainConn]}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    离职标示：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.OFF_STATUS_FOR_RESERVE[bean.offStatus]}}
                </td>
                <td class="text-right col-xs-3">
                    操作类型：
                </td>
                <td class="col-xs-4">
                    {{{'00':'新增','01':'修改','02':'删除'}[bean.operateType]}}
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
