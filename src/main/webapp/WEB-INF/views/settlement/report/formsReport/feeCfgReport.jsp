<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/23
  Time: 11:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/views/settlement/report/formsReport/feeCfgReportApp.js"></script>
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../../../common/pageHead.jsp">
    <core:param name="title" value="支付业务手续费佣金费率明细表"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlement/report/formsReport/feeCfgReport/search"
          role="form" autocomplete="off">
        <table class="table table-bordered table-condensed">


            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <%--<button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>--%>
                    <%--<shiro:hasPermission name="mchntTransSumDay_down">--%>
                    <button down-file="${ctx}/settlement/report/formsReport/feeCfgReport/download" params="vm.queryBean"
                            down-file-type="xls" class="btn btn-default" >
                        下载
                    </button>
                    <%--</shiro:hasPermission>--%>

                </td>
            </tr>
        </table>
    </form>

    <div>

        <div>
            <st-table-data st-data="vm.pagination.list" st-caches="vm.cached">

                <st-table-column st-head="机构名称" st-field="companyCode" st-mapping="ALL_COMPANY_CODE" />
                <st-table-column st-head="机构类别" st-field="companyType" st-mapping="TYPE" />
                <st-table-column st-head="费率名称" st-field="companyType" st-mapping="FEE_CFG_NAME"/>
                <st-table-column st-head="支付接口或业务种类" st-field="remark"/>
                <st-table-column st-head="费率或佣金费率" st-field="fixFeeShow"/>

            </st-table-data>

        </div>
    </div>


</div>
<core:import url="../../../common/nav.jsp"/>
</body>
</html>
</html>
