<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="login.title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/fonts/iconic/css/material-design-iconic-font.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animate/animate.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/css-hamburgers/hamburgers.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animsition/css/animsition.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/select2/select2.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/daterangepicker/daterangepicker.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/auth/util.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/auth/main.css"/>">

    <fmt:message key="login.loginPlaceholder" var="loginPlaceholder"/>
    <fmt:message key="login.passwordPlaceholder" var="passwordPlaceholder"/>
    <fmt:message key="login.incorrectValue" var="incorrectValue"/>

</head>
<body>

<div class="limiter">
    <div class="container-login100"
         style="background-image: url('${pageContext.request.contextPath}/images/login-bg.jpg')">
        <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54"
             style="opacity: 0.85;">
            <form class="login100-form validate-form" method="post"
                  action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="login_command">

                <span class="login100-form-title p-b-49">
						<fmt:message key="login.title"/>
					</span>

                <div class="wrap-input100 validate-input m-b-23" data-validate="${incorrectValue}">
                    <span class="label-input100"><fmt:message key="login.user"/></span>
                    <input class="input100" type="text" name="authField" placeholder="${loginPlaceholder}">
                    <span class="focus-input100" data-symbol="&#xf206;"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="${incorrectValue}">
                    <span class="label-input100"><fmt:message key="login.password"/></span>
                    <input class="input100" type="password" name="password" placeholder="${passwordPlaceholder}">
                    <span class="focus-input100" data-symbol="&#xf190;"></span>
                </div>

                <div class="text-right p-t-8 p-b-31">
                    <a href="#">
                        <fmt:message key="login.forgotPassword"/>
                    </a>
                </div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn">
                            <fmt:message key="login.submit"/>
                        </button>
                    </div>
                </div>

                <div class="text-center p-t-8 p-b-31">
                    <a href="#">
                        <fmt:message key="login.continueAsGuest"/>
                    </a>
                </div>

                <div class="txt1 text-center p-t-30 p-b-15">
                    <a href="#" class="txt2">
                        <fmt:message key="login.signUp"/>
                    </a>
                </div>

            </form>
        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/animsition/js/animsition.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/popper.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/vendor/select2/select2.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/moment.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/daterangepicker.js"/>"></script>
<script src="<c:url value="/vendor/countdowntime/countdowntime.js"/>"></script>
<script src="<c:url value="/js/auth.js"/>"></script>

</body>
</html>
