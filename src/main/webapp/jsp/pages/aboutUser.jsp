<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.getLogin()}</title>
</head>
<body>
<c:import url="../components/navbar.jsp"/>
${user.toString()}
<br/>
<img src="data:image/png;Base64,${user.getProfileImage().getBase64()} " height="100px" width="100px" alt="[No image]"/>
</body>
</html>
