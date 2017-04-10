<%--
  User: shulw
  Date: 2016/12/21
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<div >
    <form autocomplete="off">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td class="text-right">
                    报表类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reportType" class="{{vm.constant.inputClass}}"
                            ng-options="item.reportType as item.itemDesc for item in vm.cached.RESERVE_REPORT_TYPE_LIST ">
                        <option>请选择</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    报表年份：
                </td>
                <td>
                    <input type="text" class="form-control" uib-datepicker-popup="yyyy"
                           ng-model="vm.queryBean.reportYear"
                           required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    报表季度：
                </td>
                <td>
                    <select ng-model="vm.queryBean.reportQuarter" class="{{vm.constant.inputClass}}"
                            ng-options="status.value as status.name for status in reserveReportQuarter">
                    </select>
                </td>
            </tr>

            </thead>
            <tbody ng-include="'fileUploader.html'" class="well"></tbody>
            <tbody>
            <tr>
                <button type="button" ng-click="vm.uploadFile()" class="btn btn-default">确认提交</button>
            </tr>
            </tbody>
        </table>
    </form>
    <div>报表模版下载：<a href="${ctx}/manage/reserve/reserveReport/downReport">季报</a></div>
</div>
</body>
</html>
