<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2017/1/6
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form ng-repeat="bean in vm.item.list track by $index">
        <table class="table table-bordered" >

            <tr>
                <td class="text-right col-xs-3">
                    投诉类型：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.COMPLAINT_TYPE_FOR_RESERVE[bean.accuseType]}}
                </td>

                <td class="text-right col-xs-3">
                    投诉时间：
                </td>
                <td class="col-xs-4">
                    {{bean.accuseDate | date:'yyyy-MM-dd'}}
                </td>

            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    投诉人：
                </td>
                <td class="col-xs-4">
                    {{bean.accuse}}
                </td>

                <td class="text-right col-xs-3">
                    投诉电话：
                </td>
                <td class="col-xs-4">
                    {{bean.complainTel}}
                </td>
            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    投诉传真：
                </td>
                <td class="col-xs-4">
                    {{bean.accuseFax}}
                </td>

                <td class="text-right col-xs-3">
                    邮编：
                </td>
                <td class="col-xs-4">
                    {{bean.zipCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    投诉信息：
                </td>
                <td cols=40 rows=4>
                    {{bean.accuseInfo}}
                    </textarea>
                </td>
            </tr>
        </table>

        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    处理结果：
                </td>

                <td class="col-xs-4">
                    {{bean.handleRes}}
                    </textarea>
                </td>

                <td class="text-right col-xs-3">
                    处理时间：
                </td>
                <td class="col-xs-4">
                    {{bean.handleTime | date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    处理人：
                </td>
                <td class="col-xs-4">
                    {{bean.handle}}
                </td>

                <td class="text-right col-xs-3">
                    处理人电话：
                </td>
                <td class="col-xs-4">
                    {{bean.handleTel}}
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>