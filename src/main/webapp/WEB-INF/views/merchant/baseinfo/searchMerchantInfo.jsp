<%@ page import="com.oriental.manage.core.enums.CompanyType" %>
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
    <script src="${ctx}/js/views/merchant/baseinfo/merchantInfoApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../../common/pageHead.jsp">
    <core:param name="title" value="查询商户基本信息 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/merchant/info/search"   autocomplete="off">
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户代码：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantCode" placeholder="根据简称获取商代码" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>
                </td>
                <td class="text-right">
                    商户状态：
                </td>
                <td>
                    <select ng-model="vm.queryBean.merchantStatus" class="{{vm.constant.inputClass}}"
                    ng-options="key as value for (key,value) in vm.cached.MCHNT_STATUS">
                        <option value="">请选择</option>
                    </select>
                </td>
                <%--<td  class="text-right">--%>
                    <%--业务类型--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.queryBean.companyType" class="{{vm.constant.inputClass}}"--%>
                            <%--ng-options="key as value for (key,value) in {'<%=CompanyType.MERCHANT.getCode()%>':'<%=CompanyType.MERCHANT.getDesc()%>','<%=CompanyType.BUSINESS.getCode()%>':'<%=CompanyType.BUSINESS.getDesc()%>'}">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td class="text-right">--%>
                    <%--商户性质：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select class="form-control" class="form-control">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <%--<td class="text-right">--%>
                    <%--是否有协议：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select class="form-control" class="form-control">--%>
                        <%--<option value="">请选择</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            </tr>
            <tr valign="top">
                <td class="text-right">
                    商户全称：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.merchantName" placeholder="支持模糊查询" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead=" item.value as item.value  for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:10"/>
                </td>

                <%--<td class="text-right" >--%>
                    <%--归属地：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select class="form-control">--%>
                        <%--<option>=====</option>--%>
                    <%--</select>--%>
                    <%--<select class="form-control">--%>
                        <%--<option>=====</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <td class="text-right">
                    开通时间：
                </td>
                <td colspan="8" >
                    <div style="float:left">
                        开始时间
                        <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                               ng-model="vm.queryBean.beginTime" required  /></div>

                        结束时间 <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                                    ng-model="vm.queryBean.endTime" required  />

                <%--</td>--%>
                <%--<td>结束时间<input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup--%>
                           <%--ng-model="vm.queryBean.endTime"--%>
                           <%----%>
                           <%-- --%>
                           <%--required/></td>--%>

                <%--<td></td><td></td>--%>


            </tr>
            <%--<input type="text" ng-model="vm.queryBean.companyType" ng-init="vm.queryBean.companyType = '03'" ng-show="false" />--%>
            <tr align="center">
                <td colspan="8">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>
        <p class="btn-group">
<shiro:hasPermission name="merchant-info_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
</shiro:hasPermission>
<shiro:hasPermission name="merchant-info_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
</shiro:hasPermission>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>商户代码</th>
                <th>商户简称</th>
                <th>商户名称</th>
                <th>商户状态</th>
                <%--<th>业务类型</th>--%>
                <th>开通人</th>
                <th>开通时间</th>
                <th>修改人</th>
                <th>修改时间</th>
                <th>是否有协议</th>
                <th>服务电话</th>
                <th>监控类型</th>
                <th>CA证书状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list ">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.merchantCode)">
                </td>
                <td>{{bean.merchantCode}}</td>
                <td>{{bean.merchantAbbrName}}</td>
                <td>{{bean.merchantName}}</td>
                <td>{{vm.cached.MCHNT_STATUS[bean.merchantStatus]}}</td>
                <%--<td>{{vm.cached.COMPANY_TYPE[bean.companyType]}}</td>--%>
                <td>{{bean.openUser}}</td>
                <td>{{bean.openDate|date:'yyyy-MM-dd'}}</td>
                <td>{{bean.modifyUser}}</td>
                <td>{{bean.lastUpdTime|date:'yyyy-MM-dd'}}</td>
                <td>{{{'0':'无协议','1':'协议期内',}[bean.contractFlag]}}</td>
                <td>{{bean.serviceTelephone}}</td>
                <td>{{vm.cached.MCHNT_TYPE[bean.mchntMonType]}}</td>
                <td>{{vm.cached.CA_APPLY_STATE[bean.caApplyState]}}</td>
                <td>
                    <shiro:hasPermission name="merchant-info_auditpass">
                        <button type="button" ng-if='bean.merchantStatus == "A"' ng-click= "vm.updateItemEnableFlag(bean,'D')">审核通过</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="merchant-info_auditrefuse">
                        <button type="button" ng-if='bean.merchantStatus == "A"' ng-click= "vm.updateItemEnableFlag(bean,'C')">审核失败</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="merchant-info_close">
                        <button type="button" ng-if='bean.merchantStatus == "B"'   ng-click= "vm.updateItemEnableFlag(bean,'C')">关闭</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="merchant-info_open">
                        <button type="button" ng-if='bean.merchantStatus == "D"'   ng-click= "vm.updateItemEnableFlag(bean,'B')">开通</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="merchant-info_reopen">
                    <button type="button" ng-if='bean.merchantStatus == "C"'   ng-click= "vm.updateItemEnableFlag(bean,'A')">重新开启</button>  </td>
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
