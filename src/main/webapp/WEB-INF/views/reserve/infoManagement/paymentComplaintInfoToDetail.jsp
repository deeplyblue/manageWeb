<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2017/1/5
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
        <table class="table table-bordered">

            <tr>
                <td class="text-right col-xs-3">
                    投诉类型：
                </td>
                <td class="col-xs-4">
                    {{vm.cached.COMPLAINT_TYPE_FOR_RESERVE[vm.item.accuseType]}}
                </td>

                <td class="text-right col-xs-3">
                    投诉时间：
                </td>
                <td class="col-xs-4">
                    {{vm.item.accuseDate | date:'yyyy-MM-dd'}}
                </td>

            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    投诉人：
                </td>
                <td class="col-xs-4">
                    {{vm.item.accuse}}
                </td>

                <td class="text-right col-xs-3">
                    投诉电话：
                </td>
                <td class="col-xs-4">
                    {{vm.item.complainTel}}
                </td>
            </tr>

            <tr>
                <td class="text-right col-xs-3">
                    投诉传真：
                </td>
                <td class="col-xs-4">
                    {{vm.item.accuseFax}}
                </td>

                <td class="text-right col-xs-3">
                    邮编：
                </td>
                <td class="col-xs-4">
                    {{vm.item.zipCode}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    投诉信息：
                </td>
                <td cols=40 rows=4>
                    {{vm.item.accuseInfo}}
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
                    {{vm.item.handleRes}}
                        </textarea>
                </td>

                <td class="text-right col-xs-3">
                    处理时间：
                </td>
                <td class="col-xs-4">
                    {{vm.item.handleTime | date:'yyyy-MM-dd'}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    处理人：
                </td>
                <td class="col-xs-4">
                    {{vm.item.handle}}
                </td>

                <td class="text-right col-xs-3">
                    处理人电话：
                </td>
                <td class="col-xs-4">
                    {{vm.item.handleTel}}
                </td>
            </tr>

        </table>
</div>
</body>
</html>