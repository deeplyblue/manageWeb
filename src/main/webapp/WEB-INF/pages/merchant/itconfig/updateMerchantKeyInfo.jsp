<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <div>{{vm.cached.MERCHANT_CODE[vm.item.companyCode]}}[{{vm.item.companyCode}}]</div>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    数据KEY：
                </td>
                <td>

                    <input type="text" ng-model="vm.item.dataKey" ng-init="vm.item.dataKey='*******'" class="{{vm.constant.inputClass}}" required name="dataKey"/>
                </td>
            </tr>

            <%--<tr>--%>
            <%--<td class="text-right">--%>
            <%--交易KEY：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<input type="text" ng-model="vm.item.transPwd" class="{{vm.constant.inputClass}}" required name="transPwd" />--%>
            <%--</td>--%>
            <%--</tr>--%>


            <tr>
                <td class="text-right">
                    数据加密KEY有效期：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.dataKeyValidDate" required  name="dataKeyValidDate"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    信息验证类型：
                </td>
                <td>
                    <select ng-model="vm.item.encType" class="{{vm.constant.inputClass}} " required name="encType"
                            ng-options="temp.key as temp.value for temp in vm.cached.ENCTYPE">
                        <option value="">请选择类型</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    上传公钥文件：
                </td>
                <td>
                    <input nv-file-select=""  type="file" options="vm.fileItems[0]" uploader="vm.fileUploader" required name="rsaPublicKey"/>


                </td>
            </tr>
            <tr>
                <td class="text-right">
                    RSA公钥有效期：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.rsaKeyValidDate" required name="rsaKeyValidDate" />
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