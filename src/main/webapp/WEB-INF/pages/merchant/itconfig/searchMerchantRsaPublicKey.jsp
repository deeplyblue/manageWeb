<%--
  User: Yangxp
  Date: 2016/5/19
  Time: 9:31
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div>
    <form name="keyInfoUpdatee" class="form-inline" w5c-form-validate novalidate>
        <table class="table table-bordered">
            <tr>
                <td class="text-right">
                    商户：
                </td>
                <td>
                    {{vm.cached.MERCHANT_CODE[vm.item.companyCode]}}
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    RSA公钥：
                </td>
                <td  style="word-break:break-all">
                    <textarea class="{{vm.constant.inputClass}}" readonly="readonly" style="width: 300px; height: 200px; max-width:300px; max-height: 200px;">{{vm.item.rsaPublicKey}}</textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>