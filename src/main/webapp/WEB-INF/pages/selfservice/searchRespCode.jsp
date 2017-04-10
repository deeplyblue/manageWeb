
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/selfservice/respCodeApp-0d2fb655cc.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询错误码信息 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/selfService/respCode/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    错误码:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.respCode" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    支付机构:
                </td>
                <td>
                    <input ng-model="vm.queryBean.orgCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.key+item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:10">
                    </input>
                </td>
                <td class="text-right">
                    机构响应码:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.orgRespCode" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    旧机构错误码:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldBankErrCode" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    机构类型:
                </td>
                <td>
                    <select ng-model="vm.queryBean.orgType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.RESP_CODE">
                        <option value="">请选择</option>
                    </select>
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
           <shiro:hasPermission name="respCode_add">
            <button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>
           </shiro:hasPermission>
           <shiro:hasPermission name="respCode_update">
            <button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>
           </shiro:hasPermission>
           <shiro:hasPermission name="respCode_delete">
            <button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>
           </shiro:hasPermission>
        </p>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>错误码 </th>
                <th>错误描述 </th>
                <th>机构类型 </th>
                <th>机构代码 </th>
                <th>机构响应码  </th>
                <th>机构响应码描述   </th>
                <th>旧机构错误码   </th>
                <th>旧机构错误码描述   </th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{bean.respCode}}</td>
                <td>{{bean.respDesc}}</td>
                <td>{{vm.cached.RESP_CODE[bean.orgType]}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.orgCode]}}{{bean.orgCode}}</td>
                <td>{{bean.orgRespCode}}</td>
                <td>{{bean.orgRespDesc}}</td>
                <td>{{bean.oldBankErrCode}}</td>
                <td>{{bean.oldBankErrDesc}}</td>
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
