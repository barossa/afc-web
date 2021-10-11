<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<nav class="navbar navbar-expand-lg navbar-light bg-dark bg-opacity-85"
     style="background-color: #63535B !important;">
    <%--<a class="navbar-brand ms-3" href="${pageContext.request.contextPath}/index.jsp">
        <img datatype="png" height="30" width="28" src="<c:url value="/images/navbar-brand.png"/>" alt="AFC"/>
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>--%>

    <div class="collapse navbar-collapse m-l-10" style="opacity: 1;" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto">
            <li class="nav-item active nav-font-main-ws">
                <a class="nav-link me-auto" href="${pageContext.request.contextPath}/index.jsp"
                   style="font-size: 25px; font-weight: bold; color: whitesmoke;">
                    <fmt:message key="navbar.home"/></a>
            </li>
        </ul>
        <ul class="navbar-nav m-l-auto">
            <li class="nav-item active nav-font-main-ws">
                <a class="nav-link me-auto" href="${pageContext.request.contextPath}/jsp/pages/submitAd.jsp"
                   style="font-size: 25px; font-weight: bold; color: whitesmoke;">
                    <fmt:message key="announcements.submitAd"/></a>
            </li>
        </ul>

        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle nav-font" href="#" id="navbarDropdown" role="button"
                   data-bs-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"
                   style="font-size: 15px; font-weight: bolder; color: whitesmoke;">
                    <fmt:message key="language"/>
                </a>

                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li>
                        <button id="eng_bt" class="dropdown-item nav-font" type="button" name="locale" value="en_US">
                            English(US)
                        </button>
                        <%--href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_US">English(US)--%>
                    </li>
                    <li>
                        <button id="rus_bt" class="dropdown-item nav-font" type="button" name="locale" value="ru_RU">
                            <%--href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU">--%>
                            Русский(RU)
                        </button>
                    </li>
                </ul>
            </li>
        </ul>

        <a class="d-flex align-items-center mx-3 rounded-circle bg-light"
           href="#"
           id="navbarDropdownMenuLink"
           role="button"
           data-bs-toggle="dropdown"
           aria-expanded="false">
            <c:choose>
                <c:when test="${user.role != 'GUEST'}">
                    <img src="data:image/png;Base64,${user.profileImage.base64}"
                         class="rounded-circle"
                         height="30"
                         alt=""
                         loading="lazy"/>
                </c:when>
                <c:otherwise>
                    <img src="data:image/png;Base64,${applicationScope.guestImage.base64}"
                         class="rounded-circle"
                         height="30"
                         alt=""
                         loading="lazy"/>
                </c:otherwise>
            </c:choose>
        </a>
        <ul class="dropdown-menu dropdown-menu-end"
            aria-labelledby="navbarDropdownMenuLink">
            <c:if test="${!sessionScope.isAuthorized}">
                <li>
                    <button class="dropdown-item nav-font reqBtn" name="command" value="to_login_page">
                        <fmt:message key="navbar.login"/>
                    </button>
                </li>
            </c:if>

            <c:if test="${sessionScope.isAuthorized}">
                <li>
                    <button class="dropdown-item nav-font reqBtn" name="command" value="to_my_announcements">
                        <fmt:message key="navbar.myAnnouncements"/>
                    </button>
                </li>
                <li>
                    <button class="dropdown-item nav-font reqBtn" name="command" value="to_profile">
                        <fmt:message key="navbar.profile"/>
                    </button>
                </li>
                <li>
                    <a class="dropdown-item nav-font"
                       href="${pageContext.request.contextPath}/controller?command=logout_command">
                        <fmt:message key="navbar.logout"/>
                    </a>
                </li>
            </c:if>

        </ul>
    </div>
</nav>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/vendor/popper/popper.js"/>"></script>
<script src="<c:url value="/js/navbar.js"/>"></script>

</body>
</html>
