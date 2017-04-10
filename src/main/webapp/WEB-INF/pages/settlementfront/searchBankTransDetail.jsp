<%@ page import="com.oriental.check.commons.enums.TransCode" %>
<%@ page import="com.oriental.check.commons.enums.TransStatus" %>
<%@ page import="com.oriental.check.commons.enums.ChkStatus" %>
<%@ page import="com.oriental.check.commons.enums.SettleStatus" %>
<%@ page import="com.oriental.check.commons.enums.DeleteFlag" %>
<%@ page import="com.oriental.check.commons.enums.ConnChannel" %>
<%--
  User: 蒯越
  Date: 2016/5/11
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/build/js/views/settlementfront/bankTransDetailApp-cc579a4442.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
</head>
<body ng-app="myApp" style="overflow:scroll;overflow-x:hidden">
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="银行交易查询"/>
</core:import>
<div ng-controller="queryCtrl" class="container-fluid">
    <form id="queryForm" name="queryForm" action="${ctx}/settlementFront/bankTransDetail/search"
          novalidate w5c-form-validate="vm.validateOptions" autocomplete="off">
        <table class="table table-bordered table-condensed">
            <tr>
                <td class="text-right">支付机构</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.payOrgCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.getCache('COMANY_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">商户</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.platformCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('MERCHANT_CODE') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">接入渠道</td>
                <td>
                    <select ng-model="vm.queryBean.connChannel" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (ConnChannel e : ConnChannel.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">银行</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankCode" class="{{vm.constant.inputClass}}"
                           uib-typeahead="item.key as item.value for item in vm.getCache('BANK_INFO') | filter:$viewValue | limitTo:6">
                </td>
                <td class="text-right">支付订单号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.innerPayTransNo" class="{{vm.constant.inputClass}}">
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

            </tr>
            <tr>
                <td class="text-right">银行请求流水号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankReqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">原银行请求流水号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.oldBankReqNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">交易状态</td>
                <td>
                    <select ng-model="vm.queryBean.transStatus" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (TransStatus e : TransStatus.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">银行响应流水号</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.bankRespNo" class="{{vm.constant.inputClass}}">
                </td>
                <td class="text-right">交易金额(元)</td>
                <td>
                    <input type="text" ng-model="vm.queryBean.sTransAmtStart" class="{{vm.constant.inputClass}}"
                           style="width: 90px;" name="number1" ng-pattern="/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/">
                    &nbsp;--&nbsp;
                    <input type="text" ng-model="vm.queryBean.sTransAmtEnd" class="{{vm.constant.inputClass}}"
                           style="width: 90px;" name="number2" ng-pattern="/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/">
                </td>
                <td class="text-right">勾兑状态</td>
                <td>
                    <select ng-model="vm.queryBean.chkStatus" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (ChkStatus e : ChkStatus.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">银行清算日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateStart" required/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">银行清算日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oDateEnd" required/>
                </td>
                <td class="text-right">清分状态</td>
                <td>
                    <select ng-model="vm.queryBean.settleStatus" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (SettleStatus e : SettleStatus.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">内部清算日期开始</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oSettleDateStart"/>
                </td>
                <td class="text-right">
                    <span class="glyphicon glyphicon-calendar">内部清算日期结束</span>
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup
                           ng-model="vm.queryBean.oSettleDateEnd"/>
                </td>
                <td class="text-right">是否删除</td>
                <td>
                    <select ng-model="vm.queryBean.deleteFlag" class="{{vm.constant.inputClass}}">
                        <option value="">请选择</option>
                        <%for (DeleteFlag e : DeleteFlag.values()) {%>
                        <option value="<%=e.getCode()%>">
                            <%=e.getDesc()%>
                        </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr align="center">
                <td colspan="6">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default"
                            ng-disabled="queryForm.$invalid">查询</button>
                    <button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>
                    <shiro:hasPermission name="bankTransDetail_down">
                        <button down-file="${ctx}/settlementFront/bankTransDetail/download" params="vm.queryBean"
                                down-file-type="xls" class="btn btn-green btn-sm" down-cfg="vm.downCfg">
                            下载
                        </button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </form>
    <div>
        <table table-detail>
            <thead>
            <tr>
                <th>序号</th>
                <th>支付机构</th>
                <th>银行</th>
                <th>接入渠道</th>
                <th>交易类型</th>
                <th>交易状态</th>
                <th>交易金额(元)</th>
                <th>银行请求流水号</th>
                <th>银行请求时间</th>
                <th>勾兑状态</th>
                <th>银行清算日期</th>
                <th>是否参与清算</th>
                <th>清算状态</th>
                <th>商户</th>
                <th>支付订单号</th>
                <th>是否已删除</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody ng-repeat="bean in vm.pagination.list">
            <tr ng-if="bean.deleteFlag == '0'">
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td>{{vm.cached.BANK_INFO[bean.bankCode]}}({{bean.bankCode}})</td>
                <td>{{bean.connChannelDesc}}</td>
                <td>{{bean.transCodeDesc}}</td>
                <td>{{bean.transStatusDesc}}</td>
                <td>{{bean.transAmt / 100| currency:""}}</td>
                <td>{{bean.bankReqNo}}</td>
                <td>{{bean.bankReqTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.chkStatusDesc}}</td>
                <td>{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
                <td>
                    {{vm.cached.SEETLE_FLAG[bean.settleFlag]}}
                </td>
                <td>{{bean.settleStatusDesc}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.platformCode]}}({{bean.platformCode}})</td>
                <td>{{bean.innerPayTransNo}}</td>
                <td>正常</td>
                <td>
                    <button ng-click="vm.queryTransDetail(bean.platformCode,bean.innerPayTransNo)"
                            class="btn btn-default">查看详情
                    </button>
                </td>
            </tr>
            <tr ng-if="bean.deleteFlag == '1'" style="color: red">
                <td>{{$index + 1}}</td>
                <td>{{vm.cached.COMANY_CODE[bean.payOrgCode]}}({{bean.payOrgCode}})</td>
                <td>{{vm.cached.BANK_INFO[bean.bankCode]}}({{bean.bankCode}})</td>
                <td>{{bean.connChannelDesc}}</td>
                <td>{{bean.transCodeDesc}}</td>
                <td>{{bean.transStatusDesc}}</td>
                <td>{{bean.transAmt / 100| currency:""}}</td>
                <td>{{bean.bankReqNo}}</td>
                <td>{{bean.bankReqTime | date:"yyyy-MM-dd HH:mm:ss"}}</td>
                <td>{{bean.chkStatusDesc}}</td>
                <td>{{bean.bankSettleDate | date:"yyyy-MM-dd"}}</td>
                <td>
                    {{vm.cached.SEETLE_FLAG[bean.settleFlag]}}
                </td>
                <td>{{bean.settleStatusDesc}}</td>
                <td>{{vm.cached.MERCHANT_CODE[bean.platformCode]}}({{bean.platformCode}})</td>
                <td>{{bean.innerPayTransNo}}</td>
                <td>删除</td>
                <td>
                    <button ng-click="vm.queryTransDetail(bean.platformCode,bean.innerPayTransNo)"
                            class="btn btn-default">查看详情
                    </button>
                </td>
            </tr>
            <tfoot>
            <tr>
                <td colspan="4" class="text-right">总笔数:</td>
                <td>{{vm.sumObject.totalCount}}</td>
                <td class="text-right">总计:</td>
                <td>{{vm.sumObject.transAmt / 100 | currency:''}}</td>
            </tr>
            </tfoot>
        </table>
    </div>
    <core:import url="../common/pageFoot.jsp"/>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
