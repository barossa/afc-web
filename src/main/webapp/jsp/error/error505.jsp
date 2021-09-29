<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="exception.title"/></title>
</head>
<body>
<h1>505 - <fmt:message key="exception.505"/></h1>
    <br>
    <fmt:message key="exception.message"/>: ${exception_message}
</body>
</html>
