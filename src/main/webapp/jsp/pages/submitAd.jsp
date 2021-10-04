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
        <fmt:message key="announcements.submitAd"/>
    </title>

    <link rel="stylesheet" href="<c:url value="/vendor/bootstrap/css/bootstrap-icons.css"/>">
    <link rel="stylesheet" href="<c:url value="/vendor/bootstrap/fonts/bootstrap-icons-stylesheet.css"/>">
    <link rel="stylesheet" href="<c:url value="/vendor/file-input/css/fileinput.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/vendor/file-input/themes/explorer-fas/theme.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/submitAnnouncement.css"/>">

</head>

<body>
<c:import url="/jsp/components/navbar.jsp"/>

<div id="main" class="flex-c p-3">
    <div style="margin-left: 3.5rem; margin-right: 3.5rem;">

        <div class="flex-c" id="description">
            <h5 class="flex-c justify-content-center"
                style="font-size: 30px;">
                <fmt:message key="announcements.submission"/>
            </h5>

            <fmt:message key="announcements.pricePlaceholder" var="pricePlaceholder"/>
            <fmt:message key="announcements.titlePlaceholder" var="titlePlaceholder"/>

            <div class="form-group input-group rounded me-auto mt-3" style="display:inline-flex;">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-pencil-square-o m-t-6 m-b-6"></i> </span>
                </div>
                <input name="adTitle" class="input form-control" placeholder="${titlePlaceholder}"
                       type="text">
            </div>

            <div class="form-group input-group me-auto mt-2" style="display: inline-flex;">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-money m-t-6 m-b-6"></i> </span>
                </div>
                <input name="price" min="0" max="999999999" class="input form-control" placeholder="${pricePlaceholder}"
                       type="number">
            </div>


            <div class="flex-c d-inline-flex my-1">
            <%--Category dropdown--%>
            <div class="dropdown flex-c justify-content-center p-2">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="chooseCtButton"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    <fmt:message key="announcements.chooseCategory"/>
                </button>
                <ul id="categoriesDropdown" class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <c:forEach var="category" items="${applicationScope.categories}">
                        <li><a class="dropdown-item" id="category_${category.id}">
                            <fmt:message key="filter.category_${category.id}"/>
                        </a></li>
                    </c:forEach>
                </ul>
            </div>
            <%--Category dropdown--%>

            <%--Regioon dropdown--%>
            <div class="dropdown flex-c justify-content-center p-2">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="chooseRgButton"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    <fmt:message key="announcements.chooseRegion"/>
                </button>
                <ul id="regionsDropdown" class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <c:forEach var="region" items="${applicationScope.regions}">
                        <li><a class="dropdown-item" id="region_${region.id}">
                            <fmt:message key="filter.region_${region.id}"/>
                        </a></li>
                    </c:forEach>
                </ul>
            </div>
            <%--Region dropdown--%>
            </div>

            <div class="input-group">
            <span class="input-group-text">
                <fmt:message key="announcements.description"/>
            </span>
                <textarea class="form-control" aria-label="With textarea"></textarea>
            </div>

            <div class="flex-c mt-3 justify-content-center"> <%--Submit button--%>
                <button type="button" class="btn btn-primary w-50">
                    <fmt:message key="announcements.submitAd"/>
                </button>
            </div> <%--Submit button--%>

        </div>
    </div>

    <div class="flex-c" id="images">
        <div>
            <input id="input-images" name="images" type="file" class="file" multiple
                   data-show-upload="false" data-show-caption="true"
                   data-allowed-file-extensions='["png", "jpg", "jpeg"]'
                   required="required"
                   data-browse-on-zone-click="true" data-msg-placeholder="Select {files} for upload...">

        </div>
    </div>

</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>

<script src="<c:url value="/vendor/file-input/js/plugins/piexif.js"/>"></script>
<script src="<c:url value="/vendor/file-input/js/plugins/sortable.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/js/fileinput.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/js/locales/ru.js"/>" crossorigin="anonymous"></script>
<%--<script src="<c:url value="/vendor/file-input/js/locales/LANG.js"/>" crossorigin="anonymous"></script>--%>
<script src="<c:url value="/vendor/file-input/themes/gly/theme.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/js/submitAd.js"/>" crossorigin="anonymous"></script>
<%--<script src="<c:url value="/vendor/file-input/themes/fa/theme.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/themes/explorer-fas/theme.js"/>" crossorigin="anonymous"></script>--%>

</body>