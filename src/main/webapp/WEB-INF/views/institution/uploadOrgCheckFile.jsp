<%--
  User: wuxg
  Date: 2016/7/04
  Time: 15:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <form autocomplete="off">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td class="text-right">
                    支付机构
                </td>
                <td>
                    <input type="text" ng-model="vm.item.companyCode" class="{{vm.constant.inputClass}}"
                           typeahead-min-length="0"   uib-typeahead="item.key as item.value for item in vm.cached.COMANY_CODE  | filter:$viewValue | limitTo:10"
                           required />
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    银行清算时间
                </td>
                <td>
                    <input type="text" class="{{vm.constant.inputClass}}" uib-datepicker-popup="yyyy-MM-dd"
                           ng-model="vm.item.bankSettleDate"
                           required />
                </td>
            </tr>
            </thead>
            <tbody ng-include="'fileUploader.html'" class="well"></tbody>
            <tbody>
            <tr>
                <button type="button" ng-click="vm.uploadFile()" class="btn btn-default">确认提交</button>
            </tr>
            </tbody>
        </table>
    </form>

    <div>
        <h4>
            说明：<br>
            对账文件命名规则：支付机构号_银行清算日期（yyyyMMdd）。<br>
            例：20160225_103310048990032_20160225.zip<br>
        </h4>
    </div>
</div>
</body>
</html>
