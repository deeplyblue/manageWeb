<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/16
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/MailInfoApp-c94220142d.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询邮寄信息"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/mailInfo/search" autocomplete="off">
        <table table-form>
            <tr>
                <td align="center">

                    支付机构：
                    <input ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                    </input>
                </td>

            </tr>


            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="org-mailInfo_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="org-mailInfo_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="org-mailInfo_delete">
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>支付机构</th>
                <th>姓名</th>
                <th>邮寄地址  </th>
                <th>发票抬头  </th>
                <th>商户归属地  </th>
                <th>税务登记证纳税人名称 </th>
                <th>纳税人识别号  </th>
                <th>税务登记证地址 </th>
                <th>财务联系电话 </th>
                <th>一般纳税人开户行  </th>
                <th>一般纳税人银行账户   </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td> {{vm.cached.COMANY_CODE[bean.companyCode]}}{{bean.companyCode}}</td>
                <td>{{bean.username}}</td>
                <td>{{bean.postAddr}}</td>
                <td>{{bean.invoiceHead}}</td>
                <td>{{bean.areaCode}}{{bean.cityCode}}</td>
                <td>{{bean.taxpayerName}}</td>
                <td>{{bean.taxpayerNo}}</td>
                <td>{{bean.certificateAddr}}</td>
                <td>{{bean.financialPhone}}</td>
                <td>{{bean.taxpayerBankCode}}</td>
                <td>{{bean.taxpayerBankAccount}}</td>
            </tr>
            </tbody>
            <tfoot>
            <tr>


            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
 <core:import url="../common/nav.jsp"/>
</body>
</html>
