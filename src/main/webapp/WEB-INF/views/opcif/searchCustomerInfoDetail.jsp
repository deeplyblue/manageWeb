<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <table class="table table-bordered table-condensed">
        <tr>
            <td class="text-right">
                操作员编号
            </td>
            <td>
                {{vm.item.operatorNo}}
            </td>
            <td class="text-right">
                登录用户号
            </td>
            <td>
                {{vm.item.loginNo}}
            </td>
            <td class="text-right">
                客户号
            </td>
            <td>
                {{vm.item.customerNol}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                证件类型
            </td>
            <td>
                {{vm.cached.OPCIF_CERTIFICATE_TYPE[vm.item.certificateType]}}
            </td>
            <td class="text-right">
                证件号
            </td>
            <td>
                {{vm.item.certificateNo }}
            </td>
            <td class="text-right">
                客户名称
            </td>
            <td>
                {{vm.item.customerName}}
            </td>

        </tr>
        <tr>
            <td class="text-right">
                证件生效日期
            </td>
            <td>
                {{vm.item.certStartDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                证件失效期

            </td>
            <td>
                {{vm.item.certExpiryDate | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                客户角色
            </td>
            <td>
                {{vm.cached.CUSTOMER_CRADE[vm.item.customerGrade]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                简称
            </td>
            <td>
                {{vm.item.shortName}}
            </td>
            <td class="text-right">
                客户类型
            </td>
            <td>
              {{vm.cached.ACCOUNT_TYPE[vm.item.customerType]}}
            </td>
            <td class="text-right">
                客户状态
            </td>
            <td>
                {{vm.cached.TGB_CUSTOMER_STATUS[vm.item.customerStatus]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                实名级别
            </td>
            <td>
                {{vm.cached.REALNAME_LV[vm.item.realnameLv]}}
            </td>
            <td class="text-right">
                认证日期

            </td>
            <td>
                {{vm.item.authTime | date:"yyyy-MM-dd HH:mm:ss"}}
            </td>
            <td class="text-right">
                数据来源
            </td>
            <td>
                {{vm.item.dataSourceType}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                出生地
            </td>
            <td >
                {{vm.item.birthplaceTpCd}}
            </td>
            <td class="text-right">
                学历
            </td>
            <td >
                {{vm.item.highestEduTpCd}}
            </td>
            <td class="text-right">
                职业状况
            </td>
            <td >
                {{vm.item. occpStTpCd}}
            </td>

        </tr>
        <tr>
            <td class="text-right">
                职业

            </td>
            <td>
                {{vm.item.occupation}}
            </td>
            <td class="text-right">
                联系地址

            </td>
            <td>
                {{vm.item.contractAddress}}
            </td>
            <td class="text-right">
                邮编

            </td>
            <td>
                {{vm.item.postCode}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                家庭电话

            </td>
            <td>
                {{vm.item.phone}}
            </td>
            <td class="text-right">
                其他电话
            </td>
            <td>
                {{vm.item.otherPhone}}
            </td>
            <td class="text-right">
                手机

            </td>
            <td>
                {{vm.item.mobile}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                办公电话
            </td>
            <td>
                {{vm.item.officePhone }}
            </td>
            <td class="text-right">
                联系邮箱
            </td>
            <td>
                {{vm.item.email}}
            </td>
            <td class="text-right">
                其他地址
            </td>
            <td>
                {{vm.item.otherAddress}}
            </td>
        </tr>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
