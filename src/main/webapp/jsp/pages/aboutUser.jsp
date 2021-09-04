<%--
  Created by IntelliJ IDEA.
  User: antoxa
  Date: 9/2/21
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.getLogin()}</title>
</head>
<body>
${user.toString()}
<br/>
<img src="data:image/png;Base64,${user.getProfileImage().getBase64()} " height="100px" width="100px" alt="[No image]"/>
</body>
</html>
