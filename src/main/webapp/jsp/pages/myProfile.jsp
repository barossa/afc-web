<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="project.titleTag"/><fmt:message key="announcements.my"/></title>
</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/myAnnouncements.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/usersTable.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/myProfile.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/auth/register.css"/>"/>
<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <div class="container">
        <div class="row gutters">
            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                <div class="card h-100">
                    <div class="card-body mx-3">
                        <div class="account-settings">
                            <div class="user-profile">
                                <div class="user-avatar">
                                    <img src="${user.profileImage.base64}" alt="NoImage">
                                </div>
                                <h5 class="user-name">${user.firstname} ${user.lastname}</h5>
                                <h6 class="role">${user.role}</h6>
                                <h6 class="user-email">${user.login}</h6>
                            </div>
                            <div class="about">
                                <h5><fmt:message key="edit.about"/></h5>
                                <p>${user.about}></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                <div class="card h-100">
                    <div class="card-body">
                        <form id="updateForm">
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <h6 class="mb-2 text-primary"><fmt:message key="edit.personalInfo"/></h6>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <fmt:message key="edit.firstnamePlaceholder" var="firstnamePlaceholder"/>
                                        <label for="firstname"><fmt:message key="auth.firstnamePlaceholder"/></label>
                                        <input type="text" class="form-control" name="firstname" id="firstname"
                                               placeholder="${firstnamePlaceholder}"
                                               value="${user.firstname}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <div class="form-group">
                                        <fmt:message key="edit.lastnamePlaceholder" var="lastnamePlaceholder"/>
                                        <label for="lastname"><fmt:message key="auth.lastnamePlaceholder"/></label>
                                        <input type="text" class="form-control" name="lastname" id="lastname"
                                               placeholder="${lastnamePlaceholder}"
                                               value="${user.lastname}">
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <fmt:message key="edit.emailPlaceholder" var="emailPlaceholder"/>
                                    <div class="form-group">
                                        <label for="email"><fmt:message key="auth.emailPlaceholder"/></label>
                                        <input type="email" class="form-control" id="email" name="email" name="email"
                                               placeholder="${emailPlaceholder}"
                                               value="${user.email}"/>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                    <fmt:message key="edit.phonePlaceholder" var="phonePlaceholder"/>
                                    <div class="form-group">
                                        <label for="phone"><fmt:message key="auth.phonePlaceholder"/></label>
                                        <input type="text" class="form-control" id="phone" name="phone"
                                               placeholder="${phonePlaceholder}"
                                               value="${user.phone}">
                                    </div>
                                </div>
                            </div>
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <h6 class="mt-3 mb-2 text-primary"><fmt:message key="edit.aboutTitle"/></h6>
                                </div>
                                <div class="input-group">
                                    <fmt:message key="edit.aboutPlaceholder" var="aboutPlaceholder"/>
                                    <span class="input-group-text">
                                    <fmt:message key="edit.aboutTitle"/>
                                </span>
                                    <textarea id="about" class="form-control">${user.about}</textarea>
                                </div>

                            </div>
                            <div class="row gutters">
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                    <div class="text-left mt-3"
                                         style="width: max-content;">
                                        <label class="text-primary" for="image"><fmt:message key="edit.image"/></label><br/>
                                        <input type="file" name="image" id="image"/>
                                    </div>
                                    <div class="text-right mt-3">
                                        <button type="button" id="saveInfo" name="submit" class="btn btn-primary"
                                                style="width: max-content;">
                                            <fmt:message key="edit.update"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
