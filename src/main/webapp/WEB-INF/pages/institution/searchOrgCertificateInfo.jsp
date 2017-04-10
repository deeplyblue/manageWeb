<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/09
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/institution/orgCertificateInfo-9ff8e2b618.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询机构证书信息"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/institution/orgCertificate/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    机构代码
                </td>
                <td>
                    <input ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                    </input>
                </td>
                <td class="text-right">
                    本地文件名
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.localFilename" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    证书版本号
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.version" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
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
<shiro:hasPermission name="orgCertificate_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
    </shiro:hasPermission>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll" >
                </th>
                <th>机构代码</th>
                <th>本地文件名</th>
                <th>文档类型 </th>
                <th>证书状态 </th>
                <th>证书版本号 </th>
                <th>证书生效日期</th>
                <th>证书过期日期</th>
                <th>记录最后更新日期 </th>
                <th>修改人 </th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="checked" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode]}}({{bean.orgCode}})</td>
                <td>{{bean.localFilename}}</td>
                <td>{{bean.certificateType}}</td>
                <td>{{{'0':'可用','1':'不可用','2':'废弃'}[bean.status]}}</td>
                <td>{{bean.version}}</td>
                <td>{{bean.effectData | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.expiryData | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.lastUpdTime| date:"yyyy-MM-dd"}}</td>
                <td>{{bean.operator}}</td>
                <td ng-if="bean.operateStatus=='00'&& bean.status =='1' ">
<shiro:hasPermission name="orgCertificate_toOppen">
                   <button type="button"  ng-click= "vm.updateItemEnableFlag(bean,'01')">申请启用</button>
    </shiro:hasPermission>
<shiro:hasPermission name="orgCertificate_abandoned">
                   <button type="button"  ng-click= "vm.updateItemEnableFlag(bean,'05')">废弃</button>
    </shiro:hasPermission>
                </td>
                <td ng-if="bean.operateStatus=='01'&& bean.status =='1' ">
<shiro:hasPermission name="orgCertificate_open">
                    <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,'02')">确认启用</button>
    </shiro:hasPermission>
                    <shiro:hasPermission name="orgCertificate_cancelOpen">
                        <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,'00')">取消启用</button>
                    </shiro:hasPermission>
                </td>
                <td ng-if="bean.operateStatus=='02'&& bean.status =='0' ">
<shiro:hasPermission name="orgCertificate_toClose">
                    <button type="button"   ng-click= "vm.updateItemEnableFlag(bean,'03')">申请关闭</button>
    </shiro:hasPermission>
                </td>
                    <td ng-if="bean.operateStatus=='03'&& bean.status =='0' " >
<shiro:hasPermission name="orgCertificate_close">
                    <button type="button"  ng-click= "vm.updateItemEnableFlag(bean,'04')">确认关闭</button>
    </shiro:hasPermission>
                        <shiro:hasPermission name="orgCertificate_cancelClose">
                            <button type="button"  ng-click= "vm.updateItemEnableFlag(bean,'02')">取消关闭</button>
                        </shiro:hasPermission>
                    </td>
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
