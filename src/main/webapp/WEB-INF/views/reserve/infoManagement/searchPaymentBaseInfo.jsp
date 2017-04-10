<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: JinXin
  Date: 2016/12/21
  查询支付机基本信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/infoManagement/paymentBaseInfo.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="支付机构基本信息管理 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/msg/paymentBaseInfo/search" autocomplete="off">
        <table table-form>
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"/>--%>
                        <input type="text" ng-model="vm.queryBean.orgNo"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <%--<input type="text" ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"/>--%>
                        <input type="text" ng-model="vm.queryBean.orgName"  class="{{vm.constant.inputClass}}" typeahead-min-length="0"
                               typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>

                </td>
                <td class="text-right">
                    机构类型：
                </td>
                <td>
                    <select ng-model="vm.queryBean.orgType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  {'01':'支付机构','02':'分公司'}">
                        <option value="">请选择类型</option>
                    </select>
                </td>
                <td class="text-right">
                    审核状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.auditStatus" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in  vm.cached.HANDLE_STATUS_FOR_RESERVE">
                        <option value="">请选择审核状态</option>
                    </select>
                </td>
                </tr>
            <tr>
                <td class="text-right">
                    机构组织代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}" />
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
                <td>
                    许可证有效期：
                </td>
                <td  >
                    <div style="float:left">
                        开始时间
                        <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                               ng-model="vm.queryBean.beginTime"   /></div>
                    <div style="float:left">
                    结束时间 <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                                ng-model="vm.queryBean.endTime"   /></div>
                    </td>
            </tr>

            <tr align="center">
                <td colspan="8" class="textCenter">
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
                    <input type="checkbox"  ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>

                <th>机构编号</th>
                <th>机构名称</th>
                <th>机构类型</th>
                <th>组织机构代码</th>
                <th>许可证有效期</th>
                <th>许可证编号</th>
                <th>许可证发放日期</th>
                <th>营业执照编号</th>
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
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.msgId)"
                           ng-model="bean._checked" ng-disabled="bean.centerStatus =='01' ||bean.deleteFlag =='1'||(bean.operateType=='02'&&bean.auditStatus=='08')">
                </td>
                <td>{{bean.orgNo}}</td>
                <td>{{bean.orgName}}</td>
                <td>{{{'01':'支付机构','02':'分公司'}[bean.orgType]}}</td>
                <td>{{bean.orgCode}}</td>
                <td>{{bean.licenseDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.licenseNO}}</td>
                <td>{{bean.licenseGrantDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.businessCode}}</td>
                <td>{{vm.cached.HANDLE_STATUS_FOR_RESERVE[bean.auditStatus]}}{{{'00':'人行初始下发'}[bean.auditStatus]}}</td>
                <td>{{{'01':'是','00':'否'}[bean.centerStatus]}}</td>
                <td>{{{'00':'新增','01':'修改','02':'删除'}[bean.operateType]}}</td>
                <td>{{{'0':'否','1':'是'}[bean.deleteFlag]}}</td>
                <td>
            <shiro:hasPermission name="merchant-contacts_open">
                    <%--点击显示详情--%>
                <button  ng-click="vm.queryBaseInfoDetail(bean)">查看</button>
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
