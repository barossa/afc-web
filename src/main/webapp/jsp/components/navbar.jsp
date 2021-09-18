<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>Navbar</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/util.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/iconic/css/material-design-iconic-font.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/montserrat/css/montserrat.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/navbar.css"/>">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-dark bg-opacity-75 m-l-6 m-r-6 m-b-6 m-t-6"
style="background-color: #d9534f !important;">
    <a class="navbar-brand ms-3" href="${pageContext.request.contextPath}/index.jsp">
        <img datatype="png" src="<c:url value="/images/navbar-brand.png"/>" alt="AFC"/>
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" style="opacity: 1;" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto">
            <li class="nav-item active">
                <a class="nav-link me-auto" href="${pageContext.request.contextPath}/index.jsp"
                style="font-size: 25px; font-weight: bold; color: whitesmoke;">
                    <fmt:message key="navbar.home"/></a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <li>
                <c:if test="${!sessionScope.isAuthorized}">
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/pages/auth/login.jsp"
                style="font-size: 15px; font-weight: bolder; color: whitesmoke;">
                    <fmt:message key="navbar.login"/>
                </a>
                </c:if>
                <c:if test="${sessionScope.isAuthorized}">
                    <a class="nav-link " href="${pageContext.request.contextPath}/controller?command=logout_command"
                        style="font-size: 15px; font-weight: bolder; color: whitesmoke;">
                        <fmt:message key="navbar.logout"/>
                    </a>
                </c:if>
            </li>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"
                   style="font-size: 15px; font-weight: bolder; color: whitesmoke;">
                    <fmt:message key="language"/>
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_US">English(US)</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU">Русский(RU)</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/vendor/popper/popper.js"/>"></script>

</body>
</html>
