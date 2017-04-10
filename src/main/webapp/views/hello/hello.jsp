<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>hello</title>
</head>
<body>
<%=request.getAttribute("name")%>
<%=session.getAttribute("name")%>

<h1>${name},你好!</h1>
</body>
</html>
