<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="project.titleTag"/><fmt:message key="announcements.my"/></title>
</head>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/myAnnouncements.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/vendor/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">

<body>

<c:import url="/jsp/components/navbar.jsp"/>

<div id="main">

    <%--<aside>

    </aside>--%>
    <div class="container-xl my-4">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
                        <h2><fmt:message key="admin.users"/></h2>
                    </div>
                    <div class="col-sm-7">
                        <a href="#" class="btn btn-secondary"><i class="fa fa-plus-circle"></i>
                            <span>Add New User</span></a>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Date Created</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td><a href="#"><img src="/examples/images/avatar/1.jpg" class="avatar" alt="Avatar"> Michael
                        Holz</a></td>
                    <td>04/10/2013</td>
                    <td>Admin</td>
                    <td><span class="status text-success">&bull;</span> Active</td>
                    <td>
                        <a href="#" class="settings">
                            <i class="fa fa-cog"></i>
                        </a>
                        <a href="#" class="delete">
                            <i class="fa fa-times-circle"></i>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>2</td>
                    <td><a href="#"><img src="/examples/images/avatar/2.jpg" class="avatar" alt="Avatar"> Paula
                        Wilson</a></td>
                    <td>05/08/2014</td>
                    <td>Publisher</td>
                    <td><span class="status text-success">&bull;</span> Active</td>
                    <td>
                        <a href="#" class="settings" title="Settings" data-toggle="tooltip"><i
                                class="material-icons">&#xE8B8;</i></a>
                        <a href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
                    </td>
                </tr>
                <tr>
                    <td>3</td>
                    <td><a href="#"><img src="/examples/images/avatar/3.jpg" class="avatar" alt="Avatar"> Antonio
                        Moreno</a></td>
                    <td>11/05/2015</td>
                    <td>Publisher</td>
                    <td><span class="status text-danger">&bull;</span> Suspended</td>
                    <td>
                        <a href="#" class="settings" title="Settings" data-toggle="tooltip"><i
                                class="material-icons">&#xE8B8;</i></a>
                        <a href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
                    </td>
                </tr>
                <tr>
                    <td>4</td>
                    <td><a href="#"><img src="/examples/images/avatar/4.jpg" class="avatar" alt="Avatar"> Mary
                        Saveley</a></td>
                    <td>06/09/2016</td>
                    <td>Reviewer</td>
                    <td><span class="status text-success">&bull;</span> Active</td>
                    <td>
                        <a href="#" class="settings" title="Settings" data-toggle="tooltip"><i
                                class="material-icons">&#xE8B8;</i></a>
                        <a href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
                    </td>
                </tr>
                <tr>
                    <td>5</td>
                    <td><a href="#"><img src="/examples/images/avatar/5.jpg" class="avatar" alt="Avatar"> Martin
                        Sommer</a></td>
                    <td>12/08/2017</td>
                    <td>Moderator</td>
                    <td><span class="status text-warning">&bull;</span> Inactive</td>
                    <td>
                        <a href="#" class="settings" title="Settings" data-toggle="tooltip"><i
                                class="material-icons">&#xE8B8;</i></a>
                        <a href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>


</div>

<footer>
    <c:import url="/jsp/components/footer.jsp"/>
</footer>

<script src="<c:url value="/js/main.js"/>"></script>

</body>
</html>
