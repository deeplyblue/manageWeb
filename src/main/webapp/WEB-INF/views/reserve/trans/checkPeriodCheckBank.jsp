<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table table-detail>
            <tr>
                <td class="text-right">
                    备付金账户：
                </td>
                <td>
                    <input type="text" ng-model="vm.queryBean.otherSideAccountNo" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.accountNo as item.desc for item in vm.cached.BANK_INFO_LIST | filter:$viewValue | limitTo:10"/>
                    <input type="text" ng-model="vm.queryBean.id" ng-hide="true" class="{{vm.constant.inputClass}}"/>

                </td>
                <td class="text-right">
                    交易日期：
                </td>
                <td nowrap="nowrap" style="white-space:nowrap;overflow:hidden;word-break:keep-all;">
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.queryStartDate"
                           required/>
                    ——
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.queryBean.queryEndDate"
                           required/>

                </td>
            </tr>
            <tr class="text-center">
                <td colspan="10" class="textCenter">
                    <button type="button" ng-click="vm.queryDetail()" class="btn btn-default">查询</button>
                    <%--<button type="button" ng-click="vm.resetForm()" class="btn btn-default">重置</button>--%>
                </td>
            </tr>
        </table>
        <div>
            <table table-detail>
                <thead>
                <tr>
                    <th>机构号</th>
                    <th>机构类型</th>
                    <th>清算开始</th>
                    <th>清算结算</th>
                    <th>应收金额</th>
                    <th>应收笔数</th>
                    <th>应收手续费</th>
                    <th>应付金额</th>
                    <th>应付笔数</th>
                    <th>应付手续费</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tr>
                    <td>{{vm.item.orgCode}}</td>
                    <td>{{vm.item.orgType}}</td>
                    <td>{{vm.item.startDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{vm.item.endDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{vm.item.clearDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{vm.item.receivableAmt}}</td>
                    <td>{{vm.item.receivableCount}}</td>
                    <td>{{vm.item.receivableFeeAmt}}</td>
                    <td>{{vm.item.payableAmt}}</td>
                    <td>{{vm.item.payableCount}}</td>
                    <td>{{vm.item.payableFeeAmt}}</td>
                    <td>{{vm.item.remarkDesc}}</td>
                </tr>
            </table>
        </div>
        <div>
            <button type="button" ng-click="vm.checkTrans()">确认核对</button>
        </div>
        <div style="width: 100%;overflow:auto">
            <table table-detail style="width: 1300px;">
                <thead>
                <tr>
                    <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.cached.CHECK_LIST"></th>
                    <th>账号</th>
                    <th>账户名</th>
                    <th>交易类型</th>
                    <th>借贷标识</th>
                    <th>金额</th>
                    <th>余额</th>
                    <th>银行入账日期</th>
                    <th>银行入账时间</th>
                    <th>对方账户</th>
                    <th>对方户名</th>
                    <th>银行备注</th>
                    <th>资金状态</th>
                    <th>清除</th>
                </tr>
                </thead>
                <tbody>
                <tr  ng-repeat="bean in vm.cached.CHECK_LIST track by $index">
                    <td><input name="check" type="checkbox" ng-model="bean._checked"></td>
                    <td>{{bean.accountNo}}</td>
                    <td>{{bean.accountName}}</td>
                    <td>{{bean.transType}}</td>
                    <td>{{bean.cdFlag}}</td>
                    <td>{{bean.amt}}</td>
                    <td>{{bean.balance}}</td>
                    <td>{{bean.transDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{bean.transTime|date:'yyyy-MM-dd HH:mm:ss'}}</td>
                    <td>{{bean.otherSideAccountNo}}</td>
                    <td>{{bean.otherSideName}}</td>
                    <td>{{bean.bankRemark}}</td>
                    <td>{{bean.cashStatus}}</td>
                    <td>
                        <button type="button" ng-click="vm.clearCheck(bean)">清除核对</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

    </form>
</div>
</body>
</html>
