<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="project.titleTag"/>${requestScope.announcement.title}</title>
</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/announcement.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main" class="justify-content-around">

    <div id="images" class="carousel carousel-dar carousel-fade flex-c rounded-3" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <c:if test="${requestScope.announcement.images.size() > 1}">
                <c:forEach varStatus="status" var="image" items="${requestScope.announcement.images}">
                    <c:choose>
                        <c:when test="${requestScope.announcement.primaryImageNumber == status.index}">
                            <button type="button" data-bs-target="#images"
                                    data-bs-slide-to="${status.index}" class="active" aria-current="true"
                                    aria-label="Image ${status.index}"></button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" data-bs-target="#images"
                                    data-bs-slide-to="${status.index}" aria-label="Image ${status.index}"></button>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:if>
        </div>
        <div class="carousel-inner">
            <c:forEach varStatus="status" var="image" items="${requestScope.announcement.images}">
                <c:choose>
                    <c:when test="${requestScope.announcement.primaryImageNumber == status.index}">
                        <div class="carousel-item active flex-c" data-bs-interval="10000">
                            <img src="${image.base64}"
                                 class="d-block w-100 align-self-center rounded-1"
                                 alt="Image ${status.index}">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="carousel-item flex-c" data-bs-interval="5000">
                            <img src="${image.base64}"
                                 class="d-block w-100 align-self-center rounded-1"
                                 alt="Image ${status.index}">
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${requestScope.announcement.images.size() > 1}">
            <button class="carousel-control-prev" type="button" data-bs-target="#images"
                    data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#images"
                    data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </c:if>
    </div>

    <div id="description" style="align-items: center; display: flex; flex-direction: column">
        <div class="card">
            <div class="my-4 mx-3">
                <h2>${requestScope.announcement.title}</h2>
                <p>${requestScope.announcement.description}</p>
                <div class="product_meta">
                <span class="posted_in"> <strong><fmt:message key="announcements.category"/>:</strong>
                    <a rel="tag"><fmt:message
                            key="filter.category_${requestScope.announcement.category.id}"/></a></span><br/>
                    <span class="tagged_as"><strong><fmt:message key="filter.region"/>:</strong> <a
                            rel="tag"><fmt:message
                            key="filter.region_${requestScope.announcement.region.id}"/></a></span>
                </div>
                <div class="m-bot15"><strong><fmt:message key="announcements.price"/>: </strong> <span
                        class="fw-bolder">${requestScope.announcement.price} BYN</span>
                </div>
            </div>
        </div>
        <div class="card text-center mt-4" style="max-width: 450px">
            <div class="card-header"><fmt:message key="announcements.author"/></div>
            <div class="card-body">
                <h5 class="card-title">${requestScope.announcement.owner.firstname} ${requestScope.announcement.owner.lastname}</h5>
                <div class="flex-c">
                    <div style="min-width: 100px;">
                        <img src="${requestScope.announcement.owner.profileImage.base64}"
                             class="rounded-circle align-self-start" height="50"
                             style="max-height: 60px; max-width: 60px;"/>
                    </div>
                    <div class="justify-content-center" style="max-width: 300px; display: flex; flex-direction: column; align-items: flex-end">
                        <p class="card-text">${requestScope.announcement.owner.about}</p><br/>
                        <p class="card-text text-muted">${requestScope.announcement.owner.email}</p>
                        <p class="card-text text-decoration-underline">${requestScope.announcement.owner.phone}</p>
                    </div>
                </div>
            </div>
            <input type="hidden" id="referer">
            <div class="card-footer text-muted"><a id="backButton" class="btn btn-secondary"><fmt:message
                    key="announcements.back"/></a></div>
        </div>
    </div>
</div>


<c:import url="/jsp/components/footer.jsp"/>

<%--<script src="<c:url value="/js/main.js"/>"></script>--%>

</body>
</html>
