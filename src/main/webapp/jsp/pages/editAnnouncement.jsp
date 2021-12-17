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
<link rel="stylesheet" href="<c:url value="/vendor/bootstrap/css/bootstrap-icons.css"/>">
<link rel="stylesheet" href="<c:url value="/vendor/bootstrap/fonts/bootstrap-icons-stylesheet.css"/>">
<link rel="stylesheet" href="<c:url value="/vendor/file-input/css/fileinput.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">

<link rel="stylesheet" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
<link rel="stylesheet" href="<c:url value="/vendor/file-input/themes/explorer-fas/theme.css"/>"/>
<%--<link rel="stylesheet" href="<c:url value="/css/submitAnnouncement.css"/>">--%>
<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="oldImages" style="display: none">
    <c:forEach var="image" items="${requestScope.announcement.images}">
        <div class="file-preview-frame krajee-default  kv-preview-thumb" id="thumb-newImages-${image.id}.jpg"
             data-fileindex="0" data-fileid="${image.id}.jpg" data-template="image" data-zoom="">
            <div class="kv-file-content">
                <img src="${image.base64}" class="file-preview-image kv-preview-data"
                     title="${image.id}.jpg" alt="${image.id}.jpg"
                     style="width: auto; height: auto; max-width: 100%; max-height: 100%; image-orientation: from-image;">
            </div>
            <div class="file-thumbnail-footer">
                <div class="file-footer-caption" title="${image.id}.jpg">
                    <div class="file-caption-info">${image.id}.jpg</div>
                </div>
                <div class="file-actions">
                    <div class="file-footer-buttons">
                        <button type="button" class="kv-file-zoom btn btn-sm btn-kv btn-default btn-outline-secondary"
                                title="View Details"><i class="bi-zoom-in"></i></button>
                    </div>
                </div>

                <div class="clearfix"></div>
            </div>

            <div class="kv-zoom-cache">
                <div class="file-preview-frame krajee-default  kv-zoom-thumb" id="zoom-thumb-newImages-${image.id}.jpg"
                     data-fileindex="0" data-fileid="${image.id}.jpg" data-template="image" data-zoom="{zoomData}">
                    <div class="kv-file-content">
                        <img src="${image.base64}" class="file-preview-image kv-preview-data"
                             title="${image.id}.jpg" alt="${image.id}.jpg"
                             style="width: auto; height: auto; max-width: 100%; max-height: 100%; image-orientation: from-image;">
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div id="main" class="justify-content-around">
    <div id="leftPanel">

        <div id="images" class="flex-c rounded-3" style="max-height: none !important;">
            <input id="newImages" name="image" type="file" class="file" multiple="multiple"
                   data-show-upload="false" data-show-caption="true"
                   data-allowed-file-extensions='["png", "jpg", "jpeg"]'
                   required="required"
                   data-browse-on-zone-click="true" data-msg-placeholder="Select {files} for upload...">

        </div>

        <div class="card my-4 mx-3" style="display: flex; align-items: center;">
            <p><fmt:message key="edit.imageWarn"/></p>
        </div>

    </div>

    <div id="description" style="align-items: center; display: flex; flex-direction: column">
        <form id="updateAdForm">
            <input type="hidden" name="command" value="update_announcement"/>
            <input type="hidden" name="id" value="${requestScope.announcement.id}">
            <div class="card" style="min-width: 35vw">
                <div class="my-4 mx-3">
                    <label class="h4">
                        <fmt:message key="announcements.titlePlaceholder"/><br/>
                        <input id="titleField" value="${requestScope.announcement.title}"/>
                    </label>
                    <div class="input-group mt-2">
                    <span class="input-group-text">
                        <fmt:message key="announcements.description"/>
                    </span>
                        <textarea id="descriptionArea" class="form-control "
                                  aria-label="With textarea">${requestScope.announcement.description}</textarea>
                    </div>
                    <div class="product_meta">
                        <div style="display: flex; margin-top: 1rem">
                            <strong style="align-self: center"><fmt:message key="announcements.category"/></strong>
                            <div class="dropdown flex-c justify-content-center p-2">
                                <input type="hidden" name="category" value="${requestScope.announcement.category.id}"
                                       id="categoryField">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="chooseCtButton"
                                        data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    <fmt:message key="filter.category_${requestScope.announcement.category.id}"/>
                                </button>
                                <ul id="categoriesDropdown" class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <c:forEach var="category" items="${applicationScope.categories}">
                                        <li><a id="${category.id}" class="dropdown-item">
                                            <fmt:message key="filter.category_${category.id}"/>
                                        </a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>

                        <div style="display: flex; margin-top: 1rem">
                            <strong style="align-self: center"><fmt:message key="filter.region"/></strong>
                            <div class="dropdown flex-c justify-content-center p-2">
                                <input type="hidden" name="region" value="${requestScope.announcement.region.id}"
                                       id="regionField">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="chooseRgButton"
                                        data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    <fmt:message key="filter.region_${requestScope.announcement.region.id}"/>
                                </button>
                                <ul id="regionsDropdown" class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    <c:forEach var="region" items="${applicationScope.regions}">
                                        <li id="${region.id}"><a class="dropdown-item">
                                            <fmt:message key="filter.region_${region.id}"/>
                                        </a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="fw-bolder" style="margin-top: 1rem; display: flex; align-items: center">
                        <fmt:message key="announcements.pricePlaceholder" var="pricePlaceholder"/>
                        <label for="price"><fmt:message key="announcements.price"/></label>
                        <input id="price" name="price" min="0" max="999999999" class="input form-control"
                               placeholder="${pricePlaceholder}" value="${requestScope.announcement.price.intValue()}"
                               type="number" step="1" style="max-width: 35%; margin-left: 8px;">
                        <span style="margin-left: 8px;">BYN</span>
                    </div>
                </div>
                <div class="card-footer flex-c" style="align-items: center; height: min-content">
                    <button class="btn btn-outline-primary" type="button" id="updateAd"><fmt:message
                            key="edit.update"/></button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:import url="/jsp/components/footer.jsp"/>

<script src="<c:url value="/js/main.js"/>"></script>
<script src="<c:url value="/vendor/file-input/js/plugins/piexif.js"/>"></script>
<script src="<c:url value="/vendor/file-input/js/plugins/sortable.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/js/fileinput.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/js/locales/ru.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/vendor/file-input/themes/gly/theme.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/js/submitAd.js"/>" crossorigin="anonymous"></script>
<script src="<c:url value="/js/editAnnouncement.js"/>" crossorigin="anonymous"></script>

</body>
</html>
