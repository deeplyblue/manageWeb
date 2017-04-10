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
                    商户代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}" readonly="readonly" required name="companyCode">

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contCode" class="{{vm.constant.inputClass}}" readonly="readonly" required name="contCode">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同名称 ：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contName" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同概要：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.contSummary" class="{{vm.constant.inputClass}}">

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同签订日期：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.contSignedDate"class="{{vm.constant.inputClass}}" />--%>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.contSignedDate" required  name="contSignedDate"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同生效日期：
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.contBgnDate" required  name="contBgnDate"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同结束日期：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.item.contEndDate"class="{{vm.constant.inputClass}}" />--%>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.contEndDate" required name="contEndDate" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    签约机构：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.signedOrgCode"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    企业代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.item.enterpriseCode"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证编号：
                </td>
                <td>
                    <input ng-model="vm.item.taxRegisterId"class="{{vm.constant.inputClass}}" />

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    <input ng-model="vm.item.bizLicenseNo"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织机构代码证编号：
                </td>
                <td>
                    <input ng-model="vm.item.organizationCodeId"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    纳税人证书到期日：
                </td>
                <td>
                    <input uib-datepicker-popup="yyyy-MM-dd"  ng-model="vm.item.payerCertEndDate"class="{{vm.constant.inputClass}}" />

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证到期日：
                </td>
                <td>
                    <input uib-datepicker-popup="yyyy-MM-dd"  ng-model="vm.item.taxRegisterEndDate"class="{{vm.constant.inputClass}}" />

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照到期日：
                </td>
                <td>
                    <input uib-datepicker-popup="yyyy-MM-dd"  ng-model="vm.item.bizLicenseEndDate"class="{{vm.constant.inputClass}}" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    合同附件：
                </td>
                <td>
                    <%--<input type="file" ng-model="vm.item.dfsContAttach"class="{{vm.constant.inputClass}}" />--%>
                    <%--@TODO 修改时可展示图片，若有难度，可以先提供a标签下载文件--%>
                    <input nv-file-select="" type="file" options="vm.fileItems[0]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsContAttach!=null&&vm.item.dfsContAttach!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo.dfsGroupName}}">
                        {{bean.localFilename}}下载</a>
                    <p ng-if="vm.item.dfsContAttach==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    开户行许可证：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[1]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsOpenBankCert!=null&&vm.item.dfsOpenBankCert!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo1.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo1.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo1.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsOpenBankCert==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    一般纳税人证书：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[2]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsRatePayerCert!=null&&vm.item.dfsRatePayerCert!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo2.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo2.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo2.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsRatePayerCert==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    税务登记证：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[3]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsTaxRegisterCert!=null&&vm.item.dfsTaxRegisterCert!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo3.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo3.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo3.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsTaxRegisterCert==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    营业执照：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[4]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsBizLicenseCert!=null&&vm.item.dfsBizLicenseCert!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo4.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo4.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo4.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsBizLicenseCert==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    组织机构代码证：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[5]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsOrganizationCodeCert!=null&&vm.item.dfsOrganizationCodeCert!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo5.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo5.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo5.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsOrganizationCodeCert==null">请上传文件</p>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行基本信息表：
                </td>
                <td>
                    <input nv-file-select="" type="file" options="vm.fileItems[6]" uploader="vm.fileUploader"/>
                    <a ng-if="vm.item.dfsBankFile!=null&&vm.item.dfsBankFile!=''" href="${ctx}/manage/base/dfsFileInfo/download?localFilename={{vm.item.dfsInfo6.localFilename}}
                        &dfsFullFilename={{vm.item.dfsInfo6.dfsFullFilename}}
                        &dfsGroupname={{vm.item.dfsInfo6.dfsGroupName}}">下载</a>
                    <p ng-if="vm.item.dfsBankFile==null">请上传文件</p>
                    <input type="text" ng-model="vm.item.auditStatus" ng-hide="true">
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td class="text-right">--%>
            <%--审核：--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<select ng-model="vm.item.auditStatus" class="{{vm.constant.inputClass}}"--%>
            <%--ng-options="key as value for (key,value) in {'01':'未处理','02':'审核成功','03':'审核失败','04':'已下架','05':'已上架'}">--%>

            <%--</select>--%>
            <%--</td>--%>
            <%--</tr>--%>
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
