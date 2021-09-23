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

</head>

<body>
<c:import url="/jsp/components/navbar.jsp"/>

<%--<div class="flex-c m-t-20 m-b-26">
    <div class="input-group rounded align-content-center w-50">
        <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
               aria-describedby="search-addon"/>
        <button type="button" class="btn btn-primary" style="margin-left: 20px !important;">Search</button>
    </div>
</div>--%>
<div id="main"> <%--Main flex container--%>

    <nav>
        NAVIGATION AND FILTERS
    </nav>

    <aside>
        <div id="search" class="flex-c m-t-20 m-b-26"> <%--Search--%>
            <div class="input-group rounded align-content-center w-50">
                <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
                       aria-describedby="search-addon"/>
                <button type="button" class="btn btn-primary" style="margin-left: 20px !important;">Search</button>
            </div>
        </div>
        <div id="advertisements">
            ADVERTISEMENTS BLA BLA
        </div>
    </aside>


</div>

<%--<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>--%>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

</body>
</html>