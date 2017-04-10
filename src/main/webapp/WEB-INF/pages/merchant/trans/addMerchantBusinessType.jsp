<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div common-modal-form>

        <table class="table table-bordered">
            <tr>
                <td class="text-right col-xs-3">
                    商户：
                </td>
                <td class="col-xs-4">
                    <input type="text" ng-model="vm.item.merchantCode"  name="merchantCode" required
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.MERCHANT_CODE | filter:$viewValue | limitTo:6"  class="{{vm.constant.inputClass}} "/>
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    业务资金源：
                </td>
                <td class="col-xs-4">
                    <select name="businessType" ng-model="vm.item.businessType" class="{{vm.constant.inputClass}}"
                        ng-options="key as value for (key,value) in vm.cached.BUSINESS_FUNDING_SOURCES"
                            ng-change="vm.item.businessTypeDesc = vm.cached.BUSINESS_FUNDING_SOURCES[vm.item.businessType]"   required>
                        <option value="">请选择</option>
                    </select>
                    <input ng-model="vm.item.businessTypeDesc" style="display: none;">
                </td>
            </tr>

            <tr>
                <td class="text-right">
                    启用标记：
                </td>
                <td class="col-xs-4">
                    <select name="enableType" ng-model="vm.item.enableType" class="{{vm.constant.inputClass}}"
                            ng-options="key as value for (key,value) in {'0':'关闭','1':'开启','2':'维护'}"  class="{{vm.constant.inputClass}} " required>
                        <option value="">请选择</option>
                    </select>
                </td>
            </tr>


        </table>

</div>
</body>
</html>