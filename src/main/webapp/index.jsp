<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.role != 'GUEST' && sessionScope.user.status == 'DELAYED_REG'}">
        <jsp:forward page="/controller?command=to_confirm_page"/>
    </c:when>
    <c:when test="${sessionScope.user.role != 'GUEST' && sessionScope.user.status == 'BANNED'}">
        <jsp:forward page="/jsp/pages/auth/banPage.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="/controller?command=find_announcements"/>
    </c:otherwise>
</c:choose>
</body>
</html>