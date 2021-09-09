<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Error ${pageContext.errorData.statusCode}</title>
</head>
<body>
<h2>Error ${pageContext.errorData.statusCode}</h2>
<hr/>
<table>
    <tr>
        <td>Error:</td>
        <td>error</td>
    </tr>
    <tr>
        <td>URI:</td>
        <td>${pageContext.errorData.requestURI}</td>
    </tr>
    <tr>
        <td>Status code:</td>
        <td>${pageContext.errorData.statusCode}</td>
    </tr>
    <tr>
        <td>Servlet name:</td>
        <td>${pageContext.errorData.servletName}</td>
    </tr>
    <tr>
        <td>Exception:</td>
        <td>${pageContext.exception}</td>
    </tr>
    <tr>
        <td>Message:</td>
        <td>${pageContext.exception.message}</td>
    </tr>
</table>
</body>
</html>