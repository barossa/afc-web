<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="auth.regTitle"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/iconic/css/material-design-iconic-font.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animate/animate.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/css-hamburgers/hamburgers.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/animsition/css/animsition.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/select2/select2.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/vendor/daterangepicker/daterangepicker.css"/>">
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

             <span class="login-form-title p-b-45">
						<fmt:message key="auth.regTitle"/>
					</span>

            <div class="form-group input-group me-auto">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-pencil"></i> </span>
                </div>
                <input name="" class="form-control" placeholder="${firstnamePlaceholder}" type="text">
                <div class="input-group-prepend"
                style="margin-left: 10px;">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-pencil"></i> </span>
                </div>
                <input name="" class="form-control" placeholder="${lastnamePlaceholder}" type="text">
            </div>
            <div class="form-group input-group m-t-10">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="" class="form-control" placeholder="${emailPlaceholder}" type="email">
            </div>
            <div class="form-group input-group m-t-10">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-phone"></i> </span>
                </div>
                <input name="" class="form-control" placeholder="${phonePlaceholder}" type="text">
            </div> <!-- form-group// -->
            <div class="form-group input-group m-t-10">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-user"></i> </span>
                </div>
                <input class="form-control" placeholder="${loginPlaceholder}" type="text">
            </div> <!-- form-group end.// -->
            <div class="form-group input-group m-t-10">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-lock"></i> </span>
                </div>
                <input class="form-control" placeholder="${firstPassPlaceholder}" type="password">
            </div> <!-- form-group// -->
            <div class="form-group input-group m-t-10">
                <div class="input-group-prepend">
                    <span class="input-group-text p-b-14 p-t-14"> <i class="fa fa-lock"></i> </span>
                </div>
                <input class="form-control" placeholder="${secondPassPlaceholder}" type="password">
            </div> <!-- form-group// -->
            <div class="form-group text-center m-t-30">
                <button type="submit" class="btn btn-primary btn-block"><fmt:message key="auth.register"/></button>
            </div> <!-- form-group// -->
            <p class="text-center m-t-30"><fmt:message key="auth.alreadyHaveAccount"/></p>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/jsp/pages/auth/login.jsp"><fmt:message key="auth.login"/></a>
            </div>
        </div>
    </div>
</div>


<div id="dropDownSelect1"></div>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/animsition/js/animsition.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/vendor/select2/select2.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/moment.min.js"/>"></script>
<script src="<c:url value="/vendor/daterangepicker/daterangepicker.js"/>"></script>
<script src="<c:url value="/vendor/countdowntime/countdowntime.js"/>"></script>
<script src="<c:url value="/vendor/popper/popper.js"/>"></script>
<script src="<c:url value="/js/register.js"/>"></script>

</body>
</html>
