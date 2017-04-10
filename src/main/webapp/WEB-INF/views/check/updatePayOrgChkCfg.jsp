<%@ page import="com.oriental.check.commons.enums.ChkType" %>
<%@ page import="com.oriental.check.commons.enums.FileMethod" %>
<%@ page import="com.oriental.check.commons.enums.ChkCycle" %>
<%@ page import="com.oriental.check.commons.enums.RefundCheckFlag" %>
<%--
  User: 蒯越
  Date: 2016/5/9
  Time: 16:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">支付机构</td>
                <td class="col-xs-4">
                    {{vm.cached.COMANY_CODE[vm.item.companyCode]}}({{vm.item.companyCode}})
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">对账类型</td>
                <td class="col-xs-4">
                    <select type="text" ng-model="vm.item.chkType" class="{{vm.constant.inputClass}} ">
                        <%
                            for (ChkType e : ChkType.values()) {
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
                <td class="text-right col-xs-3">对账文件方式</td>
                <td class="col-xs-4">
                    <select type="text" ng-model="vm.item.fileMethod" class="{{vm.constant.inputClass}} ">
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
                    <input type="text" ng-model="vm.item.ftpHost" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
                <td class="text-right col-xs-3">FTP端口</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.ftpPort" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
                <td class="text-right col-xs-3">FTP用户名</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.ftpUser" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
                <td class="text-right col-xs-3">FTP密码</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.ftpPwd" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr ng-if="vm.item.fileMethod == 01 || vm.item.fileMethod == 02">
                <td class="text-right col-xs-3">FTP远程目录</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.ftpRemoteDir" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">对账文件时间</td>
                <td class="col-xs-4">
                    <select type="text" ng-model="vm.item.fileTime" class="{{vm.constant.inputClass}} ">
                        <option value="02:00" ng-if="vm.item.chkType == '03'">02:00</option>
                        <option value="02:30" ng-if="vm.item.chkType == '03'">02:30</option>
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
                        <option value="12:00" ng-if="vm.item.chkType == '03'">12:00</option>
                        <option value="12:30" ng-if="vm.item.chkType == '03'">12:30</option>
                        <option value="13:00" ng-if="vm.item.chkType == '03'">13:00</option>
                        <option value="13:30" ng-if="vm.item.chkType == '03'">13:30</option>
                        <option value="14:00" ng-if="vm.item.chkType == '03'">14:00</option>
                        <option value="14:30" ng-if="vm.item.chkType == '03'">14:30</option>
                        <option value="15:00" ng-if="vm.item.chkType == '03'">15:00</option>
                        <option value="15:30" ng-if="vm.item.chkType == '03'">15:30</option>
                        <option value="16:00" ng-if="vm.item.chkType == '03'">16:00</option>
                        <option value="16:30" ng-if="vm.item.chkType == '03'">16:30</option>
                        <option value="17:00" ng-if="vm.item.chkType == '03'">17:00</option>
                        <option value="17:30" ng-if="vm.item.chkType == '03'">17:30</option>
                        <option value="18:00" ng-if="vm.item.chkType == '03'">18:00</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">对账周期</td>
                <td class="col-xs-4">
                    <select type="text" ng-model="vm.item.chkCycle" class="{{vm.constant.inputClass}} ">
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
                <td class="text-right col-xs-3">退款对账标识</td>
                <td class="col-xs-4">
                    <select type="text" ng-model="vm.item.refundCheckFlag" class="{{vm.constant.inputClass}} ">
                        <%
                            for (RefundCheckFlag e : RefundCheckFlag.values()) {
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
    </form>
</div>
</body>
</html>
