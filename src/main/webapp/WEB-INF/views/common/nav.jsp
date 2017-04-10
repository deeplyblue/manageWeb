<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 11:07
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div growl></div>
<%--普通的弹出框--%>
<script type="text/ng-template" id="myModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.modalTitle}}</h3>
    </div>
    <div class="modal-body" ng-include="vm.modalBody">

    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="button" ng-click="save()">save</button>
        <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
    </div>
</script>
<%--不带保存的弹出框--%>
<script type="text/ng-template" id="myModalNoSave.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.modalTitle}}</h3>
    </div>
    <div class="modal-body" ng-include="vm.modalBody">

    </div>
    <div class="modal-footer">
        <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
    </div>
</script>

<script type="text/ng-template" id="myModalNoFooter.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.modalTitle}}</h3>
    </div>
    <div class="modal-body" ng-include="vm.modalBody">

    </div>
</script>

<script type="text/ng-template" id="alertModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.msg}}</h3>
    </div>

    <div class="modal-footer">
        <button class="btn btn-warning" type="button" ng-click="ok()">OK</button>
    </div>
</script>

<script type="text/ng-template" id="confirmModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.msg}}</h3>
    </div>

    <div class="modal-footer">
        <button class="btn btn-primary" type="button" ng-click="confirm()">确认</button>
        <button class="btn btn-warning" type="button" ng-click="cancel()">取消</button>
    </div>
</script>

<script type="text/ng-template" id="promptModalContent.html">
    <div class="modal-header">
        <h3 class="modal-title">{{vm.msg}}</h3>
    </div>
    <div class="modal-body">
        <input type="textarea" ng-model="vm.result" placeholder="请输入" class="form-control" rows="2">
    </div>
    <div class="modal-footer">
        <button class="btn btn-warning" type="button" ng-click="save()">保存</button>
    </div>
</script>

<script type="text/ng-template" id="treeView.html">
    <ul class="tree-view">
        <li ng-repeat="item in treeData" ng-include="treeItem.html"></li>
    </ul>
</script>

<script type="text/ng-template" id="treeItem.html">
    <i ng-click="itemExpended(item, $event);" class=""></i>

    <input type="checkbox" ng-model="item.$$isChecked" class="check-box" ng-if="canChecked"
           ng-change="warpCallback('itemCheckedChanged', item, $event)">

    <span class='text-field' ng-click="warpCallback('itemClicked', item, $event);"></span>
    <ul ng-if="!isLeaf(item)" ng-show="item.$$isExpend">
        <li ng-repeat="item in item.children" ng-include="'/treeItem.html'">
        </li>
    </ul>

</script>

<script type="text/ng-template" id="fileUploader.html">
    <tr>
        <td class="text-right">
            上传文件
        </td>
        <td colspan="4">
            <input nv-file-select="" type="file" uploader="vm.fileUploader"/>
        </td>
    </tr>
    <tr>
        <h4>当前队列 ({{ vm.fileUploader.queue.length }})</h4>
        <th width="40%">文件名</th>
        <th ng-show="vm.fileUploader.isHTML5">大小</th>
        <th ng-show="vm.fileUploader.isHTML5">进度</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    <tr ng-repeat="item in vm.fileUploader.queue">
        <td><strong>{{ item.file.name }}</strong></td>
        <td ng-show="vm.fileUploader.isHTML5" nowrap>{{ item.file.size/1024|number:2 }} KB</td>
        <td ng-show="vm.fileUploader.isHTML5">
            <div class="progress" style="margin-bottom: 0;">
                <div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
            </div>
        </td>
        <td class="text-center">
            <span ng-show="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span>
            <span ng-show="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span>
            <span ng-show="item.isError"><i class="glyphicon glyphicon-remove"></i></span>
        </td>
        <td nowrap>
            <button type="button" class="btn btn-success btn-xs" ng-click="item.upload()"
                    ng-disabled="item.isReady || item.isUploading || item.isSuccess">
                <span class="glyphicon glyphicon-upload"></span>
            </button>
            <button type="button" class="btn btn-warning btn-xs" ng-click="item.cancel()"
                    ng-disabled="!item.isUploading">
                <span class="glyphicon glyphicon-ban-circle"></span>
            </button>
            <button type="button" class="btn btn-danger btn-xs" ng-click="item.remove()">
                <span class="glyphicon glyphicon-trash"></span>
            </button>
        </td>
    </tr>
</script>

</body>
</html>
