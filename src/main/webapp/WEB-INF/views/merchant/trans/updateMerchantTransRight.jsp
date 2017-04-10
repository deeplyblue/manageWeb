<%@ page import="com.oriental.manage.core.enums.CompanyType" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link href="${ctx}/css/tree/zTreeStyle.css" rel="stylesheet">
    <script src="${ctx}/js/tree/jquery.ztree.core-3.5.js"></script>
    <script src="${ctx}/js/tree/jquery.ztree.excheck-3.5.js"></script>
</head>

<body>

<div common-modal-form>

        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    <div>{{vm.item.MERCHANT_CODE[vm.item.merchantCode]}}[{{vm.item.merchantCode}}]</div>
                    <input type="text" ng-model="vm.item.merchantCode" class="form-control" style="display: none;" />
                    <input type="text" ng-hide="true" ng-model="vm.item.companyType" ng-init="vm.item.companyType='<%=CompanyType.MERCHANT.getCode()%>'">

                </td>
            </tr>

            <tr>
                <td class="text-right">
                    渠道/订单类型：
                </td>
                <td>
                    <div class="zTreeDemoBackground col-md-offset-1">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                </td>
            </tr>

        </table>

</div>
</body>
</html>