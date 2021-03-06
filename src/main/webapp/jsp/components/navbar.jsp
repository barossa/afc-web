<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
  <meta name="contextPath" content="${pageContext.request.contextPath}">
  <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/util.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/iconic/css/material-design-iconic-font.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/montserrat/css/montserrat.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/navbar.css"/>">
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-dark bg-opacity-85"
     style="background-color: #63535B !important;">

  <div class="collapse navbar-collapse m-l-10" style="opacity: 1;" id="navbarSupportedContent">
    <ul class="navbar-nav me-auto">
      <li class="nav-item active nav-font-main-ws">
        <button class="nav-link me-auto href" value=""
                style="border:none; font-size: 25px; font-weight: bold; color: whitesmoke;">
          <fmt:message key="navbar.home"/></button>
      </li>
    </ul>
    <ul class="navbar-nav m-l-auto">
      <li class="nav-item active nav-font-main-ws">
        <button class="nav-link me-auto command" value="to_submit_ad_page"
                style="font-size: 25px; font-weight: bold; color: whitesmoke;">
          <fmt:message key="announcements.submitAd"/></button>
      </li>
    </ul>

    <ul class="navbar-nav">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle nav-font" id="navbarDropdown" role="button"
           data-bs-toggle="dropdown"
           aria-haspopup="true" aria-expanded="false"
           style="font-size: 15px; font-weight: bolder; color: whitesmoke;">
          <fmt:message key="language"/>
        </a>

        <ul id="langDropdown" class="dropdown-menu" aria-labelledby="navbarDropdown">
          <li>
            <button value="change_locale&locale=en_US"
                    class="dropdown-item nav-font command" type="button">
              English(US)
            </button>
          </li>
          <li>
            <button value="change_locale&locale=ru_RU" class="dropdown-item nav-font command" type="button">
              ??????????????(RU)
            </button>
          </li>
        </ul>
      </li>
    </ul>

    <a class="d-flex align-items-center ms-1"
       style="text-decoration: none"
       id="profileDropdown"
       role="button"
       data-bs-toggle="dropdown"
       aria-expanded="false">
      <c:if test="${user.role == 'GUEST'}">
        <i class="nav-font name"><fmt:message key="navbar.guest"/></i>
      </c:if>
      <c:if test="${user.role != 'GUEST'}">
        <i class="nav-font name">${user.login}</i>
      </c:if>
      <i class="bg-light rounded-circle ms-1 me-3">
        <c:choose>
          <c:when test="${user.role != 'GUEST'}">
            <img src="${user.profileImage.base64}"
                 class="rounded-circle"
                 height="35"
                 alt=""
                 loading="lazy"
                 style="max-height: 35px; max-width: 35px;"/>
          </c:when>
          <c:otherwise>
            <img src="${applicationScope.guestImage.base64}"
                 class="rounded-circle"
                 height="35"
                 alt=""
                 loading="lazy"/>
          </c:otherwise>
        </c:choose>
      </i>
    </a>
    <ul class="dropdown-menu dropdown-menu-end"
        id="mainDropdown"
        aria-labelledby="profileDropdown">
      <c:if test="${sessionScope.user.role == 'GUEST'}">
        <li>
          <button class="dropdown-item nav-font command" value="to_login_page">
            <fmt:message key="navbar.login"/>
          </button>
        </li>
      </c:if>

      <c:if test="${sessionScope.user.role != 'GUEST'}">

        <c:choose>
          <c:when test="${user.role == 'ADMINISTRATOR'}">
            <li>
              <button class="dropdown-item nav-font command" value="to_administrator_page">
                <fmt:message key="admin.controlPanel"/>
              </button>
            </li>
          </c:when>
          <c:when test="${user.role == 'MODERATOR'}">
            <li>
              <button class="dropdown-item nav-font command" value="to_moderator_page">
                <fmt:message key="announcements.moderatingPanel"/>
              </button>
            </li>
          </c:when>
        </c:choose>

        <li>
          <button class="dropdown-item nav-font command" value="find_my_announcements">
            <fmt:message key="navbar.myAnnouncements"/>
          </button>
        </li>
        <li>
          <button class="dropdown-item nav-font command" value="to_my_profile">
            <fmt:message key="navbar.profile"/>
          </button>
        </li>
        <li>
          <hr class="dropdown-divider">
        </li>
        <li>
          <button class="dropdown-item nav-font command" value="logout_command">
            <fmt:message key="navbar.logout"/>
          </button>
        </li>
      </c:if>

    </ul>
  </div>
</nav>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<%--<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>--%>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.bundle.js"/>"></script>
</body>
</html>
