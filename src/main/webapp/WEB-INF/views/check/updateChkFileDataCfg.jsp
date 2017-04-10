<%@ page import="com.oriental.check.commons.enums.ChkFileDataField" %>
<%--
  User: 蒯越
  Date: 2016/5/17
  Time: 16:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">对账文件模板号</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.templateNo" class="{{vm.constant.inputClass}} " ng-show="false">
                    {{vm.item.templateNo}}
                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">对账文件模板名称</td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.templateName" class="{{vm.constant.inputClass}} " ng-show="false">
                    {{vm.item.templateName}}
                </td>
            </tr>
        </table>
        <table class="table table-bordered">
            <tr>
                <td colspan="3" style="text-align: center">对账文件数据配置</td>
            </tr>
            <tr>
                <td>字段</td>
                <td>排列序号</td>
                <td>操作</td>
            </tr>
            <tr ng-repeat="bean in vm.item.fieldDTOList track by $index">
                <td>
                    <select ng-model="bean.field" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (ChkFileDataField e : ChkFileDataField.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
                <td>
                    <input type="text" ng-model="bean.seqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td>
                    <button ng-click="vm.addChkFileDataField($index)" title="增加">增加</button>
                    <button ng-click="vm.deleteChkFileDataField($index)" title="删除">删除</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
