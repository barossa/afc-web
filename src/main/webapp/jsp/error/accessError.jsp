<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><fmt:message key="error.accessTitle"/></title>

    <link href="<c:url value="/fonts/montserrat/css/montserrat.css"/>" rel="stylesheet">
    <link href="<c:url value="/fonts/titillium/css/titillium.css"/>" rel="stylesheet">

    <link type="text/css" rel="stylesheet" href="<c:url value="/css/error-style.css"/>" />
</head>

<body>

<div id="notfound">
    <div class="notfound">
        <div class="notfound-404">
            <h1><fmt:message key="error.accessMessageText"/></h1>
        </div>
        <h2><fmt:message key="error.accessMessageHead"/></h2>
        <p><fmt:message key="error.accessMessageBody"/></p>
        <a href="<c:url value="/index.jsp"/>"><fmt:message key="error.goToHome"/></a>
    </div>
</div>

</body>
</html>
