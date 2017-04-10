<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: JinXin
  Date: 2016/12/21
  查询支付机构董高监信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/paymentManagerInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="支付机构董高监信息管理 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/paymentManagerInfo/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgNo" class="{{vm.constant.inputClass}}"/>
                        <%--<input type="text" ng-model="vm.queryBean.orgNo"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"--%>
                               <%--typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>--%>

                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgName" class="{{vm.constant.inputClass}}"/>
                        <%--<input type="text" ng-model="vm.queryBean.orgName"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"--%>
                               <%--typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>--%>

                </td>
                <td class="text-right">
                    身份类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.type" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'01':'董事','02':'监事','03':'高管'}">
                        <option value="">请选择类型</option>
                    </select>
                </td>
            </tr>
            <tr>

                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.name" class="{{vm.constant.inputClass}}" />
                </td>
                <td class="text-right">
                    是否删除：
                </td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'0':'否','1':'是'}">
                        <option value="">请选择是否删除</option>
                    </select>
                </td>
                <td class="text-right">
                    操作类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.operateType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'00':'新增','01':'修改','02':'删除'}">
                        <option value="">请选择操作类型</option>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6" class="textCenter">
<%--<shiro:hasPermission name="merchant-contacts_search">--%>
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
<%--</shiro:hasPermission>--%>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
            <%--<shiro:hasPermission name="merchant-contacts_add">--%>
                <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
            <%--</shiro:hasPermission>--%>

            <%--<shiro:hasPermission name="merchant-contacts_update">--%>
                    <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
            <%--</shiro:hasPermission>--%>

            <%--<shiro:hasPermission name="institution-contact_delete">--%>
                <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
            <%--</shiro:hasPermission>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>机构编号</th>
                <th>机构名称</th>
                <th>姓名</th>
                <th>身份类型</th>
                <th>职务</th>
                <th>办公电话</th>
                <th>离职标识</th>
                <th>审核状态</th>
                <th>是否人行数据</th>
                <th>操作类型</th>
                <th>是否删除</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list ">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.msgId)" ng-disabled="bean.centerStatus =='01' ||bean.deleteFlag =='1'||(bean.operateType=='02'&&bean.auditStatus=='08')">
                </td>
                <td>{{bean.orgNo}}</td>
                <td>{{bean.orgName}}</td>
                <td>{{bean.name}}</td>
                <td>{{{'01':'董事','02':'监事','03':'高管'}[bean.type]}}</td>
                <td>{{bean.rule}}</td>
                <td>{{bean.tel}}</td>
                <td>{{{'01':'在职','02':'离职中','03':'已离职'}[bean.offStatus]}}</td>
                <td>{{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.auditStatus]}}</td>
                <td>{{{'01':'是','00':'否'}[bean.centerStatus]}}</td>
                <td>{{{'00':'新增','01':'修改','02':'删除'}[bean.operateType]}}</td>
                <td>{{{'0':'否','1':'是'}[bean.deleteFlag]}}</td>
                <td>
<shiro:hasPermission name="merchant-contacts_open">
    <button  ng-click="vm.queryManagerInfoDetail(bean)">查看</button>
    </shiro:hasPermission>
            </tr>
            </tbody>

        </table>
    </div>

    <core:import url="../../common/pageFoot.jsp"/>
</div>
<core:import url="../../common/nav.jsp"/>
</body>
</html>
