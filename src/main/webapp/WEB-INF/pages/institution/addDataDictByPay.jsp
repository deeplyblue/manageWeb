<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/4
  Time: 14:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ include file=".././common/taglibs.jsp" %>--%>
<%--<script src="${ctx}/build/js/views/institution/datadictApp-e22206a4c9.js"></script>--%>
<script language="javascript">
    function doChange(){
        var value = $.trim($("select[name='vm.item.itemVal']").find("ng-options:selected").text());
        $("input[name='vm.item.itemDesc']").val(value);
        alert($("input[name='vm.item.itemDesc']").val(value))

    }
</script>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    支付机构交易名称：
                </td>
                <td class="col-xs-4">
                    <%--<input type="text" ng-model="vm.item.itemVal" class="{{vm.constant.inputClass}} ">--%>
                    <select ng-model="vm.item.itemVal" class="{{vm.constant.inputClass}}"
                            ng-options="item.key as item.value for item in vm.cached.TRANS_CODE_ALL" >
                        <option value="">请选择</option>
                    </select>

                </td>
            </tr>
            <tr>
                <td class="text-right col-xs-3">
                    分账交易代码：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.spare1" class="{{vm.constant.inputClass}} ">
                </td>
            </tr>
            <input type="text" ng-model="vm.item.colNameCn" ng-init="vm.item.colNameCn = '支付机构交易代码'" ng-show="false" />
            <input type="text" ng-model="vm.item.itemType" ng-init="vm.item.itemType = '02'" ng-show="false" />
        </table>
    </form>
</div>
</body>
</html>
