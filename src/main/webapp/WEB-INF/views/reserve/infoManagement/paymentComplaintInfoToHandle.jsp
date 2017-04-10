<%--
  Created by IntelliJ IDEA.
  User: hz
  Date: 2016/12/22
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form>
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
                    <textarea cols=40 rows=4 ng-model="vm.item.handleRes" class="{{vm.constant.inputClass}} ">
                        </textarea>
                </td>
            </tr>


        </table>
    </form>
</div>
</body>
</html>