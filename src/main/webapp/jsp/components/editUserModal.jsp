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
                <form id="updateForm${requestScope.user.id}">
                    <input class="edit-fd" type="hidden" name="command" value="update_user"/>
                    <input class="edit-fd" type="hidden" name="id" value="${requestScope.user.id}">
                    <div class="flex-c justify-content-between">
                        <div class="form-group input-group me-auto m-r-20" style="margin-right: 20px !important;">
                            <div class="input-group-prepend">
                                <span class="input-group-text p-b-15"> <span class="fa fa-pencil"></span> </span>
                            </div>
                            <input name="firstname" class="edit-fd input form-control" placeholder="${firstnamePlaceholder}"
                                   value="${requestScope.user.firstname}" type="text">
                        </div>
                        <div class="form-group input-group me-auto">
                            <div class="input-group-prepend">
                                <span class="input-group-text p-b-15"> <span class="fa fa-pencil"></span> </span>
                            </div>
                            <input name="lastname" class="input form-control edit-fd" placeholder="${lastnamePlaceholder}"
                                   type="text"
                                   value="${requestScope.user.lastname}">
                        </div>
                    </div>
                    <div class="form-group input-group m-t-10">
                        <div class="input-group-prepend">
                            <span class="input-group-text p-b-15"> <span class="fa fa-envelope"></span> </span>
                        </div>
                        <input name="email" class="input form-control edit-fd" placeholder="${emailPlaceholder}" type="text"
                               value="${requestScope.user.email}">
                    </div>
                    <div class="flex-c">
                        <div class="form-group input-group m-t-10 me-auto" style="margin-right: 20px !important;">
                            <div class="input-group-prepend">
                                <span class="input-group-text p-b-15"> <span class="fa fa-phone"></span> </span>
                            </div>
                            <input name="phone" class="input form-control me-auto edit-fd" placeholder="${phonePlaceholder}"
                                   type="text"
                                   value="${requestScope.user.phone}">
                        </div> <!-- form-group// -->
                        <div class="form-group input-group m-t-10 me-auto">
                            <div class="input-group-prepend">
                                <span class="input-group-text p-b-15"> <span class="fa fa-user"></span> </span>
                            </div>
                            <input name="login" class="input form-control edit-fd" placeholder="${loginPlaceholder}" type="text"
                                   value="${requestScope.user.login}">
                        </div> <!-- form-group end.// -->
                    </div>

                    <div class="mt-3 flex-c justify-content-lg-around">
                        <input class="edit-fd" type="hidden" name="role" value="${requestScope.user.role}">
                        <div class="dropdown">
                            <button type="button" name="role"
                                    class="btn btn-primary dropdown-toggle dd"
                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                    aria-expanded="false">${requestScope.user.role}
                            </button>
                            <ul class="dropdown-menu" name="role">
                                <li><a value="USER" id="${requestScope.user.id}" class="dropdown-item"><fmt:message key="role.user"/></a></li>
                                <li><a value="ADMINISTRATOR" id="${requestScope.user.id}" class="dropdown-item"><fmt:message key="role.admin"/></a></li>
                                <li><a value="MODERATOR" id="${requestScope.user.id}" class="dropdown-item"><fmt:message key="role.moder"/></a></li>
                            </ul>
                        </div>

                        <div class="dropdown">
                            <input class="edit-fd" type="hidden" name="status" value="${requestScope.user.status}">
                            <button type="button" name="status"
                                    class="btn btn-primary dropdown-toggle dd"
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
                            <ul class="dropdown-menu" name="status">
                                <li><a id="${requestScope.user.id}" value="DELAYED_REG" class="dropdown-item"><fmt:message key="status.delayedTeg"/></a></li>
                                <li><a id="${requestScope.user.id}" value="ACTIVE" class="dropdown-item"><fmt:message key="status.active"/></a></li>
                                <li><a id="${requestScope.user.id}" VALUE="BANNED" class="dropdown-item"><fmt:message key="status.banned"/></a></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message
                        key="admin.cancel"/></button>
                <button type="button" value="${requestScope.user.id}" class="btn btn-primary updateButton" data-bs-dismiss="modal"><fmt:message
                        key="edit.update"/></button>
            </div>
        </div>
    </div>
</div>
</body>
</html>