<%--
  Created by IntelliJ IDEA.
  User: zhangxinhai
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/settlement/clear/redo/coercePayClearDetailApp-83f007b4fc.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="机构强制结算"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/clear/redo/coercePayClearDetail/notarize"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right col-md-2">
                    机构代码
                </td>
                <td class="col-md-3">
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6"
                           typeahead-show-hint="true"/>
                </td>
                <td class="text-right">
                    结算日期
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyyMMdd"
                           ng-model="vm.queryBean.clrDate"

                            required
                           required/>
                </td>
                <td class="text-right">
                    结算类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.clrType" class="form-control"
                            ng-options="key as value for (key,value) in {'02':'本金结算','03':'手续费结算'}">
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">操作</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>


</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
