<%--
  Created by IntelliJ IDEA.
  User: wangjun
  Date: 2016/5/16
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/merchant/settleManage/ContranctApp-4c814604e1.js"></script>
</head>


<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询合同信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/merchantContaract/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}">--%>
                        <input type="text" ng-model="vm.queryBean.companyCode" class="{{vm.constant.inputClass}}"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10">

                </td>
                <td class="text-right">
                    合同编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.contCode" class="{{vm.constant.inputClass}}">
                </td>


            </tr>
            <tr>
                <td class="text-right">
                    审核状态:
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}" ng-options="">--%>

                    <select ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'01':'未处理','02':'审核成功','03':'审核失败','04':'已下架','05':'已上架'}">
                        <option value="">请选择</option>
                    </select>
                </td>
                <%--<td class="text-right">--%>
                    <%--生效时间：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--&lt;%&ndash;<input type="text" ng-model="vm.queryBean.contBgnDate" class="{{vm.constant.inputClass}}">&ndash;%&gt;--%>
                    <%--<input type="text" class="{{vm.constant.inputClass}}"uib-datepicker-popup="yyyyMMdd"--%>
                           <%--ng-model="vm.queryBean.contBgnDate"/>--%>
                <%--</td>--%>
                <td class="text-right">
                    合同结束时间:
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.contEndDate" class="{{vm.constant.inputClass}}">--%>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyyMMdd"
                           ng-model="vm.queryBean.contEndDate" />
                </td>

            </tr>

            <tr align="center">
                <td colspan="6">
<%--<shiro:hasPermission name="merchant-contaract_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
  <%--  </shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <shiro:hasPermission name="merchant-contaract_add">
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            </shiro:hasPermission>
            <shiro:hasPermission name="merchant-contaract_update">
                <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>商户</th>
                <th>合同编号</th>
                <th>合同名称  </th>
                <th>审核状态 </th>
                <th>合同签订日期  </th>
                <th>合同生效日期</th>
                <th>合同结束日期  </th>
                <th>签约机构 </th>
                <th>企业代码 </th>
                <th>操作 </th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td> {{vm.cached.COMANY_CODE[bean.companyCode]}}{{bean.companyCode}}</td>
                <td>{{bean.contCode}}</td>
                <td>{{bean.contName}}</td>
                <td>{{{'01':'未处理','02':'审核成功','03':'审核失败','04':'已下架','05':'已上架'}[bean.auditStatus]}}</td>
                <td>{{bean.contSignedDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.contBgnDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.contEndDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.signedOrgCode}}</td>
                <td>{{bean.enterpriseCode}}</td>
                <td>
<shiro:hasPermission name="merchant-contaract_audit">
                    <button ng-if="bean.auditStatus=='01'" type="button" ng-click="vm.updateItemEnableFlag(bean,'02')">审核成功</button>
</shiro:hasPermission>
 <shiro:hasPermission name="merchant-contaract_fail">
                    <button ng-if="bean.auditStatus=='01'" type="button" ng-click="vm.updateItemEnableFlag(bean,'03')">审核失败</button>
</shiro:hasPermission>
<shiro:hasPermission name="merchant-contaract_on">
                    <button ng-if="bean.auditStatus=='02'" type="button" ng-click="vm.updateItemEnableFlag(bean,'05')">上架</button>
</shiro:hasPermission>
<shiro:hasPermission name="merchant-contaract_off">
                    <button ng-if="bean.auditStatus=='02'" type="button" ng-click="vm.updateItemEnableFlag(bean,'04')">下架</button>
</shiro:hasPermission>
<shiro:hasPermission name="merchant-contaract_down">
                    <button type="button" ng-click="vm.downEnclosure(bean)">下载全部附件</button>
</shiro:hasPermission>
                </td>
            </tr>
            </tbody>

        </table>
    </div>
    <core:import url="../../common/pageFoot.jsp"/>
</div>
 <core:import url="../../common/nav.jsp"/>
</body>
</html>
