<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <form id="uploadForm" name="uploadForm" action="#" class="form-inline" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <thead>
            <tr>
                <td class="text-right">
                    操作
                </td>
                <td colspan="5">
                    <button type="button" ng-click="vm.confirmUploadFile()" class="btn btn-default">确认提交</button>
                </td>
            </tr>
            </thead>
            <tbody ng-include="'fileUploader.html'" class="well"></tbody>
        </table>
    </form>

    <div>
        <h4>
            说明：<br>
            一：上传确认后的文件,<span style="color: red">不要修改原文件名</span><br>
            二：文件比对，只比对<span style="color: red">用户ID、金额</span>字段，其余信息<span style="color: red">以用户绑卡信息</span>为准
        </h4>
    </div>
</div>
</body>
</html>
