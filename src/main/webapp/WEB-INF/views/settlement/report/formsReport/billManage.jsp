<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2017/1/10
  Time: 17:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
</head>
<body>
    <form action="<%=request.getAttribute("url")%>" method="get">
        <input name="type" type="hidden" value="<%=request.getAttribute("type")%>">
        <input name="num" type="hidden" value="<%=request.getAttribute("num")%>">
        <input name="singe" type="hidden" value="<%=request.getAttribute("singe")%>">
        <input id="submit" type="submit" style="visibility: hidden" value="go!">
    </form>

<script>
    $(document).ready(function () {
        var button = document.getElementById("submit");
        $(button).click();
    })
</script>
</body>
</html>
