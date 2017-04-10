<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <form id="uploadForm" name="uploadForm" action="#" class="form-inline">
        <table class="table table-bordered table-condensed">
            <thead>
            <tr>
                <td class="text-right">
                    操作
                </td>
                <td colspan="5">
                    <input type="button" value="下载模板" class="btn btn-default" ng-click="vm.downLoadTemplate()"/>
                    &nbsp;
                    <button type="button" ng-click="vm.uploadFile()" class="btn btn-default">提交</button>
                </td>
            </tr>
            </thead>
            <tbody ng-include="'fileUploader.html'" class="well"></tbody>
        </table>
    </form>

    <div>
        <h4>
            说明：<br>
            第一步：下载模板到本地<br>
            第二步：按模板文件名格式，<span style="color: red">修改文件名</span><br>
            第三步：在模板文件中填入<span style="color: red">用户ID、姓名、手机号、中奖等级、金额</span><br>
            第四步：将填好的模板进行上传<br>
            第五步：在查询页面查看信息<br>
        </h4>
    </div>
</div>
</body>
</html>
