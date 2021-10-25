<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<c:choose>
    <c:when test="${latest_forward_path == null}">
        <jsp:forward page="/controller?command=find_announcements&load_only=1"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="${latest_forward_path}"/>
    </c:otherwise>
</c:choose>
</body>
</html>