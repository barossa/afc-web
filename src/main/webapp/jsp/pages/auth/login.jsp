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
        <fmt:message key="auth.loginTitle"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/iconic/css/material-design-iconic-font.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animate/animate.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animsition/css/animsition.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/css-hamburgers/hamburgers.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/select2/select2.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/daterangepicker/daterangepicker.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/util.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/auth/login.css"/>">

    <fmt:message key="auth.authFieldPlaceholder" var="loginPlaceholder"/>
    <fmt:message key="auth.passwordPlaceholder" var="passwordPlaceholder"/>
    <fmt:message key="auth.incorrectValue" var="incorrectValue"/>

</head>
<body>
<div class="limiter">
    <div class="login-container"
         style="background-image: url('${pageContext.request.contextPath}/images/login-bg.jpg')">
        <div class="login-wrap p-l-55 p-r-55 p-t-60 p-b-54"
             style="opacity: 0.85;">
            <form class="login-form validate-form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="login_command">

                <span class="login-form-title p-b-49">
						<fmt:message key="auth.loginTitle"/>
					</span>

                <div class="wrap-input validate-input m-b-23" data-validate="${incorrectValue}">
                    <span class="label-input"><fmt:message key="auth.user"/></span>
                    <input class="input" type="text" name="authField" placeholder="${loginPlaceholder}">
                    <span class="focus-input" data-symbol="&#xf206;"></span>
                </div>

                <div class="wrap-input validate-input" data-validate="${incorrectValue}">
                    <span class="label-input"><fmt:message key="auth.password"/></span>
                    <input class="input" type="password" name="password" placeholder="${passwordPlaceholder}">
                    <span class="focus-input" data-symbol="&#xf190;"></span>
                </div>

                <div class="text-right p-t-8 p-b-31">
                    <a href="${pageContext.request.contextPath}/jsp/pages/auth/forgotPassword.jsp">
                        <fmt:message key="auth.forgotPassword"/>
                    </a>
                </div>

                <div class="container-login-form-btn">
                    <div class="wrap-login-form-btn">
                        <div class="login-form-bg-btn"></div>
                        <button class="login-form-btn">
                            <fmt:message key="auth.login"/>
                        </button>
                    </div>
                </div>

                <div class="text-center p-t-8 p-b-31">
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <fmt:message key="auth.continueAsGuest"/>
                    </a>
                </div>

                <p class="text-center m-t-30"><fmt:message key="auth.dontHaveAnAccount"/></p>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/jsp/pages/auth/register.jsp"><fmt:message key="auth.signUp"/></a>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/vendor/select2/select2.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/moment.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/daterangepicker.js"/>"></script>
<script src="<c:url value="/vendor/countdowntime/countdowntime.js"/>"></script>
<script src="<c:url value="/vendor/popper/popper.js"/>"></script>
<script src="<c:url value="/js/auth.js"/>"></script>

</body>
</html>
