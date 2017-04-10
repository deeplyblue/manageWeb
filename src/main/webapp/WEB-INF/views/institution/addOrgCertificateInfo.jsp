<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/9
  Time: 09:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构：
                </td>
                <td class="col-xs-4">
                        <input ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}}" required name="orgCode"
                               uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE | filter:$viewValue | limitTo:6">
                        </input>

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    证书版本号：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.version" class="{{vm.constant.inputClass}} " >
                </td>
            </tr>
            <tr>
            <td class="text-right">
                证书生效日期：
            </td>
            <td>
                <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                       ng-model="vm.item.effectData"
                       required name="effectData"/>
            </td></tr>
            <tr>
            <td class="text-right">
                证书过期日期：
            </td>
            <td>
                <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                       ng-model="vm.item.expiryData"
                       required name="expiryData"/>
            </td>
            </tr>
            <tr>
                <td class="text-right">
                    上传机构证书：
                </td>
                <td>
                    <input nv-file-select=""  type="file" options="vm.fileItems[0]" uploader="vm.fileUploader" required name="localFilename"/>

                </td>
            </tr>
            <tr>
                <td><h4>当前队列 ({{ vm.fileUploader.queue.length }})</h4></td>
            </tr>
            <tr>
                <th width="25%">文件名</th>
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
        </table>
</div>
</body>
</html>
