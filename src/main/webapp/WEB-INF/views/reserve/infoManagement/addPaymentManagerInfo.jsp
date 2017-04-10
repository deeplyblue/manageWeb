<%--
  User: JinXin
  Date: 2016/12/21
  新增支付机构董高监信息
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
                    <input type="text" name="orgNo" ng-model="vm.item.orgNo" class="{{vm.constant.inputClass}} "  required ng-pattern="/^[\w]*$/"  maxlength="14"/>
                </td>
                <td class="text-right">
                    机构名称：
                </td>
                <td>
                    <input type="text" name="orgName" ng-model="vm.item.orgName" class="{{vm.constant.inputClass}} "  required  maxlength="90"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    姓名：
                </td>
                <td>
                    <input type="text" name="name" ng-model="vm.item.name" class="{{vm.constant.inputClass}} " required maxlength="90"/>
                </td>
                <td class="text-right">
                    身份类型：
                </td>
                <td>
                    <select ng-model="vm.item.type" class="{{vm.constant.inputClass}} "  name="type"
                            ng-options="key as value for (key,value) in {'01':'董事','02':'监事','03':'高管'}" required>
                        <option value="">请选择类型</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    职务：
                </td>
                <td>
                    <input type="text" name="rule" ng-model="vm.item.rule" class="{{vm.constant.inputClass}} " required maxlength="90"/>
                </td>
                <td class="text-right">
                    办公电话：
                </td>
                <td>
                    <input type="text" name="tel" ng-model="vm.item.tel" class="{{vm.constant.inputClass}} " required  ng-pattern="/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/" style="ime-mode:disabled" maxlength="30"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    手机：
                </td>
                <td>
                    <input type="text" name="mobile" ng-model="vm.item.mobile" class="{{vm.constant.inputClass}} "required  ng-pattern="/^1(3|4|5|7|8)\d{9}$/"  style="ime-mode:disabled" maxlength="20"/>
                </td>
                <td class="text-right">
                    邮件：
                </td>
                <td>
                    <input type="text" name="email" ng-model="vm.item.email" class="{{vm.constant.inputClass}} "  required  ng-pattern="/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/" maxlength="100"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    学历：
                </td>
                <td>
                    <input type="text" name="education" ng-model="vm.item.education" class="{{vm.constant.inputClass}} " required maxlength="100"/>
                </td>
                <td class="text-right">
                    专业：
                </td>
                <td>
                    <input type="text" name="proeession" ng-model="vm.item.proeession" class="{{vm.constant.inputClass}} " required  maxlength="100"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    身份证号：
                </td>
                <td>
                    <input type="text" name="idCard" ng-model="vm.item.idCard" class="{{vm.constant.inputClass}} " required  ng-pattern="/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/"  maxlength="100"/>
                </td>
                <td class="text-right">
                    离职标识：
                </td>
                <td>
                    <select ng-model="vm.item.offStatus" class="{{vm.constant.inputClass}} "  name="offStatus"
                            ng-options="key as value for (key,value) in {'01':'在职','02':'离职中','03':'已离职'}" required>
                        <option value="">请选择类型</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td class="text-right">
                    是否经总行批复同意：
                </td>
                <td >
                    <%--当身份类型为03高管时，必须填写--%>
                    <select ng-model="vm.item.isAgree" class="{{vm.constant.inputClass}} "  name="isAgree"
                            ng-options="key as value for (key,value) in {'01':'是','02':'否'}"  required>
                        <option value="">请选择</option>
                    </select>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>
                    从业经历：
                </td>
                <td colspan="3">
                    <textarea name="workExp" rows="10" ng-model="vm.item.workExp" class="form-control"  required maxlength="600"/>

                </td>
            </tr>
            <%--后台添加数据--%>
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
                    <%--<input type="text" name="operator" ng-model="vm.item.operator" class="{{vm.constant.inputClass}} " required />--%>
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
