<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="afc" uri="/WEB-INF/tld/taglib.tld" %>
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/pagination.css"/>">

</head>

<body>
<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <nav>
        <div class="card mx-3 my-3">
            <afc:announcementsFilter/>
        </div>
    </nav>

    <aside>

        <afc:announcementsSearch/>


        <div id="advertisements" class="me-3">

            <afc:displayAnnouncements/>

            <afc:pagination command="find_announcements"/>

        </div>

    </aside>
</div>

<script src="<c:url value="/js/main.js"/>"></script>

<c:import url="/jsp/components/footer.jsp"/>

</body>
</html>
