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
                    <input type="text" ng-model="vm.queryBean.accountNo" ng-disabled="vm.temp.accountNo" readonly class="{{vm.constant.inputClass}}"/>
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
                    <button type="button" ng-click="vm.checkAccount()" class="btn btn-default">不关联机构查询</button>
                </td>
            </tr>
        </table>
        <div>
            <table table-detail>
                <thead>
                <tr>
                    <th>账户</th>
                    <th>金额</th>
                    <th>交易时间</th>
                    <th>摘要</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tr>
                    <td>{{vm.item.accountNo}}</td>
                    <td>{{vm.item.amt}}</td>
                    <td>{{vm.item.settleDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{vm.item.summary}}</td>
                    <td>{{vm.item.bankRemark}}</td>
                </tr>
            </table>
        </div>
        <div>
            <button type="button" ng-click="vm.checkTrans()">确认核对</button>
        </div>
        <div>
            <table table-detail>
                <thead>
                <tr>
                    <th><input type="checkbox" ng-model="_checkedAll" checkbox-all="vm.cached.CHECK_LIST"></th>
                    <th>机构</th>
                    <th>清算时间</th>
                    <th>应付金额</th>
                    <th>应收手续费</th>
                    <th>应收金额</th>
                    <th>应付手续费</th>
                    <th>结算开始</th>
                    <th>结算结束</th>
                    <th>收款是否核对</th>
                    <th>付款是否核对</th>
                    <th>清除</th>
                </tr>
                </thead>
                <tbody>
                <tr  ng-repeat="bean in vm.cached.CHECK_LIST track by $index">
                    <td><input name="check" type="checkbox" ng-model="bean._checked"></td>
                    <td>{{vm.cached.COMANY_CODE[bean.orgCode]}}[{{bean.orgCode}}]</td>
                    <td>{{bean.clearDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{bean.payableAmt/100}}</td>
                    <td>{{bean.receivableFeeAmt/100}}</td>
                    <td>{{bean.receivableAmt/100}}</td>
                    <td>{{bean.payableFeeAmt/100}}</td>
                    <td>{{bean.startDate|date:'yyyy-MM-dd'}}</td>
                    <td>{{bean.endDate|date:'yyyy-MM-dd'}}</td>
                    <td>
                    <select ng-model="bean.recCheckFlag" ng-options="key as value for (key,value) in {'0':'未核对','1':'已核对'}">
                        <option value="">请选项</option>
                    </select>
                    </td>
                    <td>
                        <select ng-model="bean.payCheckFlag" ng-options="key as value for (key,value) in {'0':'未核对','1':'已核对'}">
                            <option value="">请选项</option>
                        </select>
                    </td>
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
