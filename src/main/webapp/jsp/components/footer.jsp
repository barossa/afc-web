<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<footer class="bg-light text-center text-lg-start flex-c align-items-center justify-content-center"
        style="background-color: #B1B695 !important; min-height: 3rem;">
    <!-- Copyright -->
    <div class="text-center">
        <fmt:message key="footer.copyright"/>
        <a class="text-dark" href="https://github.com/barossa/afc-web/">
            <fmt:message key="footer.description"/>
        </a>
    </div>
    <!-- Copyright -->
</footer>
</html>
