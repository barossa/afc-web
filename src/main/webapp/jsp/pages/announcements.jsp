<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title>
        <fmt:message key="project.titleTag"/>
        <fmt:message key="project.advertisements"/>
    </title>
    <link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/announcements.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">

</head>

<body>
<c:import url="/jsp/components/navbar.jsp"/>

<div id="main"> <%--Main flex container--%>

    <nav>
        <div class="card mx-3 my-3"> <%--Filters--%>
            <form id="filtersForm" onsubmit="return false;">
                <input type="hidden" name="command" value="find_announcements"/>
                <article class="card-group-item"> <%--Price filter--%>
                    <header class="card-header">
                        <h6 class="title">
                            <fmt:message key="filter.priceRange"/>
                        </h6>
                    </header>
                    <div class="filter-content">
                        <div class="card-body">
                            <div class="form-row flex-c" style="justify-content: space-around;">
                                <div class="form-group col-md-5">
                                    <label>
                                        <fmt:message key="filter.rangeMin"/>
                                    </label>
                                    <c:choose>
                                        <c:when test="${pagination_data.rangeMin != 0}">
                                            <input type="number" id="rangeMin" name="rangeMin" class="form-control"
                                                   value="${pagination_data.rangeMin}"
                                                   pattern="[0-9]*" min="0"
                                                   placeholder="BYN 0">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="number" id="rangeMin" name="rangeMin" class="form-control"
                                                   pattern="[0-9]*" min="0"
                                                   placeholder="BYN 0">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group col-md-5 text-right">
                                    <label>
                                        <fmt:message key="filter.rangeMax"/>
                                    </label>
                                    <c:choose>
                                        <c:when test="${pagination_data.rangeMax != 0}">
                                            <input type="number" id="rangeMax" name="rangeMax" class="form-control"
                                                   value="${pagination_data.rangeMax}"
                                                   pattern="[0-9]*" min="0"
                                                   placeholder="BYN 100">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="number" id="rangeMax" name="rangeMax" class="form-control"
                                                   pattern="[0-9]*" min="0"
                                                   placeholder="BYN 100">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div> <!-- card-body.// -->
                    </div>
                </article> <!-- card-group-item.// -->
                <article class="card-group-item"> <%--Categories filter--%>
                    <header class="card-header">
                        <h6 class="title">
                            <fmt:message key="filter.categories"/>
                        </h6>
                    </header>
                    <div class="filter-content flex-c">
                        <div class="card-body left-auto">

                            <%--CATEGORIES--%>
                            <c:forEach var="category" items="${applicationScope.categories}">
                                <div class="custom-control custom-checkbox">
                                    <c:choose>
                                        <c:when test="${pagination_data.categories.contains(category)}">
                                            <input type="checkbox" name="category" class="custom-control-input"
                                                   checked="true"
                                                   id="category_${category.id}" value="${category.id}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="category" class="custom-control-input"
                                                   id="category_${category.id}" value="${category.id}">
                                        </c:otherwise>
                                    </c:choose>
                                    <label class="custom-control-label">
                                        <fmt:message key="filter.category_${category.id}"/>
                                    </label>
                                </div>
                            </c:forEach>
                            <%--CATEGORIES--%>
                        </div> <!-- card-body.// -->
                    </div>
                </article> <!-- card-group-item.// -->
                <article class="card-group-item"> <%--Regions filter--%>
                    <header class="card-header">
                        <h6 class="title">
                            <fmt:message key="filter.region"/>
                        </h6>
                    </header>
                    <div class="filter-content flex-c">
                        <div class="card-body left-auto">
                            <%--REGIONS--%>
                            <c:forEach var="region" items="${applicationScope.regions}">
                                <div class="custom-control custom-checkbox">
                                    <c:choose>
                                        <c:when test="${pagination_data.regions.contains(region)}">
                                            <input type="checkbox" name="region" class="custom-control-input"
                                                   checked="true"
                                                   id="region_${region.id}" value="${region.id}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="region" class="custom-control-input"
                                                   id="region_${region.id}" value="${region.id}">
                                        </c:otherwise>
                                    </c:choose>
                                    <label class="custom-control-label">
                                        <fmt:message key="filter.region_${region.id}"/>
                                    </label>
                                </div>
                                <!-- form-check.// -->
                            </c:forEach>
                            <%--REGIONS--%>
                        </div> <!-- card-body.// -->
                    </div>
                </article> <!-- End of region filter -->
                <article class="flex-c justify-content-center m-b-15">
                    <button id="resetButton" type="button" class="btn btn-outline-secondary">
                        <fmt:message key="filter.reset"/>
                    </button>
                </article>
            </form>
        </div> <!-- card.// -->
    </nav>

    <aside>

        <form id="searchForm" onsubmit="return false;" method="post">
            <div id="search" class="flex-c m-t-20 m-b-26"> <%--Search--%>

                <div class="input-group flex-c rounded align-content-center w-50">
                    <fmt:message var="searchPlaceholder" key="announcements.searchPlaceholder"/>
                    <input type="text" id="searchRequest" name="search" class="form-control rounded"
                           placeholder="${searchPlaceholder}"
                           aria-label="Search"
                           aria-describedby="search-addon"/>
                    <button type="button" id="searchButton" class="btn btn-primary rounded"
                            style="margin-left: 20px !important;">
                        <fmt:message key="announcements.search"/>
                    </button>
                </div>

            </div>
        </form>


        <div id="advertisements" class="me-3">

            <c:if test="${empty pagination_data.currentData}">
                <div class="card">
                    <fmt:message key="search.nothingFound"/>
                </div>
            </c:if>
            <c:forEach varStatus="varStatus" var="ad" items="${pagination_data.currentData}">
                <div id="${varStatus.index}" class="card mb-2 adCard" style="height: 150px;">
                    <div class="row">
                        <div class="col-md-2 align-items-center"
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
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=change_announcements_page&action=previous"
                               tabindex="-1">
                                <fmt:message key="pagination.previous"/>
                            </a>
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
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=change_announcements_page&action=next">
                                <fmt:message key="pagination.next"/>
                            </a>
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

        </div>

    </aside>


</div>

<script src="<c:url value="/js/announcements.js"/>"></script>
<%--<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>--%>

<c:import url="/jsp/components/footer.jsp"/>

</body>
</html>
