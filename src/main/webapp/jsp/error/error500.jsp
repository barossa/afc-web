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

    <title>
        <fmt:message key="project.titleTag"/>
        <fmt:message key="error.500title"/>
    </title>

    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link href="<c:url value="/fonts/montserrat/css/montserrat.css"/>" rel="stylesheet">
    <link href="<c:url value="/fonts/titillium/css/titillium.css"/>" rel="stylesheet">

    <link type="text/css" rel="stylesheet" href="<c:url value="/css/error-style.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
</head>

<body>

<div id="notfound">
    <div class="notfound">
        <div class="notfound-404">
            <h1>5xx</h1>
        </div>
        <h2><fmt:message key="error.500messageHead"/></h2>
        <p>${requestScope.exception_message}</p>
        <button class="href btn btn-primary mt-4" value=""><fmt:message key="error.goToHome"/></button>
    </div>
</div>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
