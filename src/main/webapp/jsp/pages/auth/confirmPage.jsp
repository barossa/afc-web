<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title>
        <fmt:message key="project.titleTag"/>
        <fmt:message key="auth.confirmationTitle"/>
    </title>
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/fonts/iconic/css/material-design-iconic-font.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/util.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/auth/register.css"/>">

    <fmt:message key="auth.incorrectValue" var="incorrectValue"/>
    <fmt:message key="auth.firstnamePlaceholder" var="firstnamePlaceholder"/>
    <fmt:message key="auth.lastnamePlaceholder" var="lastnamePlaceholder"/>
    <fmt:message key="auth.emailPlaceholder" var="emailPlaceholder"/>
    <fmt:message key="auth.phonePlaceholder" var="phonePlaceholder"/>
    <fmt:message key="auth.loginPlaceholder" var="loginPlaceholder"/>
    <fmt:message key="auth.firstPassPlaceholder" var="firstPassPlaceholder"/>
    <fmt:message key="auth.secondPlaceholder" var="secondPassPlaceholder"/>

</head>
<body>
<div class="limiter">
    <div class="login-container"
         style="background-image: url('${pageContext.request.contextPath}/images/login-bg.jpg');
                 padding-top: 15px;padding-bottom: 15px;">
        <div class="login-wrap p-l-55 p-r-55 p-t-50 p-b-54"
             style="opacity: 0.85;">

             <span class="login-form-title p-b-10">
						<fmt:message key="auth.confirmationTitle"/>
             </span>
            <p class="ms-sm-3">
                <fmt:message key="confirmation.text"/>
                ${requestScope.email}
            </p>
            <p class="ms-sm-3"><fmt:message key="confirm.codeDescription"/></p>
            <form class="form-inline" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="confirm_account">
                <div class="form-group mx-sm-3 mt-2 mb-1">
                    <fmt:message key="confirmation.fieldPlaceholder" var="fieldPlaceholder"/>
                    <fmt:message key="confirmation.buttonPlaceholder" var="buttonPlaceholder"/>
                    <input type="number" step="1" min="100000" max="999999" name="code" placeholder="${fieldPlaceholder}"
                           class="form-control">
                </div>
                <div class="flex-c justify-content-around">
                    <button type="submit" class="btn btn-primary my-3">${buttonPlaceholder}</button>
                    <button id="resendCode" type="button" class="btn my-3">
                        <fmt:message key="confirmation.resendCode"/></button>
                </div>
            </form>
            <div class="flex-c justify-content-lg-start mx-sm-4">
                <form method="post" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="logout_command">
                    <button class="btn-outline-primary" type="submit">
                        <fmt:message key="navbar.logout"/>
                    </button>
                </form>
            </div>


            <%--container--%>

        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
