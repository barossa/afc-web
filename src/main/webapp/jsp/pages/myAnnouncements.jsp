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

<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <aside>
        <div class="rounded" id="filters">
            <c:choose> <%--ALL RADIO BUTTON--%>
                <c:when test="${pagination_data.status == 'UNDEFINED'}">
                    <div class="form-check">
                        <input class="form-check-input radio" name="filter" type="radio" value="all"
                               id="flexCheckDefault1"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault1">
                            <fmt:message key="announcements.all"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="filter" type="radio" value="all"
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
                        <input class="form-check-input radio" name="filter" type="radio" value="moderating"
                               id="flexCheckDefault3"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault3">
                            <fmt:message key="announcements.moderating"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="filter" type="radio" value="moderating"
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
                        <input class="form-check-input radio" name="filter" type="radio" value="active"
                               id="flexCheckDefault5"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault5">
                            <fmt:message key="announcements.active"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="filter" type="radio" value="active"
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
                        <input class="form-check-input radio" name="filter" type="radio" value="inactive"
                               id="flexCheckDefault7"
                               checked>
                        <label class="form-check-label" for="flexCheckDefault7">
                            <fmt:message key="announcements.inactive"/>
                        </label>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-check">
                        <input class="form-check-input radio" name="filter" type="radio" value="inactive"
                               id="flexCheckDefault8">
                        <label class="form-check-label" for="flexCheckDefault8">
                            <fmt:message key="announcements.inactive"/>
                        </label>
                    </div>
                </c:otherwise>
            </c:choose><%--INACTIVE RADIO BUTTON--%>
        </div>

        <c:if test="${empty pagination_data.currentData}">
            <div class="card mb-3">
                <fmt:message key="search.nothingFound"/>
            </div>
        </c:if>
        <c:forEach varStatus="varStatus" var="ad" items="${pagination_data.currentData}">
            <div id="${varStatus.index}" class="card mb-2 adCard" style="height: 150px;">
                <div class="row h-100">
                    <div class="col-md-2 align-items-center justify-content-end"
                         style="display: flex; max-height: 150px;">
                        <img class="rounded-2 mx-4 my-3"
                             style="max-width: 130px; max-height: 130px;"
                             src="data:image/png;Base64,${ad.getPrimaryImage()}"
                             width="130" alt="No image"/>
                    </div>
                    <div class="col-md-7">
                        <div class="card-body">
                            <h5 class="card-title">${ad.title}</h5>
                            <h6 class="card-subtitle mb-2 text-muted"
                                style="margin-top: -5px;">${ad.category.description}</h6>
                                ${ad.shortDescription}
                        </div>

                        <p class="card-text flex-c justify-content-end">
                            <small class="text-muted">
                                <i class="fa fa-user">
                                    <label>${ad.owner.login}</label>
                                </i>
                                <i class="fa fa-calendar ms-2">
                                    <label>${ad.publicationDate}</label>
                                </i>
                            </small>
                        </p>
                    </div>
                    <div class="col-md-3">
                        <div class="flex-c align-content-center align-items-center h-100">
                            <h3>
                                <c:choose>
                                    <c:when test="${ad.price == 0}">
                                        <fmt:message key="announcements.free"/>
                                    </c:when>
                                    <c:otherwise>
                                        ${ad.price}BYN
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${pagination_data.isPrevious()}">
                    <li class="page-item">
                        <button class="page-link pag"
                                value="previous"
                                tabindex="-1">
                            <fmt:message key="pagination.previous"/>
                        </button>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link" tabindex="-1">
                            <fmt:message key="pagination.previous"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
            <li class="page-item disabled"><a class="page-link">${pagination_data.currentPage + 1}</a></li>
            <c:choose>
                <c:when test="${pagination_data.isNext()}">
                    <li class="page-item">
                        <button class="page-link pag"
                                value="next">
                            <fmt:message key="pagination.next"/>
                        </button>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link">
                            <fmt:message key="pagination.next"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>

    </aside>

</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/js/main.js"/>"></script>
<script src="<c:url value="/js/myAnnouncements.js"/>"></script>

</body>
</html>
