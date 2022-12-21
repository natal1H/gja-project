<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Student page</title>
    <link rel="icon" href="/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" />"></script>
</head>
<body>
<tag:header></tag:header>

<div class="wrapper">
    <h2>Student Page</h2>

    <div class="students-title">
        <h3>My lectors</h3>
    </div>

    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th style="text-align: right;">Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Loop over and print students -->
        <c:forEach var="tempLector" items="${lectors}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/profile?id=${tempLector.id}&inst=ALL&sort=ArtistASC">${tempLector.userName}</a></td>
                <td>${tempLector.firstName}</td>
                <td>${tempLector.lastName}</td>
                <td>
                    REMOVE
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>

<style>

    .students-title {
        display: flex;
        justify-content: space-between;
        align-items: baseline;
    }

    .students-title a {
        padding: 12px 20px;
        background-color: #13b992;
        color: #1a1d28;
        border-radius: 12px;
        text-decoration: none;
        transition: linear 0.3s;
    }
    .playlists-title a:hover {
        box-shadow: 0px 0px 5px 5px rgba(19, 185, 146, 0.24);
    }

</style>
