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
                    数据KEY：
                </td>
                <td>
                    {{vm.item.dataKey}}
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>