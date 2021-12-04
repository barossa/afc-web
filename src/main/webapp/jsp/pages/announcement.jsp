<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="project.titleTag"/>${current_announcement.title}</title>
</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/announcement.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <div id="images" class="carousel carousel-dar carousel-fade flex-c rounded-3" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <c:if test="${current_announcement.images.size() > 1}">
                <c:forEach varStatus="status" var="image" items="${current_announcement.images}">
                    <c:choose>
                        <c:when test="${current_announcement.primaryImageNumber == status.index}">
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
            <c:forEach varStatus="status" var="image" items="${current_announcement.images}">
                <c:choose>
                    <c:when test="${current_announcement.primaryImageNumber == status.index}">
                        <div class="carousel-item active flex-c" data-bs-interval="10000">
                            <img src="data:image/png;Base64,${image.base64}" class="d-block w-100 align-self-center rounded-1"
                                 alt="Image ${status.index}">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="carousel-item flex-c" data-bs-interval="5000">
                            <img src="data:image/png;Base64,${image.base64}" class="d-block w-100 align-self-center rounded-1"
                                 alt="Image ${status.index}">
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${current_announcement.images.size() > 1}">
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

    <aside>
        <div id="description" class="flex-c">
            <h2>${current_announcement.title}</h2>
        </div>

    </aside>

</div>


<c:import url="/jsp/components/footer.jsp"/>


<%--<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>--%>
<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
