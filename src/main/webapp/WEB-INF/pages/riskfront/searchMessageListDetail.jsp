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
                消息类型码
            </td>
            <td>
                {{vm.cached.RISKFRONT_TX_CODE[vm.item.txCode]}}
            </td>
            <td class="text-right">
                机构号
            </td>
            <td>
                {{vm.item.messageFrom}}
            </td>
            <%--<td class="text-right">--%>
                <%--传输报文流水号--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--{{vm.item.transSerialNumber}}--%>
            <%--</td>--%>
        </tr>
        <tr>
            <td class="text-right">
                消息处理状态
            </td>
            <td>
                {{vm.cached.RISKFRONT_STATUS[vm.item.status]}}
            </td>
            <td class="text-right">
                业务处理状态
            </td>
            <td>
                {{vm.cached.RISKFRONT_DEAL_STATUS[vm.item.dealStatus]}}
            </td>
            <td class="text-right">
                解除状态
            </td>
            <td>
                {{vm.item.isRemove}}
            </td>

        </tr>
        <tr>
            <td class="text-right">
                业务处理码
            </td>
            <td>
                {{vm.cached.RISKFRONT_DEAL_STATUS[vm.item.returnCode] }}
            </td>
            <td class="text-right">
                业务处理描述

            </td>
            <td>
                {{vm.item.returnMessage }}
            </td>
            <td class="text-right">
                业务申请编号
            </td>
            <td>
                {{vm.item.applicationID}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                业务类型
            </td>
            <td>
                {{vm.cached.RISKFRONT_TYPE[vm.item.applicationType]}}
            </td>
            <td class="text-right">
                案件编号
            </td>
            <td>
              {{vm.item.caseNumber}}
            </td>
            <td class="text-right">
                案件类型
            </td>
            <td>
                {{vm.cached.RISKFRONT_CASETYPE[vm.item.caseType]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                支付机构编号
            </td>
            <td>
                {{vm.item.onlinePayCompanyID}}
            </td>
            <td class="text-right">
                支付机构名称

            </td>
            <td>
                {{vm.item.onlinePayCompanyName }}
            </td>
            <td class="text-right">
                账号类别
            </td>
            <td>
                {{vm.cached.RISKFRONT_SUBJECT_TYPE[vm.item.subjectType]}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                参数类型
            </td>
            <td >
                {{vm.cached.RISKFRONT_DATA_TYPE[vm.item.dataType]}}
            </td>
            <td class="text-right">
                订单号/流水号
            </td>
            <td >
                {{vm.item.data}}
            </td>
            <td class="text-right">
                交易时间
            </td>
            <td >
                {{vm.item. transactionDate  }}
            </td>

        </tr>
        <tr>
            <td class="text-right">
                银行编号

            </td>
            <td>
                {{vm.item.bankID}}
            </td>
            <td class="text-right">
                冻结法律文书号

            </td>
            <td>
                {{vm.item.caseDocumentID}}
            </td>
            <td class="text-right">
                止付,冻结事由

            </td>
            <td>
                {{vm.item.reason}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                止付,冻结说明

            </td>
            <td>
                {{vm.item.remark}}
            </td>
            <td class="text-right">
                起始时间
            </td>
            <td>
                {{vm.item.startTime}}
            </td>
            <td class="text-right">
                结束时间

            </td>
            <td>
                {{vm.item.expireTime }}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                申请时间
            </td>
            <td>
                {{vm.item.applicationTime }}
            </td>
            <td class="text-right">
                申请机构编码
            </td>
            <td>
                {{vm.item.applicationOrgID}}
            </td>
            <td class="text-right">
                申请机构名称
            </td>
            <td>
                {{vm.item.applicationOrgName}}
            </td>
        </tr>

        <tr>
            <td class="text-right">
                经办人证件类型
            </td>
            <td>
                <span  ng-if= "vm.item.operatorIDType == '1'">身份证</span>
                {{vm.cached.RISKFRONT_ID_TYPE[vm.item.operatorIDType] }}
            </td>
            <td class="text-right">
                经办人证件号
            </td>
            <td>
                {{vm.item.operatorIDNumber}}
            </td>
            <td class="text-right">
                经办人姓名
            </td>
            <td>
                {{vm.item.operatorName}}
            </td>
        </tr>
        <tr>
            <td class="text-right">
                经办人电话
            </td>
            <td>
                {{vm.item.operatorPhoneNumber  }}
            </td>
            <td class="text-right">
                协查人证件类型
            </td>
            <td>
                {{vm.cached.RISKFRONT_ID_TYPE[vm.item.investigatorIDType]}}
            </td>
            <td class="text-right">
                协查人证件号
            </td>
            <td>
                {{vm.item.investigatorIDNumbe}}
            </td>
        </tr>

        <tr>
            <td class="text-right">
                协查人姓名
            </td>
            <td>
                {{vm.item.investigatorName  }}
            </td>
            <td class="text-right">
                原止付申请编号
            </td>
            <td>
                {{vm.item.originalApplicationID}}
            </td>

        </tr>
    </table>
</div>
<core:import url="../common/nav.jsp"/>
</body>
</html>
