<%@ page import="com.oriental.check.commons.enums.ErrType" %>
<%@ page import="com.oriental.check.commons.enums.HandleStatus" %>
<%@ page import="com.oriental.check.commons.enums.HandleType" %>
<%@ page import="com.oriental.check.commons.enums.TransCode" %>
<%--
  User: 蒯越
  Date: 2016/5/11
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file=".././common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/check/bankChkErrApp-185a89ade8.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="银行对账差异查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/check/bankChkErr/search"
          novalidate w5c-form-validate="vm.validateOptions" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">支付机构</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">交易类型</td>
                <td>
                    <select ng-model="vm.queryBean.transCode" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%
                            for (TransCode e : TransCode.values()) {
                                if (e.name().startsWith("BANK")) {
                        %>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </td>
                <td class="text-right">差异类型</td>
                <td>
                    <select ng-model="vm.queryBean.errType" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (ErrType e : ErrType.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateStart" required/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">清算日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateEnd" required/>
                </td>
                <td class="text-right">处理状态</td>
                <td>
                    <select ng-model="vm.queryBean.handleStatus" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (HandleStatus e : HandleStatus.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">银行请求流水号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankReqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">银行响应流水号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankRespNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">处理人</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.handler" class="{{vm.constant.inputClass}}">
                </td>
            </tr>
            <tr>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.platformCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">支付订单号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.innerPayTransNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">处理方式</td>
                <td>
                    <select ng-model="vm.queryBean.handleType" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (HandleType e : HandleType.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">处理日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oHandleDateStart"/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">处理日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oHandleDateEnd"/>
                </td>
                <td class="text-right"></td>
                <td></td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default"
                            ng-disabled="queryForm.$invalid">
                        查询
                    </button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="bankChkErr_download">
                        <button down-file="${ctx}/check/bankChkErr/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-default" down-cfg="vm.downCfg">
                            下载
                        </button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <shiro:hasPermission name="bankChkErr_operation">
            <button ng-click="vm.batchHandle('02')" class="btn btn-default" >补业务</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="bankChkErr_operationBank">
            <button ng-click="vm.batchHandle('03')" class="btn btn-default" >退银行</button>
        </shiro:hasPermission>
        <table table-detail>
            <thead>
            <tr>
                <th>
                    <input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.pagination.list">
                </th>
                <th>序号</th>
                <th>支付机构</th>
                <th>银行请求流水号</th>
                <th>银行响应流水号</th>
                <th>清算日期</th>
                <th>平台清算日期</th>
                <th>商户</th>
                <th>支付订单号</th>
                <th>交易类型</th>
                <th>系统交易金额</th>
                <th>外部系统交易金额</th>
                <th>外部交易状态</th>
                <th>差异类型</th>
                <th>处理状态</th>
                <th>处理人</th>
                <th>处理时间</th>
                <th>处理方式</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr>
                <td>
                    <input name="check" type="checkbox" ng-model="bean._checked">
                </td>
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td>{{bean.bankReqNo}}</td>
                <td>{{bean.bankRespNo}}</td>
                <td>{{bean.extSettleDate | date:"yyyy-MM-dd"}}</td>
                <td>{{bean.settleDate | date:"yyyy-MM-dd"}}</td>
                <td>
                    <span ng-if="bean.platformCode != null">
                        {{vm.cached.MERCHANT_CODE[bean.platformCode]}}({{bean.platformCode}})
                    </span>
                </td>
                <td>{{bean.innerPayTransNo}}</td>
                <td>{{bean.transCodeDesc}}</td>
                <td>{{bean.transAmt / 100 | currency:""}}</td>
                <td>{{bean.extTransAmt / 100 | currency:""}}</td>
                <td>{{bean.extTransStatus}}</td>
                <td>{{bean.errTypeDesc}}</td>
                <td>{{bean.handleStatusDesc}}</td>
                <td>{{bean.handler}}</td>
                <td>{{bean.handleTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.handleTypeDesc}}</td>
                <td>{{bean.remark}}</td>
                <td>
<shiro:hasPermission name="bankChkErr_operation">
                    <button type="button" ng-click="vm.handle(bean.id, bean.button.split('|')[$index])"
                            class="btn btn-default" ng-repeat="buttonDesc in bean.buttonDesc.split('|')">
                        {{buttonDesc}}
                    </button>
</shiro:hasPermission>
                </td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <td colspan="8" class="text-right">总笔数:</td>
                <td>{{vm.sumObject.totalCount}}</td>
                <td class="text-right">总计:</td>
                <td>{{vm.sumObject.transAmt / 100 | currency:''}}</td>
                <td>{{vm.sumObject.extTransAmt / 100 | currency:''}}</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
