<%--
  User: JinXin
  Date: 2016/12/21
  增加支付机构分公司信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    机构编号：
                </td>
                <td>
                    <input type="text" name="orgNo" ng-model="vm.item.orgNo" class="{{vm.constant.inputClass}} "  required/>
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <input type="text" name="orgName" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    分公司编号：
                </td>
                <td>
                    <input type="text" name="branchNO" ng-model="vm.item.branchNO" class="{{vm.constant.inputClass}} "  required/>
                </td>
                <td class="text-right">
                    分公司名称：
                </td>
                <td>
                    <input type="text" name="branchName" ng-model="vm.item.branchName" class="{{vm.constant.inputClass}} "  required/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    成立时间：
                </td>
                <td>
                    <input  type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                            ng-model="vm.item.setupDate" />
                </td>
                <td class="text-right">
                    备案完成时间：
                </td>
                <td>
                    <input  type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                            ng-model="vm.item.regDate" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    备案业务类型：
                </td>
                <td>
                    <input type="text" name="regService" ng-model="vm.item.regService" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    住所（省份）：
                </td>
                <td>
                    <input type="text" name="addrProvince" ng-model="vm.item.addrProvince" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    住所（城市）：
                </td>
                <td>
                    <input type="text" name="addrCity" ng-model="vm.item.addrCity" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    住所：
                </td>
                <td>
                    <input type="text" name="addr" ng-model="vm.item.addr" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地（省份）：
                </td>
                <td>
                    <input type="text" name="realProvince" ng-model="vm.item.realProvince" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    实际经营地（城市）：
                </td>
                <td>
                    <input type="text" name="realCity" ng-model="vm.item.realCity" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    实际经营地：
                </td>
                <td>
                    <input type="text" name="realAddr" ng-model="vm.item.realAddr" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    主要负责人：
                </td>
                <td>
                    <input type="text" name="manager" ng-model="vm.item.manager" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    <input type="text" name="zipCode" ng-model="vm.item.zipCode" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    <input type="text" name="fax" ng-model="vm.item.fax" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    负责人手机：
                </td>
                <td>
                    <input type="text" name="mobile" ng-model="vm.item.mobile" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    负责人办公电话：
                </td>
                <td>
                    <input type="text" name="tel" ng-model="vm.item.tel" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    员工人数：
                </td>
                <td>
                    <input type="text" name="peopleNum" ng-model="vm.item.peopleNum" class="{{vm.constant.inputClass}} "  />
                </td>
                <td></td>
                <td></td>
            </tr>

<%--后台添加--%>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--操作类型：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<select ng-model="vm.item.operateType" class="{{vm.constant.inputClass}} "  name="operateType"--%>
                            <%--ng-options="key as value for (key,value) in {'01':'新增','02':'修改','03':'删除'}" required>--%>
                        <%--<option value="">请选择类型</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--操作人：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input type="text" name="operator" ng-model="vm.item.operator" class="{{vm.constant.inputClass}} "  required/>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td class="text-right">--%>
                    <%--操作时间：--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<input  type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd HH:mm:ss"--%>
                            <%--ng-model="vm.item.operateTime" required/>--%>
                <%--</td>--%>
            <%--</tr>--%>

        </table>

    </div>
</body>
</html>
