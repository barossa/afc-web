<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="afc" uri="myCustomTag" %>
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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/pagination.css"/>">

<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <aside>
        <div class="rounded" id="filters">
            <c:choose> <%--ALL RADIO BUTTON--%>
                <c:when test="${pagination_data.status == 'UNDEFINED'}">
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="all"
                               id="flexCheckDefault1"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault1">
                            <fmt:message key="announcements.all"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="all"
                               id="flexCheckDefault2">
                        <label class="form-check-label" for="flexCheckDefault2">
                            <fmt:message key="announcements.all"/>
                        </label>
                    </div>
                </c:otherwise>
            </c:choose> <%--ALL RADIO BUTTON--%>

            <c:choose> <%--MODERATING RADIO BUTTON--%>
                <c:when test="${pagination_data.status == 'MODERATING'}">
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="moderating"
                               id="flexCheckDefault3"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault3">
                            <fmt:message key="announcements.moderating"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="moderating"
                               id="flexCheckDefault4">
                        <label class="form-check-label" for="flexCheckDefault4">
                            <fmt:message key="announcements.moderating"/>
                        </label>
                    </div>
                </c:otherwise>
            </c:choose><%--MODERATING RADIO BUTTON--%>

            <c:choose> <%--ACTIVE RADIO BUTTON--%>
                <c:when test="${pagination_data.status == 'ACTIVE'}">
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="active"
                               id="flexCheckDefault5"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault5">
                            <fmt:message key="announcements.active"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="active"
                               id="flexCheckDefault6">
                        <label class="form-check-label" for="flexCheckDefault6">
                            <fmt:message key="announcements.active"/>
                        </label>
                    </div>
                </c:otherwise>
            </c:choose><%--ACTIVE RADIO BUTTON--%>

            <c:choose><%--INACTIVE RADIO BUTTON--%>
                <c:when test="${pagination_data.status == 'INACTIVE'}">
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="inactive"
                               id="flexCheckDefault7"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault7">
                            <fmt:message key="announcements.inactive"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="status" type="radio" value="inactive"
                               id="flexCheckDefault8">
                        <label class="form-check-label" for="flexCheckDefault8">
                            <fmt:message key="announcements.inactive"/>
                        </label>
                    </div>
                </c:otherwise>
            </c:choose><%--INACTIVE RADIO BUTTON--%>
        </div>

        <afc:displayAnnouncements/>

        <afc:pagination command="find_my_announcements"/>

    </aside>
</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
