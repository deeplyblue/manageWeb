<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/reserve/reserveDifferApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">

<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="差异数据查询 "/>
</core:import>

<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/reserve/data/differ/differSearch" class="form-inline"
          autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    报文工作日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.startDate"
                           required/>
                    ——
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.endDate"
                           required/>
                <td class="text-right">
                    处理标志：
                </td>
                <td>
                    <!--<input type="text" ng-model="vm.queryBean.isRemark" class="form-control"/>-->
                    <select ng-model="vm.queryBean.isRemark" class="{{vm.constant.inputClass}} "  name="type"
                            ng-options="key as value for (key,value) in {'01':'未处理','02':'已处理'}">
                        <option value="" selected="true" disabled="true">请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="8" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                </td>
            </tr>
        </table>
    </form>

    <div>

        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" name="checkAll">
                </th>
                <th>报文标识号</th>
                <th>对账批号</th>
                <th>对账结果</th>
                <th>对账日期</th>
                <th>处理标志</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list track by $index">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-click="vm.updateChecked($event, bean.id)">
                </td>
                <td>{{bean.batchMsgId}}</td>
                <td>{{bean.checkSeqNo}}</td>
                <td>{{{'01':'对账成功','02':'对账失败'}[bean.checkResult]}}</td>
                <td>{{bean.checkDate | date:'yyyy-MM-dd'}}</td>
                <td>{{{'01':'未处理','02':'已处理'}[bean.isCheck]}}</td>
                <td>
                    <button ng-click="vm.queryDifferDetail(bean)">查看</button>
                </td>
            </tr>
            </tbody>

            <%--            <tfoot>
                        <tr>


                        </tr>
                        </tfoot>--%>
        </table>
    </div>

    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
