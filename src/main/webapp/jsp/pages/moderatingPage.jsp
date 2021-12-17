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
        <jsp:forward page="/controller?command=to_moderator_page"/>
    </c:if>

</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/myAnnouncements.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">

<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <div style="margin: 2rem 3rem">
        <afc:displayAnnouncements/>
    </div>

    <afc:pagination command="to_moderator_page"/>

</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/vendor/jquery/jquery-3.2.1.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
