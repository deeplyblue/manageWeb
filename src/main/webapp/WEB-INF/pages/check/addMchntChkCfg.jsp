<%@ page import="com.oriental.check.commons.enums.ChkType" %>
<%@ page import="com.oriental.check.commons.enums.FileMethod" %>
<%@ page import="com.oriental.check.commons.enums.ChkCycle" %>
<%@ page import="com.oriental.check.commons.enums.DependOnBankChkFlag" %>
<%@ page import="com.oriental.check.commons.enums.BusiPlatformOperateImpls" %>
<%--
  User: 蒯越
  Date: 2016/5/17
  Time: 16:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div common-modal-form>
    <%--<form name="addMchntChkCfg" novalidate w5c-form-validate>--%>
    <table class="table table-bordered">
        <tr>
            <td class="text-right col-xs-3">商户</td>
            <td class="col-xs-4">
                <input type="text" class="{{vm.constant.inputClass}}" ng-model="vm.item.companyCode"
                       class="{{vm.constant.inputClass}}"
                       uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:6"
                       required name="companyCode"/>

            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">对账类型</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.chkType" ng-init="vm.item.chkType='01'"
                        class="{{vm.constant.inputClass}} ">
                    <%
                        for (ChkType chkType : ChkType.values()) {
                            if (!"03".equals(chkType.getCode())) {
                    %>
                    <option value="<%=chkType.getCode()%>">
                        <%=chkType.getDesc()%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">对账文件方式</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.fileMethod" ng-init="vm.item.fileMethod='02'"
                        class="{{vm.constant.inputClass}} " required name="fileMethod" w5c-dynamic-element>
                    <%
                        for (FileMethod e : FileMethod.values()) {
                    %>
                    <option value="<%=e.getCode()%>">
                        <%=e.getDesc()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
            <td class="text-right col-xs-3">FTP地址</td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.ftpHost" class="{{vm.constant.inputClass}} "
                       ng-pattern="/^((25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))$/"
                       placeholder="输入格式：1.1.1.1" required name="ftpHost" w5c-dynamic-element>
            </td>
        </tr>
        <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
            <td class="text-right col-xs-3">FTP端口</td>
            <td class="col-xs-4">
                <input type="number" ng-model="vm.item.ftpPort" class="{{vm.constant.inputClass}} " required
                       name="ftpPort" w5c-dynamic-element>
            </td>
        </tr>
        <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
            <td class="text-right col-xs-3">FTP用户名</td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.ftpUser" class="{{vm.constant.inputClass}} " required
                       name="ftpUser" w5c-dynamic-element>
            </td>
        </tr>
        <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
            <td class="text-right col-xs-3">FTP密码</td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.ftpPwd" class="{{vm.constant.inputClass}} " required name="ftpPwd" w5c-dynamic-element>
            </td>
        </tr>
        <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
            <td class="text-right col-xs-3">FTP远程目录</td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.ftpRemoteDir" class="{{vm.constant.inputClass}} " required
                       name="ftpRemoteDir" w5c-dynamic-element>
            </td>
        </tr>
        <tr ng-if="vm.item.chkType == 01">
            <td class="text-right col-xs-3">对账文件模板</td>
            <td class="col-xs-4">
                <input type="text" ng-model="vm.item.fileTemplateNo" class="{{vm.constant.inputClass}} "
                       ng-init="vm.item.fileTemplateNo='1000000'"
                       uib-typeahead="item.key as item.value for item in vm.cached.FILE_TEMPLATE_NO | filter:$viewValue | limitTo:6">
            </td>
        </tr>
        <tr ng-if="vm.item.chkType == 02">
            <td class="text-right col-xs-3">对账文件时间</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.fileTime" class="{{vm.constant.inputClass}} ">
                    <option value="03:00">03:00</option>
                    <option value="03:30">03:30</option>
                    <option value="04:00">04:00</option>
                    <option value="04:30">04:30</option>
                    <option value="05:00">05:00</option>
                    <option value="05:30">05:30</option>
                    <option value="06:00">06:00</option>
                    <option value="06:30">06:30</option>
                    <option value="07:00">07:00</option>
                    <option value="07:30">07:30</option>
                    <option value="08:00">08:00</option>
                    <option value="08:30">08:30</option>
                    <option value="09:00">09:00</option>
                    <option value="09:30">09:30</option>
                    <option value="10:00">10:00</option>
                    <option value="10:30">10:30</option>
                    <option value="11:00">11:00</option>
                    <option value="11:30">11:30</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">对账周期</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.chkCycle" ng-init="vm.item.chkCycle='1'"
                        class="{{vm.constant.inputClass}} ">
                    <%
                        for (ChkCycle e : ChkCycle.values()) {
                    %>
                    <option value="<%=e.getCode()%>">
                        <%=e.getDesc()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">操作实现类</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.operateImplClass" class="{{vm.constant.inputClass}} ">
                    <%
                        for (BusiPlatformOperateImpls e : BusiPlatformOperateImpls.values()) {
                    %>
                    <option value="<%=e.getCode()%>">
                        <%=e.getDesc()%> : <%=e.getCode()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="text-right col-xs-3">依赖银行对账标识</td>
            <td class="col-xs-4">
                <select type="text" ng-model="vm.item.dependOnBankChkFlag" ng-init="vm.item.dependOnBankChkFlag='1'"
                        class="{{vm.constant.inputClass}} ">
                    <%
                        for (DependOnBankChkFlag e : DependOnBankChkFlag.values()) {
                    %>
                    <option value="<%=e.getCode()%>">
                        <%=e.getDesc()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </td>
        </tr>
    </table>
    <%--</form>--%>
</div>
</body>
</html>
