
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/download/userDownloadApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="查询下载文件 "/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/download/userDownload/search" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">
                    文件名：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.fileName" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">
                    结果：
                </td>
                <td>
                    <select ng-model="vm.queryBean.status" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in vm.cached.T_USER_DOWNLOAD">
                        <option value="">全部</option>
                    </select>

                </td>
            </tr>
            <tr>
                <td class="text-right">
                    处理开始时间:
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.createTime" uib-datepicker-popup="yyyyMMdd"
                           class="{{vm.constant.inputClass}}" >
                </td>
                <td class="text-right">
                    处理结束时间：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.lastUpdTime" uib-datepicker-popup="yyyyMMdd" class="{{vm.constant.inputClass}}">
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
            <%--<button ng-click="vm.addItem()"><i class="glyphicon glyphicon-plus"></i></button>--%>
            <%--<button ng-click="vm.updateItem()"><i class="glyphicon glyphicon-pencil"></i></button>--%>
            <%--<button ng-click="vm.deleteItem()"><i class="glyphicon glyphicon-minus"></i></button>--%>
        </p>
        <table table-detail>
            <thead>
            <tr>

                <th>用户名 </th>
                <th>文件名 </th>
                <th>结果 </th>
                <th>开始日期 </th>
                <th>结束日期  </th>
                <th>操作   </th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>

                <td>{{bean.userName}}</td>
                <td>{{bean.fileName}}</td>
                <td>{{vm.cached.T_USER_DOWNLOAD[bean.status]}}</td>
                <td>{{bean.createTime |date:'yyyyMMdd'}}</td>
                <td>{{bean.lastUpdTime |date:'yyyyMMdd'}}</td>
                <td><a ng-if="bean.remoteFileName!=null && bean.remoteFileName!=''"href="${ctx}/base/dfsFileInfo/download?localFilename={{bean.fileName}}
                        &dfsFullFilename={{bean.remoteFileName}}
                        &dfsGroupname={{vm.item.dfsInfo.dfsGroupName}}">
                    下载</a>
                    <p ng-if="bean.remoteFileName=='' || bean.remoteFileName==null " ><span style="color:red;">文件生成失败</span></p>
                </td>

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
