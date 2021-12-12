<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title>Title</title>

    <fmt:message key="auth.firstnamePlaceholder" var="firstnamePlaceholder"/>
    <fmt:message key="auth.lastnamePlaceholder" var="lastnamePlaceholder"/>
    <fmt:message key="auth.emailPlaceholder" var="emailPlaceholder"/>
    <fmt:message key="auth.phonePlaceholder" var="phonePlaceholder"/>
    <fmt:message key="auth.loginPlaceholder" var="loginPlaceholder"/>

</head>
<style>
    .fa {
        font-size: 16px !important;
    }
</style>
<body>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="auth.user"/> <fmt:message
                        key="admin.id"/>${requestScope.user.id}</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"
                        style="border: none; background: none;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="flex-c justify-content-between">
                    <div class="form-group input-group me-auto m-r-20" style="margin-right: 20px !important;">
                        <div class="input-group-prepend">
                            <span class="input-group-text p-b-15"> <span class="fa fa-pencil"></span> </span>
                        </div>
                        <input name="firstname" class="input form-control" placeholder="${firstnamePlaceholder}"
                               value="${requestScope.user.firstname}" type="text">
                    </div>
                    <div class="form-group input-group me-auto">
                        <div class="input-group-prepend">
                            <span class="input-group-text p-b-15"> <span class="fa fa-pencil"></span> </span>
                        </div>
                        <input name="lastname" class="input form-control" placeholder="${lastnamePlaceholder}"
                               type="text"
                               value="${requestScope.user.lastname}">
                    </div>
                </div>
                <div class="form-group input-group m-t-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text p-b-15"> <span class="fa fa-envelope"></span> </span>
                    </div>
                    <input name="email" class="input form-control" placeholder="${emailPlaceholder}" type="text"
                           value="${requestScope.user.email}">
                </div>
                <div class="flex-c">
                    <div class="form-group input-group m-t-10 me-auto" style="margin-right: 20px !important;">
                        <div class="input-group-prepend">
                            <span class="input-group-text p-b-15"> <span class="fa fa-phone"></span> </span>
                        </div>
                        <input name="phone" class="input form-control me-auto" placeholder="${phonePlaceholder}"
                               type="text"
                               value="${requestScope.user.phone}">
                    </div> <!-- form-group// -->
                    <div class="form-group input-group m-t-10 me-auto">
                        <div class="input-group-prepend">
                            <span class="input-group-text p-b-15"> <span class="fa fa-user"></span> </span>
                        </div>
                        <input name="login" class="input form-control" placeholder="${loginPlaceholder}" type="text"
                               value="${requestScope.user.login}">
                    </div> <!-- form-group end.// -->
                </div>

                <div class="mt-3 flex-c justify-content-lg-around">
                    <div class="dropdown">
                        <button type="button" id="role-dd${requestScope.user.id}"
                                class="btn btn-primary dropdown-toggle"
                                data-bs-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false">${requestScope.user.role}
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item">USER</a></li>
                            <li><a class="dropdown-item">ADMINISTRATOR</a></li>
                            <li><a class="dropdown-item">MODERATOR</a></li>
                        </ul>
                    </div>

                    <div class="dropdown">
                        <button type="button" id="status-dd${requestScope.user.id}"
                                class="btn btn-primary dropdown-toggle"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            <c:if test="${requestScope.user.status == 'DELAYED_REG'}">
                                <fmt:message key="status.delayedTeg"/>
                            </c:if>
                            <c:if test="${requestScope.user.status == 'ACTIVE'}">
                                <fmt:message key="status.active"/>
                            </c:if>
                            <c:if test="${requestScope.user.status == 'BANNED'}">
                                <fmt:message key="status.banned"/>
                            </c:if>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item"><fmt:message key="status.delayedTeg"/></a></li>
                            <li><a class="dropdown-item"><fmt:message key="status.active"/></a></li>
                            <li><a class="dropdown-item"><fmt:message key="status.banned"/></a></li>
                        </ul>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message
                        key="admin.cancel"/></button>
                <button type="button" class="btn btn-primary"><fmt:message key="edit.update"/></button>
            </div>
        </div>
    </div>
</div>
</body>
</html>