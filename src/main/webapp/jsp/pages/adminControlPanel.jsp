<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="afc" uri="myCustomTag" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="project.titleTag"/><fmt:message key="announcements.my"/></title>
    <c:if test="${requestScope.pagination == null}">
        <jsp:forward page="/controller?command=find_users"/>
    </c:if>
</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/myAnnouncements.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/usersTable.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">

<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <%--<aside>

    </aside>--%>
    <div class="container-xl my-4">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><fmt:message key="admin.users"/></h2>
                    </div>
                    <div class="col-sm-7">
                        <a href="#" class="btn btn-secondary"><i class="fa fa-plus-circle"></i>
                            <span><fmt:message key="admin.addUser"/></span></a>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="admin.id"/></th>
                    <th><fmt:message key="admin.name"/></th>
                    <th><fmt:message key="admin.login"/></th>
                    <th><fmt:message key="admin.role"/></th>
                    <th><fmt:message key="admin.status"/></th>
                    <th><fmt:message key="admin.action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.pagination.data}">
                    <tr>
                        <td>${user.id}</td>
                        <td><a href="#"><img src="data:image/png;Base64,${user.profileImage.base64}" class="avatar"
                                             alt="Avatar">${user.firstname} ${user.lastname}</a></td>
                        <td>${user.login}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.role == 'ADMINISTRATOR'}">
                                    <span class="text-danger">${user.role}</span>
                                </c:when>
                                <c:when test="${user.role == 'MODERATOR'}">
                                    <span class="text-warning">${user.role}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-info">${user.role}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><c:choose>
                            <c:when test="${user.status == 'DELAYED_REG'}">
                                <span class="status text-warning">&bull;</span><fmt:message key="status.delayedTeg"/>
                            </c:when>
                            <c:when test="${user.status == 'BANNED'}">
                                <span class="status text-danger">&bull;</span><fmt:message key="status.banned"/>
                            </c:when>
                            <c:otherwise>
                                <span class="status text-success">&bull;</span><fmt:message key="status.active"/>
                            </c:otherwise>
                        </c:choose></td>
                        <td>
                            <button class="settings edit" value="${user.id}" data-bs-toggle="modal" data-bs-target="#editModal${user.id}"
                                    style="background: none; border: none;">
                                <i class="fa fa-cog"></i>
                            </button>
                            <!-- EDIT MODAL -->
                            <div class="modal fade" id="editModal${user.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content" id="modalContent${user.id}">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="edit.loading"/></h5>
                                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"
                                                    style="border: none; background: none">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="admin.cancel"/></button>
                                            <button type="button" class="btn btn-primary"><fmt:message key="edit.update"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- EDIT MODAL -->
                            <button type="button" class="delete" data-bs-toggle="modal" data-bs-target="#banModal${user.id}"
                                    style="border:none; background: none;">
                                <i class="fa fa-times-circle"></i>
                            </button>
                            <!-- BAN MODAL -->
                            <div class="modal fade" id="banModal${user.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title"><fmt:message key="admin.ban"/> ${user.login}</h5>
                                            <button style="border: none; background: none;" type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <fmt:message key="admin.banWarning"/>
                                            <fmt:message key="admin.reasonPlaceholder" var="reasonPlaceholder"/>
                                            <br/>
                                            <label class="mb-1" for="reason${user.id}"><fmt:message key="admin.banReason"/></label>
                                            <input type="text" id="reason${user.id}" placeholder="${reasonPlaceholder}" name="reason${user.id}">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="admin.cancel"/></button>
                                            <button type="button" value="${user.id}" data-bs-dismiss="modal" class="btn btn-primary ban"><fmt:message key="admin.ban"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- BAN MODAL -->
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="flex-c justify-content-center mt-2">
            <afc:pagination command="find_users"/>
        </div>

    </div>


</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/js/main.js"/>"></script>
<script src="<c:url value="/vendor/popper/popper.min.js"/>" crossorigin="anonymous"></script>

</body>
</html>
