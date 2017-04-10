
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/updateBusiInformation.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="更新商户信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/bank/busi/search" class="form-inline" autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    商户编号：
                </td>
                <td>
                    <input type="text"   ng-model="vm.queryBean.merchantNO" class="form-control" />
                </td>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                <select class="{{vm.constant.inputClass}}"  ng-model="vm.queryBean.operateType"
                        ng-options="key as value for (key,value) in vm.cached.BUSINESS_STATUS_FOR_RESERVE">
                    <option value="">请选择</option>
                </select>
            </td>
            <tr class="text-center">
                <td colspan="4" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">更新</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>


    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
