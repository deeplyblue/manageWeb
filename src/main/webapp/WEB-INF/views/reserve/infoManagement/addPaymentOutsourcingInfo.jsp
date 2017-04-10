<%--
  User: JinXin
  Date: 2016/12/21
  增加支付机构业务外包信息
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
                    <input type="text" name="orgNo" ng-model="vm.item.orgNo" class="{{vm.constant.inputClass}} " readonly="readonly" required/>
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <input type="text" name="orgName" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} " readonly="readonly" />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    外包服务机构名称：
                </td>
                <td>
                    <input type="text" name="serviceOrgName" ng-model="vm.item.serviceOrgName" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    营业执照编号：
                </td>
                <td>
                    <input type="text" name="businessCode" ng-model="vm.item.businessCode" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    经营范围：
                </td>
                <td>
                    <input type="text" name="businessScope" ng-model="vm.item.businessScope" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    组织机构代码：
                </td>
                <td>
                    <input type="text" name="orgCode" ng-model="vm.item.orgCode" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    外包服务内容：
                </td>
                <td>
                    <input type="text" name="serviceInfo" ng-model="vm.item.serviceInfo" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    成立时间：
                </td>
                <td>
                    <input  type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                            ng-model="vm.item.setupDate" />
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    注册资本：
                </td>
                <td>
                    <input type="text" name="education" ng-model="vm.item.capital" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    注册地：
                </td>
                <td>
                    <input type="text" name="address" ng-model="vm.item.address" class="{{vm.constant.inputClass}} "  />
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
                    所在城市（省份）：
                </td>
                <td>
                    <input type="text" name="localProvince" ng-model="vm.item.localProvince" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    所在城市（城市）：
                </td>
                <td>
                    <input type="text" name="localCity" ng-model="vm.item.localCity" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    所在城市：
                </td>
                <td>
                    <input type="text" name="city" ng-model="vm.item.city" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    主要负责人：
                </td>
                <td>
                    <input type="text" name="manager" ng-model="vm.item.manager" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    邮编：
                </td>
                <td>
                    <input type="text" name="zipCode" ng-model="vm.item.zipCode" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    传真：
                </td>
                <td>
                    <input type="text" name="fax" ng-model="vm.item.fax" class="{{vm.constant.inputClass}} "  />
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
                    负责人手机：
                </td>
                <td>
                    <input type="text" name="mobile" ng-model="vm.item.mobile" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    分润：
                </td>
                <td>
                    <input type="text" name="profit" ng-model="vm.item.profit" class="{{vm.constant.inputClass}} "  />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    保证金：
                </td>
                <td>
                    <input type="text" name="bond" ng-model="vm.item.bond" class="{{vm.constant.inputClass}} "  />
                </td>
                <td class="text-right">
                    企业资质评估：
                </td>
                <td>
                    <input type="text" name="assess" ng-model="vm.item.assess" class="{{vm.constant.inputClass}} "  />
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
            </tr>


        </table>

    </div>
</body>
</html>
